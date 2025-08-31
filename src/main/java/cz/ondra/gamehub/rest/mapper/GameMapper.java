package cz.ondra.gamehub.rest.mapper;

import com.google.gson.JsonObject;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Collection;
import java.util.List;

import cz.ondra.gamehub.config.MapperConfig;
import cz.ondra.gamehub.db.entity.GameInfo;
import cz.ondra.gamehub.db.entity.GameSession;
import cz.ondra.gamehub.model.GameSessionState;
import cz.ondra.gamehub.rest.dto.AvailableGameDto;
import cz.ondra.gamehub.rest.dto.GameSessionStateDto;
import cz.ondra.gamehub.rest.dto.OpenSessionDto;
import cz.ondra.gamehub.util.JsonUtil;

@Mapper(config = MapperConfig.class)
public interface GameMapper {

    @Mapping(target = "gameId", source = "id")
    AvailableGameDto entityToDto(GameInfo game);

    List<AvailableGameDto> entitiesToDtos(Collection<GameInfo> games);

    @Mapping(target = "currentState", expression = "java(mapCurrentStateToJson(sessionState.getCurrentState()))")
    @Mapping(target = "examplePlayerInput", expression = "java(mapCurrentStateToJson(sessionState.getExamplePlayerInput()))")
    GameSessionStateDto sessionStateToDto(GameSessionState sessionState);

    @Mapping(target = "sessionId", source = "id")
    @Mapping(target = "gameName", source = "gameInfo.name")
    OpenSessionDto gameSessionToDto(GameSession session);

    List<OpenSessionDto> gameSessionsToDto(Collection<GameSession> sessions);

    default JsonObject mapCurrentStateToJson(Object object) {
        return JsonUtil.convertToJsonObject(object);
    }
}
