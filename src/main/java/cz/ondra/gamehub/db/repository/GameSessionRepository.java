package cz.ondra.gamehub.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import cz.ondra.gamehub.db.entity.GameSession;
import cz.ondra.gamehub.model.Status;

public interface GameSessionRepository extends JpaRepository<GameSession, UUID> {

    Optional<GameSession> findByIdAndPlayerId(UUID id, UUID playerId);

    List<GameSession> findAllByPlayerIdAndStatusIn(UUID playerId, List<Status> statuses);

}
