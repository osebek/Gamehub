package cz.ondra.gamehub.game.rockscissorspaper;

import java.util.EnumSet;
import java.util.Set;
import java.util.UUID;

import cz.ondra.gamehub.model.Game;
import cz.ondra.gamehub.model.GameDifficulty;
import cz.ondra.gamehub.model.PlayerInput;
import cz.ondra.gamehub.service.executor.GameExecutor;
import lombok.Getter;

@Getter
public class RockScissorsPaperGame implements Game {
    private static final PlayerInput exampleInput = RockScissorsPaperInput.buildExamplePlayerInput();

    private final UUID gameId = UUID.fromString("ca6f1c51-5c1f-4e28-a7b5-00a0e260019e");
    private final String name = "Kamen nuzky papir";
    private final String description = "Select one of shapes: ROCK, SCISSORS, PAPER. Player winning three turns wins the game.";
    private final Set<GameDifficulty> difficultyLevels = EnumSet.of(GameDifficulty.NORMAL, GameDifficulty.GOD);
    private final GameExecutor<?,?,?> gameExecutor = new RockScissorsGameExecutor();

    @Override
    public PlayerInput getExamplePlayerInput() {
        return exampleInput;
    }
}
