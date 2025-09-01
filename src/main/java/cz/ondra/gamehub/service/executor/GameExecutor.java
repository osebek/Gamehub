package cz.ondra.gamehub.service.executor;

import cz.ondra.gamehub.model.CurrentState;
import cz.ondra.gamehub.model.DataOutput;
import cz.ondra.gamehub.model.GameConfiguration;
import cz.ondra.gamehub.model.GameDifficulty;
import cz.ondra.gamehub.model.InitializationOutput;
import cz.ondra.gamehub.model.PlayerInput;
import cz.ondra.gamehub.model.Status;

public abstract class GameExecutor<R extends GameConfiguration, S extends PlayerInput,
    T extends CurrentState> {

    public abstract Class<R> getConfigClazz();
    public abstract Class<S> getInputClazz();
    public abstract Class<T> getCurrentStateClazz();



    public DataOutput executeSingleTurn(R gameConfiguration,
                                        S playerInput, T currentState) {

        validateInput(currentState, playerInput);
        currentState = processPlayersTurn(gameConfiguration, playerInput, currentState);
        currentState = executeComputerTurn(gameConfiguration, playerInput, currentState);
        var status = evaluateAfterTurn(currentState);
        return new DataOutput(currentState, status);
    }

    protected abstract T processPlayersTurn(R gameConfiguration, S input, T currentState);

    protected abstract T executeComputerTurn(R gameConfiguration, S input, T currentState);

    protected abstract void validateInput(T currentState, S input);

    protected abstract Status evaluateAfterTurn(T currentState);

    public abstract InitializationOutput configureNewGame(GameDifficulty gameDifficulty);
}
