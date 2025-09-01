package cz.ondra.gamehub.service;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.internal.matchers.apachecommons.ReflectionEquals;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import cz.ondra.gamehub.db.entity.GameInfo;
import cz.ondra.gamehub.db.repository.GameInfoRepository;
import cz.ondra.gamehub.exception.GamehubException;
import cz.ondra.gamehub.testdata.factory.UnitTestDataFactory;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GameInfoServiceTest {

    @Mock
    private GameInfoRepository gameInfoRepository;

    @InjectMocks
    private GameInfoService gameInfoService;

    @Test
    void createGameInfo_returnsResult() {
        GameInfo info = UnitTestDataFactory.prepareGameInfo();
        when(gameInfoRepository.save(info)).thenReturn(info);

        GameInfo createdInfo = gameInfoService.createGameInfo(info);

        Assertions.assertTrue(new ReflectionEquals(info).matches(createdInfo));
    }

    @Test
    void getById_returnsResult() {
        GameInfo info = UnitTestDataFactory.prepareGameInfo();
        when(gameInfoRepository.findById(info.getId())).thenReturn(Optional.of(info));

        GameInfo foundInfo = gameInfoService.getById(info.getId());

        Assertions.assertTrue(new ReflectionEquals(info).matches(foundInfo));
    }

    @Test
    void getById_throwsExceptionWhenNotFound() {
        GameInfo info = UnitTestDataFactory.prepareGameInfo();
        when(gameInfoRepository.findById(info.getId())).thenReturn(Optional.empty());

        Exception ex = Assertions.assertThrows(GamehubException.class, () -> gameInfoService.getById(info.getId()));
        Assertions.assertEquals("Game info not found for specified id", ex.getMessage());
    }

    @Test
    void getAvailableGames_returnsResult() {
        GameInfo info1 = UnitTestDataFactory.prepareGameInfo();
        GameInfo info2 = UnitTestDataFactory.prepareGameInfo();
        List<GameInfo> storedInfos = List.of(info1, info2);
        when(gameInfoRepository.findAll()).thenReturn(storedInfos);

        Collection<GameInfo> foundInfos = gameInfoService.getAvailableGames();

        assertThat(storedInfos, Matchers.containsInAnyOrder(foundInfos.toArray()));
    }
}
