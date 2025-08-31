package cz.ondra.gamehub.game.rockscissorspaper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import cz.ondra.gamehub.exception.PlayerInputValidationException;
import cz.ondra.gamehub.model.GameDifficulty;
import cz.ondra.gamehub.model.InitializationOutput;

public class RockScissorsPaperGameExecutorTest {

    private static final int executedTurns = 5;
    private static final int playerWinCount = 1;
    private static final int computerWinCount = 0;
    private static final Shape lastPlayerShape = Shape.SCISSORS;
    private static final Shape lastComputerShape = Shape.PAPER;

    @Test
    void processPlayersTurn_updatesCurrentState() {
        RockScissorsGameExecutor executor = new RockScissorsGameExecutor();

        RockScissorsPaperConfiguration config = new RockScissorsPaperConfiguration(NextMoveCalculatorStrategy.FORESEE);
        RockScissorsPaperInput input = new RockScissorsPaperInput();
        Shape inputShape = Shape.ROCK;
        input.setPlayerShape(inputShape);

        RockScissorsPaperState state = new RockScissorsPaperState();
        state.setExecutedTurns(executedTurns);
        state.setPlayerWinCount(playerWinCount);
        state.setComputerWinCount(computerWinCount);
        state.setLastPlayerShape(lastPlayerShape);
        state.setLastComputerShape(lastComputerShape);

        RockScissorsPaperState outputState = executor.processPlayersTurn(config, input, state);

        Assertions.assertEquals(executedTurns, outputState.getExecutedTurns());
        Assertions.assertEquals(playerWinCount, outputState.getPlayerWinCount());
        Assertions.assertEquals(computerWinCount, outputState.getComputerWinCount());
        Assertions.assertEquals(inputShape, outputState.getLastPlayerShape());
        Assertions.assertEquals(lastComputerShape, outputState.getLastComputerShape());
    }

    @Test
    void executeComputerTurn_updatesCurrentState() {
        RockScissorsGameExecutor executor = new RockScissorsGameExecutor();

        RockScissorsPaperConfiguration config = new RockScissorsPaperConfiguration(NextMoveCalculatorStrategy.FORESEE);
        RockScissorsPaperInput input = new RockScissorsPaperInput();
        Shape inputShape = Shape.SCISSORS;
        input.setPlayerShape(inputShape);

        RockScissorsPaperState state = new RockScissorsPaperState();
        state.setExecutedTurns(executedTurns);
        state.setPlayerWinCount(playerWinCount);
        state.setComputerWinCount(computerWinCount);
        state.setLastPlayerShape(lastPlayerShape);
        state.setLastComputerShape(lastComputerShape);

        RockScissorsPaperState outputState = executor.executeComputerTurn(config, input, state);

        Assertions.assertEquals(executedTurns, outputState.getExecutedTurns());
        Assertions.assertEquals(playerWinCount, outputState.getPlayerWinCount());
        Assertions.assertEquals(computerWinCount, outputState.getComputerWinCount());
        Assertions.assertEquals(lastPlayerShape, outputState.getLastPlayerShape());
        Assertions.assertEquals(Shape.ROCK, outputState.getLastComputerShape());
    }

    @Test
    void validateInput_throwsException_whenNoPlayerShapeProvided() {
        RockScissorsGameExecutor executor = new RockScissorsGameExecutor();

        RockScissorsPaperInput input = new RockScissorsPaperInput();
        RockScissorsPaperState state = new RockScissorsPaperState();

        Exception ex = Assertions.assertThrows(PlayerInputValidationException.class, () -> executor.validateInput(state, input));
        Assertions.assertTrue(ex.getMessage().contains("Player shape is missing"));
    }

    @ParameterizedTest
    @MethodSource("getStrategyByGameDifficulty")
    void configureNewGame_returnsValidOutput(GameDifficulty difficulty, NextMoveCalculatorStrategy expectedStrategy) {
        RockScissorsGameExecutor executor = new RockScissorsGameExecutor();

        InitializationOutput output = executor.configureNewGame(difficulty);
        RockScissorsPaperConfiguration config = (RockScissorsPaperConfiguration) output.configuration();

        Assertions.assertEquals(expectedStrategy, config.getStrategy());
        Assertions.assertNotNull(output.currentState());
    }

    private static Stream<Arguments> getStrategyByGameDifficulty() {
        return Stream.of(
            Arguments.of(GameDifficulty.NORMAL, NextMoveCalculatorStrategy.RANDOM),
            Arguments.of(GameDifficulty.GOD, NextMoveCalculatorStrategy.FORESEE)
        );
    }
}
