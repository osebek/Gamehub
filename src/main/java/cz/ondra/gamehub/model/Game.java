package cz.ondra.gamehub.model;

import java.util.Set;
import java.util.UUID;

import cz.ondra.gamehub.service.executor.GameExecutor;

public interface Game {

    UUID getGameId();
    String getName();
    String getDescription();

    /**
     * Get supported game difficulty levels. Games can be only initialized for listed difficulties
     */
    Set<GameDifficulty> getDifficultyLevels();

    <R extends GameConfiguration, S extends PlayerInput,
        T extends CurrentState> GameExecutor<R,S,T> getGameExecutor();

    /**
     * Get example player input object to be communicated to user
     */
    PlayerInput getExamplePlayerInput();
}
