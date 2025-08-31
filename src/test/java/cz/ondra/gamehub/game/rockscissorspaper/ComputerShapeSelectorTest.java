package cz.ondra.gamehub.game.rockscissorspaper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

public class ComputerShapeSelectorTest {

    @ParameterizedTest
    @MethodSource("getComputerShapeByPlayerInput")
    void selectNextShape_returnsShapeWithForeseeingStrategy(Shape playerInput, Shape expectedShape) {
        RockScissorsPaperConfiguration config = new RockScissorsPaperConfiguration(NextMoveCalculatorStrategy.FORESEE);

        Assertions.assertEquals(expectedShape, ComputerShapeSelector.selectNextShape(config, playerInput));
    }

    private static Stream<Arguments> getComputerShapeByPlayerInput() {
        return Stream.of(
            Arguments.of(Shape.ROCK, Shape.PAPER),
            Arguments.of(Shape.SCISSORS, Shape.ROCK),
            Arguments.of(Shape.PAPER, Shape.SCISSORS)
        );
    }
}
