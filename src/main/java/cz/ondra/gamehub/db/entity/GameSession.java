package cz.ondra.gamehub.db.entity;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.util.UUID;

import cz.ondra.gamehub.model.GameDifficulty;
import cz.ondra.gamehub.model.Status;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class GameSession {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @EqualsAndHashCode.Include
    private UUID id;

    @ManyToOne
    @JoinColumn(name="game_info_id")
    private GameInfo gameInfo;

    @CreatedBy
    private UUID playerId;

    @CreatedDate
    private Instant createTimestamp;

    @Enumerated(EnumType.STRING)
    private GameDifficulty difficulty;

    @Enumerated(EnumType.STRING)
    private Status status = Status.OPEN;

    private String configuration;

    private String currentState;

    public static GameSession forGameAndDifficulty(GameInfo info, GameDifficulty difficulty) {
        GameSession session = new GameSession();
        session.setGameInfo(info);
        session.setDifficulty(difficulty);
        return session;
    }
}
