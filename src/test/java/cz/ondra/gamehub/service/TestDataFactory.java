package cz.ondra.gamehub.service;

import org.mockito.Answers;
import org.mockito.Mockito;

import java.util.UUID;

import cz.ondra.gamehub.db.entity.GameInfo;
import cz.ondra.gamehub.model.CurrentState;
import cz.ondra.gamehub.model.Game;
import cz.ondra.gamehub.model.GameConfiguration;
import cz.ondra.gamehub.model.GameDifficulty;
import cz.ondra.gamehub.model.GameInitRequest;
import cz.ondra.gamehub.model.InitializationOutput;
import cz.ondra.gamehub.model.PlayerInput;
import cz.ondra.gamehub.model.Status;
import cz.ondra.gamehub.testmodel.TestCurrentState;
import cz.ondra.gamehub.testmodel.TestGameConfiguration;
import cz.ondra.gamehub.testmodel.TestPlayerInput;

import static org.mockito.Mockito.when;

public class TestDataFactory {

    public static UUID testGameId = UUID.randomUUID();
    public static String testGameName = "test game name";
    public static String testGameDescription = "test game description";
    public static PlayerInput afterInitExampleInput = new TestPlayerInput();
    public static GameConfiguration afterInitConfiguration = new TestGameConfiguration();
    public static CurrentState afterInitCurrentState = new TestCurrentState();
    public static CurrentState afterPlayerTurnCurrentState = new TestCurrentState();
    public static CurrentState afterComputerTurnCurrentState = new TestCurrentState();
    public static Status afterTurnStatus = Status.WON;

    public static GameInfo prepareGameInfo() {
        GameInfo info = new GameInfo();
        info.setId(UUID.randomUUID());
        return info;
    }

    public static GameInitRequest prepareInitRequest(UUID gameId, GameDifficulty difficulty) {
        GameInitRequest initRequest = new GameInitRequest();
        initRequest.setGameId(gameId);
        initRequest.setDifficulty(difficulty);
        return initRequest;
    }

    public static GameExecutor prepareGameExecutorMock() {
        GameExecutor mockGameExecutor = Mockito.mock(GameExecutor.class, Answers.CALLS_REAL_METHODS);
        when(mockGameExecutor.getConfigClazz()).thenReturn(TestGameConfiguration.class);
        when(mockGameExecutor.getInputClazz()).thenReturn(TestPlayerInput.class);
        when(mockGameExecutor.getCurrentStateClazz()).thenReturn(TestCurrentState.class);
        when(mockGameExecutor.evaluateAfterTurn(Mockito.any())).thenReturn(afterTurnStatus);
        when(mockGameExecutor.processPlayersTurn(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(afterPlayerTurnCurrentState);
        when(mockGameExecutor.executeComputerTurn(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(afterComputerTurnCurrentState);
        when(mockGameExecutor.configureNewGame(Mockito.any())).thenReturn(new InitializationOutput(afterInitConfiguration, afterInitCurrentState));
        return mockGameExecutor;
    }

    public static Game prepareGameMock(GameExecutor mockGameExecutor) {
        Game mockGame = Mockito.mock(Game.class);
        when(mockGame.getGameId()).thenReturn(testGameId);
        when(mockGame.getName()).thenReturn(testGameName);
        when(mockGame.getDescription()).thenReturn(testGameDescription);
        when(mockGame.getExamplePlayerInput()).thenReturn(afterInitExampleInput);
        when(mockGame.getGameExecutor()).thenReturn(mockGameExecutor);
        return mockGame;
    }
}
