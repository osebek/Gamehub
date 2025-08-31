package cz.ondra.gamehub.rest.dto;

import java.util.UUID;

import lombok.Data;

@Data
public class GameInitRequestDto {
    private UUID gameId;
    private String difficulty;
}
