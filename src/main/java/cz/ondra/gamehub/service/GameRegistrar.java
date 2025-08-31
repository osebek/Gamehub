package cz.ondra.gamehub.service;

import org.springframework.stereotype.Component;

import cz.ondra.gamehub.game.rockscissorspaper.RockScissorsPaperGame;
import cz.ondra.gamehub.model.Game;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class GameRegistrar {

    private final GameService gameService;

    @PostConstruct
    public void registerGames() {
        Game game = new RockScissorsPaperGame();
        gameService.registerGame(game);
    }
}
