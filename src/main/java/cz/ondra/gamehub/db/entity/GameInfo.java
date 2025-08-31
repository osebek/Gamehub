package cz.ondra.gamehub.db.entity;

import java.util.Set;
import java.util.UUID;

import cz.ondra.gamehub.model.Game;
import cz.ondra.gamehub.model.GameDifficulty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class GameInfo {

    @Id
    private UUID id;

    private String name;

    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "text[]")
    private Set<GameDifficulty> difficultyLevels;

    public static GameInfo forGame(Game game) {
        GameInfo info = new GameInfo();
        info.setId(game.getGameId());
        info.setName(game.getName());
        info.setDescription(game.getDescription());
        info.setDifficultyLevels(game.getDifficultyLevels());
        return info;
    }
}
