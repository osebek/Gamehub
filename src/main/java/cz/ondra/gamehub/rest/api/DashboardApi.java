package cz.ondra.gamehub.rest.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import cz.ondra.gamehub.rest.dto.GameStatsDto;
import cz.ondra.gamehub.rest.dto.OpenSessionDto;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Dashboard")
@RequestMapping("/dashboard")
public interface DashboardApi {

    @GetMapping("/open-games")
    public List<OpenSessionDto> findOpenGames();

    @GetMapping("/stats")
    public GameStatsDto getFinishedGamesStats();
}
