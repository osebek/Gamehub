package cz.ondra.gamehub.rest.controller;

import com.google.gson.JsonObject;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

import cz.ondra.gamehub.model.GameInitRequest;
import cz.ondra.gamehub.model.GameSessionState;
import cz.ondra.gamehub.rest.api.GameApi;
import cz.ondra.gamehub.rest.dto.AvailableGameDto;
import cz.ondra.gamehub.rest.dto.GameInitRequestDto;
import cz.ondra.gamehub.rest.dto.GameSessionStateDto;
import cz.ondra.gamehub.rest.mapper.GameInitRequestMapper;
import cz.ondra.gamehub.rest.mapper.GameMapper;
import cz.ondra.gamehub.service.GameInfoService;
import cz.ondra.gamehub.service.GameService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class GameController implements GameApi {

    private final GameService gameService;
    private final GameInfoService gameInfoService;

    private final GameMapper mapper;
    private final GameInitRequestMapper initRequestMapper;

    @Override
    public List<AvailableGameDto> getAvailableGames() {
        return mapper.entitiesToDtos(gameInfoService.getAvailableGames());
    }

    @Override
    public GameSessionStateDto initializeGame(GameInitRequestDto initRequestDto) {
        GameInitRequest request = initRequestMapper.dtoToEntity(initRequestDto);
        GameSessionState sessionState = gameService.initializeGame(request);
        return mapper.sessionStateToDto(sessionState);
    }

    @Override
    public GameSessionStateDto nextTurn(UUID sessionId, JsonObject input) {

        GameSessionState sessionState = gameService.processGameTurn(sessionId, input);
        return mapper.sessionStateToDto(sessionState);
    }

}
