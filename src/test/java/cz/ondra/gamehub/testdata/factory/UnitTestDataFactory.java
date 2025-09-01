package cz.ondra.gamehub.testdata.factory;

import java.util.UUID;

import cz.ondra.gamehub.db.entity.GameInfo;
import cz.ondra.gamehub.model.GameDifficulty;
import cz.ondra.gamehub.model.GameInitRequest;

public class UnitTestDataFactory {

    public static GameInfo prepareGameInfo() {
        GameInfo info = new GameInfo();
        info.setId(UUID.randomUUID());
        return info;
    }

    public static GameInitRequest prepareInitRequest(UUID gameId, GameDifficulty difficulty) {
        GameInitRequest initRequest = new GameInitRequest();
        initRequest.setGameId(gameId);
        initRequest.setDifficulty(difficulty);
        return initRequest;
    }
}
