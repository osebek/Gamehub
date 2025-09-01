package cz.ondra.gamehub.service;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import cz.ondra.gamehub.game.rockscissorspaper.RockScissorsPaperGame;
import cz.ondra.gamehub.model.Game;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@ConditionalOnProperty(value = "gamehub.register.enabled", havingValue = "true")
@Component
@RequiredArgsConstructor
public class GameRegistrator {

    private final GameService gameService;

    @PostConstruct
    public void registerGames() {
        Game game = new RockScissorsPaperGame();
        gameService.registerGame(game);
    }
}
