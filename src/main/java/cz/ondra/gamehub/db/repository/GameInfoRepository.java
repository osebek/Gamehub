package cz.ondra.gamehub.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

import cz.ondra.gamehub.db.entity.GameInfo;

public interface GameInfoRepository extends JpaRepository<GameInfo, UUID> {
}
