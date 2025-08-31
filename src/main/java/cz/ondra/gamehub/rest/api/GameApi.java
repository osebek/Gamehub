package cz.ondra.gamehub.rest.api;

import com.google.gson.JsonObject;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.UUID;

import cz.ondra.gamehub.rest.dto.AvailableGameDto;
import cz.ondra.gamehub.rest.dto.GameInitRequestDto;
import cz.ondra.gamehub.rest.dto.GameSessionStateDto;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Game")
@RequestMapping("/games")
public interface GameApi {

    @GetMapping
    List<AvailableGameDto> getAvailableGames();

    @PostMapping
    GameSessionStateDto initializeGame(@RequestBody @Validated GameInitRequestDto initRequestDto);

    @PostMapping("{sessionId}/next-turn")
    GameSessionStateDto nextTurn(@PathVariable UUID sessionId, @RequestBody JsonObject input);
}
