package cz.ondra.gamehub.game.rockscissorspaper;

import cz.ondra.gamehub.model.PlayerInput;
import lombok.Data;

@Data
public class RockScissorsPaperInput implements PlayerInput {

    private Shape playerShape;

    public static PlayerInput buildExamplePlayerInput() {
        RockScissorsPaperInput exampleInput = new RockScissorsPaperInput();
        exampleInput.setPlayerShape(Shape.ROCK);
        return exampleInput;
    }
}
