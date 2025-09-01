package cz.ondra.gamehub.service;

import com.google.gson.JsonObject;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import cz.ondra.gamehub.db.entity.GameInfo;
import cz.ondra.gamehub.db.entity.GameSession;
import cz.ondra.gamehub.db.repository.GameSessionRepository;
import cz.ondra.gamehub.exception.AuditorNotFoundException;
import cz.ondra.gamehub.exception.GamehubException;
import cz.ondra.gamehub.service.executor.GameExecutor;
import cz.ondra.gamehub.model.CurrentState;
import cz.ondra.gamehub.model.DataInput;
import cz.ondra.gamehub.model.DataOutput;
import cz.ondra.gamehub.model.Game;
import cz.ondra.gamehub.model.GameConfiguration;
import cz.ondra.gamehub.model.GameInitRequest;
import cz.ondra.gamehub.model.GameSessionState;
import cz.ondra.gamehub.model.InitializationOutput;
import cz.ondra.gamehub.model.PlayerInput;
import cz.ondra.gamehub.model.Status;
import cz.ondra.gamehub.util.AuditUtil;
import cz.ondra.gamehub.util.JsonUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GameService {

    private Map<UUID, Game> gameRegister = new HashMap<>();

    private final GameSessionRepository gameSessionRepository;

    private final GameInfoService gameInfoService;

    public void registerGame(Game game) {
        gameRegister.put(game.getGameId(), game);
        GameInfo info = GameInfo.forGame(game);
        gameInfoService.createGameInfo(info);
    }

    @Transactional
    public GameSessionState initializeGame(GameInitRequest request) {
        Game game = gameRegister.get(request.getGameId());
        if (game == null) {
            throw new GamehubException("Game not found for specified id");
        }

        GameInfo info = gameInfoService.getById(game.getGameId());

        if (!info.getDifficultyLevels().contains(request.getDifficulty())) {
            throw new GamehubException("Selected difficulty is not supported for this game");
        }

        GameSession gameSession = GameSession.forGameAndDifficulty(info, request.getDifficulty());

        InitializationOutput initOutput = game.getGameExecutor().configureNewGame(request.getDifficulty());
        unwrapInitializationOutput(gameSession, initOutput);

        gameSession = gameSessionRepository.save(gameSession);

        return buildGameSessionState(game, gameSession, initOutput.currentState());
    }

    @Transactional
    public GameSessionState processGameTurn(UUID gameSessionId, JsonObject input) {
        UUID playerId = AuditUtil.getCurrentAuditor().orElseThrow(AuditorNotFoundException::new);

        GameSession gameSession = gameSessionRepository.findByIdAndPlayerId(gameSessionId, playerId)
            .orElseThrow(() -> new GamehubException("Session not found for specified id and logged-in player"));

        if (gameSession.getStatus() != Status.OPEN) {
            throw new GamehubException("Game session is already closed");
        }

        Game game = gameRegister.get(gameSession.getGameInfo().getId());

        DataInput gameData = wrapGameData(gameSession, input);

        GameExecutor exec = game.getGameExecutor();
        DataOutput output = exec.executeSingleTurn(gameData.configuration(), gameData.playerInput(), gameData.currentState());

        unwrapGameDataOutput(gameSession, output);
        gameSessionRepository.save(gameSession);

        return buildGameSessionState(game, gameSession, output.currentState());
    }

    private DataInput wrapGameData(GameSession gameSession, JsonObject input) {
        Game game = gameRegister.get(gameSession.getGameInfo().getId());
        Class<?> configClazz = game.getGameExecutor().getConfigClazz();
        GameConfiguration config = (GameConfiguration) JsonUtil.convertToObject(gameSession.getConfiguration(), configClazz);

        Class<?> stateClazz = game.getGameExecutor().getCurrentStateClazz();
        CurrentState state = (CurrentState) JsonUtil.convertToObject(gameSession.getCurrentState(), stateClazz);

        Class<?> inputClazz = game.getGameExecutor().getInputClazz();
        PlayerInput playerInput = (PlayerInput) JsonUtil.convertToObject(input, inputClazz);

        return new DataInput(config, playerInput, state);
    }

    private void unwrapGameDataOutput(GameSession gameSession, DataOutput dataOutput) {
        gameSession.setStatus(dataOutput.status());

        CurrentState state = dataOutput.currentState();
        String stringState = JsonUtil.convertToString(state);
        gameSession.setCurrentState(stringState);
    }

    private void unwrapInitializationOutput(GameSession gameSession, InitializationOutput initOutput) {
        GameConfiguration config = initOutput.configuration();
        String stringConfig = JsonUtil.convertToString(config);
        gameSession.setConfiguration(stringConfig);

        CurrentState state = initOutput.currentState();
        String stringState = JsonUtil.convertToString(state);
        gameSession.setCurrentState(stringState);
    }

    private GameSessionState buildGameSessionState(Game game, GameSession session, CurrentState currentState) {
        return GameSessionState.builder().gameSessionId(session.getId())
            .status(session.getStatus())
            .description(game.getDescription())
            .currentState(currentState)
            .examplePlayerInput(game.getExamplePlayerInput())
            .build();
    }
}