package cz.ondra.gamehub.game.rockscissorspaper;

import cz.ondra.gamehub.exception.PlayerInputValidationException;
import cz.ondra.gamehub.model.CurrentState;
import cz.ondra.gamehub.model.GameConfiguration;
import cz.ondra.gamehub.model.GameDifficulty;
import cz.ondra.gamehub.model.InitializationOutput;
import cz.ondra.gamehub.model.Status;
import cz.ondra.gamehub.service.GameExecutor;

public class RockScissorsGameExecutor extends GameExecutor<RockScissorsPaperConfiguration, RockScissorsPaperInput, RockScissorsPaperState> {

    @Override
    public Class<RockScissorsPaperConfiguration> getConfigClazz() {
        return RockScissorsPaperConfiguration.class;
    }

    @Override
    public Class<RockScissorsPaperInput> getInputClazz() {
        return RockScissorsPaperInput.class;
    }

    @Override
    public Class<RockScissorsPaperState> getCurrentStateClazz() {
        return RockScissorsPaperState.class;
    }

    @Override
    protected RockScissorsPaperState processPlayersTurn(RockScissorsPaperConfiguration gameConfiguration,
                                                        RockScissorsPaperInput input, RockScissorsPaperState currentState) {
        currentState.setLastPlayerShape(input.getPlayerShape());
        return currentState;
    }

    @Override
    protected RockScissorsPaperState executeComputerTurn(RockScissorsPaperConfiguration gameConfiguration, RockScissorsPaperInput input,
                                       RockScissorsPaperState currentState) {
        Shape nextShape = ComputerShapeSelector.selectNextShape(gameConfiguration, currentState.getLastPlayerShape());
        currentState.setLastComputerShape(nextShape);
        return currentState;
    }

    @Override
    protected void validateInput(RockScissorsPaperState currentState, RockScissorsPaperInput input) {
        if (input.getPlayerShape() == null) {
            throw new PlayerInputValidationException("Player shape is missing");
        }
    }

    @Override
    protected Status evaluateAfterTurn(RockScissorsPaperState currentState) {
        return StatusEvaluator.evaluateRockScissorsPaperResult(currentState);
    }

    @Override
    public InitializationOutput configureNewGame(GameDifficulty difficulty) {
        NextMoveCalculatorStrategy strategy = switch(difficulty) {
            case NORMAL -> NextMoveCalculatorStrategy.RANDOM;
            case GOD -> NextMoveCalculatorStrategy.FORESEE;
        };

        GameConfiguration config = new RockScissorsPaperConfiguration(strategy);
        CurrentState state = new RockScissorsPaperState();
        return new InitializationOutput(config, state);
    }
}
