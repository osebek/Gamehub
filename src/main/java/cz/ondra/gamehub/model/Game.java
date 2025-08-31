package cz.ondra.gamehub.model;

import java.util.Set;
import java.util.UUID;

import cz.ondra.gamehub.service.GameExecutor;

public interface Game {

    UUID getGameId();
    String getName();
    String getDescription();
    Set<GameDifficulty> getDifficultyLevels();
    <R extends GameConfiguration, S extends PlayerInput,
        T extends CurrentState> GameExecutor<R,S,T> getGameExecutor();
    PlayerInput getExamplePlayerInput();
}
