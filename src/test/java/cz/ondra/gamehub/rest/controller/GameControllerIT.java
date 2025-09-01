package cz.ondra.gamehub.rest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.json.JsonCompareMode;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import cz.ondra.gamehub.TestcontainersConfiguration;
import cz.ondra.gamehub.db.repository.GameInfoRepository;
import cz.ondra.gamehub.model.Game;
import cz.ondra.gamehub.rest.dto.GameInitRequestDto;
import cz.ondra.gamehub.service.GameService;
import cz.ondra.gamehub.testdata.factory.ItTestDataFactory;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@SpringBootTest
@Import({TestcontainersConfiguration.class})
@AutoConfigureMockMvc
@Sql(scripts = "/db_test_scripts/delete_all.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class GameControllerIT {

    private static final String LOGGED_IN_USER_ID = "ff631d1d-c846-4dbf-a1f7-895047fbe503";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private GameInfoRepository gameInfoRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private GameService gameService;

    @Sql(scripts = "/db_test_scripts/game_data_insert.sql")
    @WithMockUser(value = LOGGED_IN_USER_ID)
    @Test
    void getAvailableGames_returnsGamesWithFullResponse() throws Exception {
        String expectedResponse = """
            [
                {
                    "gameId": "79dcf505-d4b6-47f2-a599-8447cb1c26d9",
                    "name": "Game 1",
                    "description": "Description for game 1",
                    "difficultyLevels": [
                        "NORMAL",
                        "GOD"
                    ]
                },
                {
                    "gameId": "f5c1daa7-6267-4bf7-b867-4ed0ea2a6f10",
                    "name": "Game 2",
                    "description": "Description for game 2",
                    "difficultyLevels": [
                        "NORMAL"
                    ]
                }
            ]
            """;

        mockMvc.perform(get("/games"))
            .andExpect(status().isOk())
            .andExpect(content().json(expectedResponse, JsonCompareMode.STRICT));
    }

    @WithMockUser(value = LOGGED_IN_USER_ID)
    @Test
    void initializeGame_returnsSessionState() throws Exception {
        Game game = ItTestDataFactory.prepareTestGame();
        gameService.registerGame(game);

        GameInitRequestDto dto = prepareInitRequest(game.getGameId());

        mockMvc.perform(post("/games").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.gameSessionId").isNotEmpty())
            .andExpect(jsonPath("$.status", is("OPEN")))
            .andExpect(jsonPath("$.description", is("IT test game description")))
            .andReturn();
    }

    @WithMockUser(value = LOGGED_IN_USER_ID)
    @Test
    void nextTurn_failsWhenSessionNotFound() throws Exception {
        GameInitRequestDto dto = prepareInitRequest(UUID.randomUUID());

        mockMvc.perform(post("/games/{gameSessionId}/next-turn", UUID.randomUUID()).contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
            .andExpect(status().isBadRequest());
    }

    private GameInitRequestDto prepareInitRequest(UUID gameId) {
        GameInitRequestDto dto = new GameInitRequestDto();
        dto.setGameId(gameId);
        dto.setDifficulty("NORMAL");
        return dto;
    }
}
