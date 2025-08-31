package cz.ondra.gamehub.rest.dto;

import java.util.Map;

import cz.ondra.gamehub.model.GameDifficulty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GameStatsDto {
    private Map<String,Map<GameDifficulty, SingleGameStatsDto>> resultMap;
}
