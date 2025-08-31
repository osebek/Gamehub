package cz.ondra.gamehub.rest.dto;

import java.util.Set;
import java.util.UUID;

import lombok.Data;

@Data
public class AvailableGameDto {

    private UUID gameId;
    private String name;
    private String description;
    private Set<String> difficultyLevels;
}
