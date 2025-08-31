package cz.ondra.gamehub.game.rockscissorspaper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import cz.ondra.gamehub.model.Status;

public class StatusEvaluatorTest {

    @ParameterizedTest
    @MethodSource("getStatusByCurrentState_forRockPlayerInput")
    void evaluateRockScissorsPaperResult_returnsValidStatus_forRockPlayerInput(Shape computerShape, int playerWinCount,
                                                                               int computerWinCount, Status expectedStatus) {
        RockScissorsPaperState state = new RockScissorsPaperState();
        state.setLastPlayerShape(Shape.ROCK);
        state.setLastComputerShape(computerShape);
        state.setPlayerWinCount(playerWinCount);
        state.setComputerWinCount(computerWinCount);

        Assertions.assertEquals(expectedStatus, StatusEvaluator.evaluateRockScissorsPaperResult(state));
    }

    private static Stream<Arguments> getStatusByCurrentState_forRockPlayerInput() {
        return Stream.of(
            Arguments.of(Shape.ROCK, 2, 2, Status.OPEN),
            Arguments.of(Shape.SCISSORS, 2, 2, Status.WON),
            Arguments.of(Shape.PAPER, 2, 2, Status.LOST),
            Arguments.of(Shape.PAPER, 2, 1, Status.OPEN)
        );
    }

    @ParameterizedTest
    @MethodSource("getStatusByCurrentState_forScissorsPlayerInput")
    void evaluateRockScissorsPaperResult_returnsValidStatus_forScissorsPlayerInput(Shape computerShape, int playerWinCount,
                                                                               int computerWinCount, Status expectedStatus) {
        RockScissorsPaperState state = new RockScissorsPaperState();
        state.setLastPlayerShape(Shape.SCISSORS);
        state.setLastComputerShape(computerShape);
        state.setPlayerWinCount(playerWinCount);
        state.setComputerWinCount(computerWinCount);

        Assertions.assertEquals(expectedStatus, StatusEvaluator.evaluateRockScissorsPaperResult(state));
    }

    private static Stream<Arguments> getStatusByCurrentState_forScissorsPlayerInput() {
        return Stream.of(
            Arguments.of(Shape.SCISSORS, 2, 2, Status.OPEN),
            Arguments.of(Shape.PAPER, 2, 2, Status.WON),
            Arguments.of(Shape.ROCK, 2, 2, Status.LOST),
            Arguments.of(Shape.ROCK, 2, 1, Status.OPEN)
        );
    }

    @ParameterizedTest
    @MethodSource("getStatusByCurrentState_forPaperPlayerInput")
    void evaluateRockScissorsPaperResult_returnsValidStatus_forPaperPlayerInput(Shape computerShape, int playerWinCount,
                                                                                   int computerWinCount, Status expectedStatus) {
        RockScissorsPaperState state = new RockScissorsPaperState();
        state.setLastPlayerShape(Shape.PAPER);
        state.setLastComputerShape(computerShape);
        state.setPlayerWinCount(playerWinCount);
        state.setComputerWinCount(computerWinCount);

        Assertions.assertEquals(expectedStatus, StatusEvaluator.evaluateRockScissorsPaperResult(state));
    }

    private static Stream<Arguments> getStatusByCurrentState_forPaperPlayerInput() {
        return Stream.of(
            Arguments.of(Shape.PAPER, 2, 2, Status.OPEN),
            Arguments.of(Shape.ROCK, 2, 2, Status.WON),
            Arguments.of(Shape.SCISSORS, 2, 2, Status.LOST),
            Arguments.of(Shape.SCISSORS, 2, 1, Status.OPEN)
        );
    }

    @ParameterizedTest
    @MethodSource("updateStateByCurrentState_forPaperPlayerInput")
    void evaluateRockScissorsPaperResult_updatesCurrentState_forPaperPlayerInput(Shape computerShape, int playerWinCount, int computerWinCount,
                                                                                 int expectedPlayerWinCount, int expectedComputerWinCount) {
        RockScissorsPaperState state = new RockScissorsPaperState();
        state.setLastPlayerShape(Shape.PAPER);
        state.setLastComputerShape(computerShape);
        state.setPlayerWinCount(playerWinCount);
        state.setComputerWinCount(computerWinCount);
        int executedTurns = 1;
        state.setExecutedTurns(executedTurns);

        StatusEvaluator.evaluateRockScissorsPaperResult(state);
        Assertions.assertEquals(expectedPlayerWinCount, state.getPlayerWinCount());
        Assertions.assertEquals(expectedComputerWinCount, state.getComputerWinCount());
        Assertions.assertEquals(executedTurns + 1, state.getExecutedTurns());
    }

    private static Stream<Arguments> updateStateByCurrentState_forPaperPlayerInput() {
        return Stream.of(
            Arguments.of(Shape.PAPER, 2, 2, 2, 2),
            Arguments.of(Shape.ROCK, 2, 2, 3, 2),
            Arguments.of(Shape.SCISSORS, 2, 2, 2, 3),
            Arguments.of(Shape.SCISSORS, 2, 1, 2, 2)
        );
    }
}
