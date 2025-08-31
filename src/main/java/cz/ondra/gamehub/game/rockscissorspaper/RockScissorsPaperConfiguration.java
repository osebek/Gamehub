package cz.ondra.gamehub.game.rockscissorspaper;

import cz.ondra.gamehub.model.GameConfiguration;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RockScissorsPaperConfiguration implements GameConfiguration {
    private NextMoveCalculatorStrategy strategy;
}
