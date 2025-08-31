package cz.ondra.gamehub.game.rockscissorspaper;

import cz.ondra.gamehub.model.CurrentState;
import lombok.Data;

@Data
public class RockScissorsPaperState implements CurrentState {
    private int executedTurns;
    private int playerWinCount;
    private int computerWinCount;
    private Shape lastComputerShape;
    private Shape lastPlayerShape;
}
