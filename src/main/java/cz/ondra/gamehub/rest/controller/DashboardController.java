package cz.ondra.gamehub.rest.controller;

import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import cz.ondra.gamehub.db.entity.GameSession;
import cz.ondra.gamehub.model.GameDifficulty;
import cz.ondra.gamehub.model.Status;
import cz.ondra.gamehub.rest.api.DashboardApi;
import cz.ondra.gamehub.rest.dto.GameStatsDto;
import cz.ondra.gamehub.rest.dto.OpenSessionDto;
import cz.ondra.gamehub.rest.dto.SingleGameStatsDto;
import cz.ondra.gamehub.rest.mapper.GameMapper;
import cz.ondra.gamehub.service.DashboardService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class DashboardController implements DashboardApi {

    private final DashboardService dashboardService;
    private final GameMapper mapper;

    @Override
    public List<OpenSessionDto> findOpenGames() {
        List<GameSession> sessions = dashboardService.findOpenSessions();
        return mapper.gameSessionsToDto(sessions);
    }

    @Override
    public GameStatsDto getFinishedGamesStats() {
        List<GameSession> sessions = dashboardService.findClosedSessions();
        Map<String,Map<GameDifficulty, SingleGameStatsDto>> resultMap = new HashMap<>();
        Map<String,List<GameSession>> sessionsByGame = sessions.stream()
            .collect(Collectors.groupingBy(gs -> gs.getGameInfo().getName()));
        sessionsByGame.forEach((k,v) -> resultMap.put(k, collectGameStats(v)));
        return new GameStatsDto(resultMap);
    }

    private Map<GameDifficulty,SingleGameStatsDto> collectGameStats(List<GameSession> sessions) {
        Map<GameDifficulty,SingleGameStatsDto> map = new HashMap<>();
        Map<GameDifficulty,List<GameSession>> mapByDifficulty = sessions.stream()
            .collect(Collectors.groupingBy(GameSession::getDifficulty));
        Arrays.stream(GameDifficulty.values()).forEach(d -> map.put(d, collectSingleGameStats(mapByDifficulty.get(d))));
        return map;
    }

    private SingleGameStatsDto collectSingleGameStats(List<GameSession> sessions) {
        SingleGameStatsDto sgsDto = new SingleGameStatsDto();
        if (sessions == null) {
            return sgsDto;
        }

        sgsDto.setPlayed(sessions.size());
        long wonGames = sessions.stream().filter(s -> s.getStatus().equals(Status.WON)).count();
        sgsDto.setWon(wonGames);
        long lostGames = sessions.stream().filter(s -> s.getStatus().equals(Status.LOST)).count();
        sgsDto.setLost(lostGames);
        return sgsDto;
    }
}

