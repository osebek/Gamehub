package cz.ondra.gamehub.rest.dto;

import java.util.UUID;

import lombok.Data;

@Data
public class OpenSessionDto {

    private UUID sessionId;
    private String gameName;
    private String difficulty;
}
