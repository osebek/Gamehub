package cz.ondra.gamehub.game.rockscissorspaper;

import java.util.Random;
import java.util.function.Function;

public class ComputerShapeSelector {

    protected static Shape selectNextShape(RockScissorsPaperConfiguration configuration, Shape playerShape) {
        NextMoveCalculatorStrategy strategy = configuration.getStrategy();
        Function<Shape,Shape> nextShapeSelector = switch(strategy) {
            case RANDOM -> randomShapeSelector();
            case FORESEE -> foreseeingShapeSelector();
        };
        return nextShapeSelector.apply(playerShape);
    }

    private static Function<Shape, Shape> randomShapeSelector() {
        return playerShape -> {
            Random rand = new Random();
            Shape[] shapes = Shape.values();
            return shapes[rand.nextInt(shapes.length)];
        };
    }

    private static Function<Shape, Shape> foreseeingShapeSelector() {
        return playerShape -> switch (playerShape) {
            case ROCK -> Shape.PAPER;
            case SCISSORS -> Shape.ROCK;
            case PAPER -> Shape.SCISSORS;
        };
    }
}
