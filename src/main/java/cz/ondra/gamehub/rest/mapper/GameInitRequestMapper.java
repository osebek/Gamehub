package cz.ondra.gamehub.rest.mapper;

import org.mapstruct.Mapper;

import cz.ondra.gamehub.config.MapperConfig;
import cz.ondra.gamehub.model.GameInitRequest;
import cz.ondra.gamehub.rest.dto.GameInitRequestDto;

@Mapper(config = MapperConfig.class)
public interface GameInitRequestMapper {

    GameInitRequest dtoToEntity(GameInitRequestDto initRequest);

}
