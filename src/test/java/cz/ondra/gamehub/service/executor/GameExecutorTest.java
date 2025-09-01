package cz.ondra.gamehub.service.executor;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.internal.matchers.apachecommons.ReflectionEquals;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import cz.ondra.gamehub.model.CurrentState;
import cz.ondra.gamehub.model.DataOutput;
import cz.ondra.gamehub.model.PlayerInput;
import cz.ondra.gamehub.testdata.model.TestPlayerInput;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class GameExecutorTest {

    @Test
    void executeSingleTurn_returnsValidResult() {
        GameExecutor mockExecutor = ExecutorMockFactory.prepareGameExecutorMock();
        PlayerInput input = new TestPlayerInput();


        DataOutput output = mockExecutor.executeSingleTurn(ExecutorMockFactory.afterInitConfiguration,
            input, ExecutorMockFactory.afterInitCurrentState);

        verify(mockExecutor).validateInput(ExecutorMockFactory.afterInitCurrentState, input);
        verify(mockExecutor).processPlayersTurn(ExecutorMockFactory.afterInitConfiguration,
            input, ExecutorMockFactory.afterInitCurrentState);
        verify(mockExecutor).executeComputerTurn(ExecutorMockFactory.afterInitConfiguration,
            input, ExecutorMockFactory.afterPlayerTurnCurrentState);
        verify(mockExecutor).evaluateAfterTurn(ExecutorMockFactory.afterComputerTurnCurrentState);

        CurrentState actualState = output.currentState();
        Assertions.assertTrue(new ReflectionEquals(ExecutorMockFactory.afterComputerTurnCurrentState).matches(actualState));

        Assertions.assertEquals(ExecutorMockFactory.afterTurnStatus, output.status());
    }
}
