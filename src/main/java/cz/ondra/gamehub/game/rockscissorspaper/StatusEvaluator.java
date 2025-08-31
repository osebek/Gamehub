package cz.ondra.gamehub.game.rockscissorspaper;

import cz.ondra.gamehub.model.Status;

public class StatusEvaluator {

    public static Status evaluateRockScissorsPaperResult(RockScissorsPaperState currentState) {
        currentState.setExecutedTurns(currentState.getExecutedTurns() + 1);

        int result = evaluateTurnResult(currentState);
        if (result == 1) {
            currentState.setPlayerWinCount(currentState.getPlayerWinCount() + 1);
        }
        if (result == -1) {
            currentState.setComputerWinCount(currentState.getComputerWinCount() + 1);
        }

        return evaluateStatus(currentState);
    }

    private static Status evaluateStatus(RockScissorsPaperState currentState) {
        if (currentState.getPlayerWinCount() == 3) {
            return Status.WON;
        }
        if (currentState.getComputerWinCount() == 3) {
            return Status.LOST;
        }
        return Status.OPEN;
    }

    private static int evaluateTurnResult(RockScissorsPaperState currentState) {
        Shape lastPlayersShape = currentState.getLastPlayerShape();
        Shape lastComputerShape = currentState.getLastComputerShape();
        int result = 0;
        switch(lastPlayersShape) {
            case ROCK -> {
                if (lastComputerShape == Shape.SCISSORS) result = 1;
                if (lastComputerShape == Shape.PAPER) result = -1;
            }
            case SCISSORS -> {
                if (lastComputerShape == Shape.PAPER) result = 1;
                if (lastComputerShape == Shape.ROCK) result = -1;
            }
            case PAPER -> {
                if (lastComputerShape == Shape.ROCK) result = 1;
                if (lastComputerShape == Shape.SCISSORS) result = -1;
            }
        }
        return result;
    }
}
