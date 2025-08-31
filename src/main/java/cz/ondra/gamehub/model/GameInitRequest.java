package cz.ondra.gamehub.model;

import java.util.UUID;

import lombok.Data;

@Data
public class GameInitRequest {
    private UUID gameId;
    private GameDifficulty difficulty;
}
