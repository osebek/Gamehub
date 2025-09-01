package cz.ondra.gamehub.service;

import com.google.gson.JsonObject;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.matchers.apachecommons.ReflectionEquals;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import cz.ondra.gamehub.db.entity.GameInfo;
import cz.ondra.gamehub.db.entity.GameSession;
import cz.ondra.gamehub.db.repository.GameSessionRepository;
import cz.ondra.gamehub.exception.GamehubException;
import cz.ondra.gamehub.model.CurrentState;
import cz.ondra.gamehub.model.Game;
import cz.ondra.gamehub.model.GameDifficulty;
import cz.ondra.gamehub.model.GameInitRequest;
import cz.ondra.gamehub.model.GameSessionState;
import cz.ondra.gamehub.model.PlayerInput;
import cz.ondra.gamehub.model.Status;
import cz.ondra.gamehub.service.executor.ExecutorMockFactory;
import cz.ondra.gamehub.service.executor.GameExecutor;
import cz.ondra.gamehub.testdata.factory.UnitTestDataFactory;
import cz.ondra.gamehub.testdata.model.TestCurrentState;
import cz.ondra.gamehub.testdata.model.TestGameConfiguration;
import cz.ondra.gamehub.testdata.model.TestPlayerInput;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class GameServiceTest {

    @Mock
    private GameSessionRepository gameSessionRepository;

    @Mock
    private GameInfoService gameInfoService;

    @Mock
    GameExecutor<TestGameConfiguration, TestPlayerInput, TestCurrentState> testGameExecutor = Mockito.mock(GameExecutor.class);

    @InjectMocks
    private GameService gameService;

    @BeforeEach
    public void beforeEach() {
        setupAuthentication();
    }

    @Test
    void registerGame() {
        Game mockGame = ExecutorMockFactory.prepareGameMock(null);
        GameInfo info = GameInfo.forGame(mockGame);

        gameService.registerGame(mockGame);

        verify(gameInfoService).createGameInfo(info);
    }

    @Test
    void initializeGame_throwsException_whenGameNotFoundInRegister() {
        Game mockGame = ExecutorMockFactory.prepareGameMock(null);

        gameService.registerGame(mockGame);

        GameInitRequest initRequest = UnitTestDataFactory.prepareInitRequest(UUID.randomUUID(), GameDifficulty.NORMAL);
        Exception ex = Assertions.assertThrows(GamehubException.class, () -> gameService.initializeGame(initRequest));
        Assertions.assertEquals("Game not found for specified id", ex.getMessage());
    }

    @Test
    void initializeGame_returnsValidOutput() {
        GameExecutor mockGameExecutor = ExecutorMockFactory.prepareGameExecutorMock();
        Game mockGame = ExecutorMockFactory.prepareGameMock(mockGameExecutor);

        GameDifficulty difficulty = GameDifficulty.NORMAL;
        GameInfo info = GameInfo.forGame(mockGame);
        GameSession session = GameSession.forGameAndDifficulty(info, difficulty);
        session.setId(UUID.randomUUID());

        when(gameInfoService.createGameInfo(Mockito.any())).thenReturn(info);
        when(gameSessionRepository.save(Mockito.any())).thenReturn(session);

        gameService.registerGame(mockGame);

        GameInitRequest initRequest = UnitTestDataFactory.prepareInitRequest(mockGame.getGameId(), difficulty);

        GameSessionState sessionState = gameService.initializeGame(initRequest);

        verify(gameInfoService).createGameInfo(info);
        verify(gameSessionRepository).save(Mockito.any());

        Assertions.assertEquals(session.getId(), sessionState.getGameSessionId());
        Assertions.assertEquals(Status.OPEN, sessionState.getStatus());
        Assertions.assertEquals(mockGame.getDescription(), sessionState.getDescription());

        CurrentState actualState = sessionState.getCurrentState();
        Assertions.assertTrue(new ReflectionEquals(ExecutorMockFactory.afterInitCurrentState).matches(actualState));

        PlayerInput actualInput = sessionState.getExamplePlayerInput();
        Assertions.assertTrue(new ReflectionEquals(ExecutorMockFactory.afterInitExampleInput).matches(actualInput));
    }

    @Test
    void processGameTurn_throwsException_whenGameSessionNotFound() {
        when(gameSessionRepository.findByIdAndPlayerId(Mockito.any(), Mockito.any())).thenReturn(Optional.empty());

        UUID sessionId = UUID.randomUUID();
        JsonObject playerInput = new JsonObject();
        Exception ex = Assertions.assertThrows(GamehubException.class, () -> gameService.processGameTurn(sessionId, playerInput));
        Assertions.assertEquals("Session not found for specified id and logged-in player", ex.getMessage());
    }

    @Test
    void processGameTurn_throwsException_whenGameSessionAlreadyClosed() {
        GameSession session = new GameSession();
        session.setStatus(Status.WON);
        when(gameSessionRepository.findByIdAndPlayerId(Mockito.any(), Mockito.any())).thenReturn(Optional.of(session));

        UUID sessionId = UUID.randomUUID();
        JsonObject playerInput = new JsonObject();
        Exception ex = Assertions.assertThrows(GamehubException.class, () -> gameService.processGameTurn(sessionId, playerInput));
        Assertions.assertEquals("Game session is already closed", ex.getMessage());
    }

    @Test
    void processGameTurn_returnsValidOutput() {
        GameExecutor mockGameExecutor = ExecutorMockFactory.prepareGameExecutorMock();
        Game mockGame = ExecutorMockFactory.prepareGameMock(mockGameExecutor);

        GameDifficulty difficulty = GameDifficulty.NORMAL;
        GameInfo info = GameInfo.forGame(mockGame);
        GameSession session = GameSession.forGameAndDifficulty(info, difficulty);
        session.setId(UUID.randomUUID());
        JsonObject playerInput = new JsonObject();

        gameService.registerGame(mockGame);

        when(gameSessionRepository.findByIdAndPlayerId(Mockito.any(), Mockito.any())).thenReturn(Optional.of(session));

        GameSessionState sessionState =  gameService.processGameTurn(session.getId(), playerInput);

        verify(gameSessionRepository).save(session);

        Assertions.assertEquals(session.getId(), sessionState.getGameSessionId());
        Assertions.assertEquals(ExecutorMockFactory.afterTurnStatus, sessionState.getStatus());
        Assertions.assertEquals(mockGame.getDescription(), sessionState.getDescription());

        CurrentState actualState = sessionState.getCurrentState();
        Assertions.assertTrue(new ReflectionEquals(ExecutorMockFactory.afterComputerTurnCurrentState).matches(actualState));

        PlayerInput actualInput = sessionState.getExamplePlayerInput();
        Assertions.assertTrue(new ReflectionEquals(ExecutorMockFactory.afterInitExampleInput).matches(actualInput));
    }

    private void setupAuthentication() {
        SecurityContextHolder.getContext().setAuthentication(new Authentication() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return List.of();
            }

            @Override
            public Object getCredentials() {
                return null;
            }

            @Override
            public Object getDetails() {
                return null;
            }

            @Override
            public Object getPrincipal() {
                return null;
            }

            @Override
            public boolean isAuthenticated() {
                return false;
            }

            @Override
            public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {

            }

            @Override
            public String getName() {
                return UUID.randomUUID().toString();
            }
        });
    }
}