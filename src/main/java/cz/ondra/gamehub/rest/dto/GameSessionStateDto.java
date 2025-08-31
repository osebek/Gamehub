package cz.ondra.gamehub.rest.dto;

import com.google.gson.JsonObject;

import java.util.UUID;

import lombok.Data;

@Data
public class GameSessionStateDto {

    private UUID gameSessionId;
    private String status;
    private String description;
    private JsonObject currentState;
    private JsonObject examplePlayerInput;
}
