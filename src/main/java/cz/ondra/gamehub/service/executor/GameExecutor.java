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

    /**
     * Get class type for configuration object
     */
    public abstract Class<R> getConfigClazz();

    /**
     * Get class type for player input object
     */
    public abstract Class<S> getInputClazz();

    /**
     * Get class type for current state describing object
     */
    public abstract Class<T> getCurrentStateClazz();

    public final DataOutput executeSingleTurn(R gameConfiguration,
                                        S playerInput, T currentState) {

        validateInput(currentState, playerInput);
        currentState = processPlayersTurn(gameConfiguration, playerInput, currentState);
        currentState = executeComputerTurn(gameConfiguration, playerInput, currentState);
        var status = evaluateAfterTurn(currentState);
        return new DataOutput(currentState, status);
    }

    /**
     * Game logic for processing of player input based on game configuration and last turn state
     * @return currentState object including updates triggered by player turn
     */
    protected abstract T processPlayersTurn(R gameConfiguration, S input, T currentState);

    /**
     * Game logic for execution of computer't turn, based on game configuration, game state after
     * player's turn and last player's input
     * @return currentState object including updates triggered execution of computer turn
     */
    protected abstract T executeComputerTurn(R gameConfiguration, S input, T currentState);

    /**
     * Method for validating input posted by player, based on the current state of the game.
     * Should throw a PlayerInputValidationException when player input is invalid
     */
    protected abstract void validateInput(T currentState, S input);

    /**
     * Game logic for evaluation of last turn result, based on the state after computer's turn.
     * @param currentState - current state object after last computer's turn, this object is modifiable to store the
     *                     current state changes after evaluation
     * @return status value
     */
    protected abstract Status evaluateAfterTurn(T currentState);

    /**
     * Method for configuring new game, based on selected game difficulty.
     * Method returns configuration object of the initialized game and current state object describing post initialization
     * state of the game
     * @return data wrapper of game configuration and post initialization game state object
     */
    public abstract InitializationOutput configureNewGame(GameDifficulty gameDifficulty);
}
