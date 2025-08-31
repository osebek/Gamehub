package cz.ondra.gamehub.service;

import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.UUID;

import cz.ondra.gamehub.db.entity.GameInfo;
import cz.ondra.gamehub.db.repository.GameInfoRepository;
import cz.ondra.gamehub.exception.GamehubException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GameInfoService {

    private final GameInfoRepository gameInfoRepository;

    public GameInfo createGameInfo(GameInfo gameInfo) {
        return gameInfoRepository.save(gameInfo);
    }

    public GameInfo getById(UUID id) {
        return gameInfoRepository.findById(id).orElseThrow(() -> new GamehubException("Game info not found for specified id"));
    }

    public Collection<GameInfo> getAvailableGames() {
        return gameInfoRepository.findAll();
    }
}
