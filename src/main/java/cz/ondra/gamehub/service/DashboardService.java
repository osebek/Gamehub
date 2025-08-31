package cz.ondra.gamehub.service;

import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import cz.ondra.gamehub.db.entity.GameSession;
import cz.ondra.gamehub.db.repository.GameSessionRepository;
import cz.ondra.gamehub.exception.AuditorNotFoundException;
import cz.ondra.gamehub.model.Status;
import cz.ondra.gamehub.util.AuditUtil;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final GameSessionRepository gameSessionRepository;

    public List<GameSession> findOpenSessions() {
        UUID playerId = AuditUtil.getCurrentAuditor().orElseThrow(AuditorNotFoundException::new);
        return gameSessionRepository.findAllByPlayerIdAndStatusIn(playerId, Collections.singletonList(Status.OPEN));
    }

    public List<GameSession> findClosedSessions() {
        UUID playerId = AuditUtil.getCurrentAuditor().orElseThrow(AuditorNotFoundException::new);
        return gameSessionRepository.findAllByPlayerIdAndStatusIn(playerId, List.of(Status.WON, Status.LOST));
    }
}
