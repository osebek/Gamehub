package cz.ondra.gamehub.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import cz.ondra.gamehub.TestcontainersConfiguration;
import cz.ondra.gamehub.config.TestAuditingConfig;
import cz.ondra.gamehub.db.entity.GameInfo;
import cz.ondra.gamehub.db.entity.GameSession;
import cz.ondra.gamehub.db.repository.GameInfoRepository;
import cz.ondra.gamehub.db.repository.GameSessionRepository;
import cz.ondra.gamehub.model.Game;
import cz.ondra.gamehub.model.GameDifficulty;
import cz.ondra.gamehub.model.GameInitRequest;
import cz.ondra.gamehub.model.Status;
import cz.ondra.gamehub.testdata.factory.ItTestDataFactory;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Import({TestcontainersConfiguration.class, TestAuditingConfig.class})
class GameServiceIT {

    @Autowired
    private GameService gameService;

    @Autowired
    private GameInfoRepository gameInfoRepository;

    @Autowired
    private GameSessionRepository gameSessionRepository;

    @AfterEach
    public void afterEach() {
        gameInfoRepository.deleteAll();
        gameSessionRepository.deleteAll();
    }

    @Test
    void registerGame_storesValidData() {
        Game game = ItTestDataFactory.prepareTestGame();
        gameService.registerGame(game);

        GameInfo info = gameInfoRepository.findById(game.getGameId()).get();

        assertThat(info.getId()).isEqualTo(game.getGameId());
        assertThat(info.getName()).isEqualTo(game.getName());
        assertThat(info.getDescription()).isEqualTo(game.getDescription());
        assertThat(info.getDifficultyLevels()).isEqualTo(game.getDifficultyLevels());
    }

    @Test
    void initializeGame_storesValidData() {
        Game game = ItTestDataFactory.prepareTestGame();
        gameService.registerGame(game);

        GameInitRequest initRequest = new GameInitRequest();
        initRequest.setGameId(ItTestDataFactory.gameId);
        initRequest.setDifficulty(GameDifficulty.NORMAL);

        gameService.initializeGame(initRequest);

        GameSession session = gameSessionRepository.findAll().getFirst();

        assertThat(TestAuditingConfig.TEST_AUDITOR).isEqualTo(session.getPlayerId());
        assertThat(initRequest.getDifficulty()).isEqualTo(session.getDifficulty());
        assertThat(Status.OPEN).isEqualTo(session.getStatus());
        assertThat(initRequest.getGameId()).isEqualTo(session.getGameInfo().getId());
    }
}
