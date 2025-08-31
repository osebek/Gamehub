package cz.ondra.gamehub.service;

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
import cz.ondra.gamehub.testmodel.TestPlayerInput;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class GameExecutorTest {

    @Test
    void executeSingleTurn_returnsValidResult() {
        GameExecutor mockExecutor = TestDataFactory.prepareGameExecutorMock();
        PlayerInput input = new TestPlayerInput();


        DataOutput output = mockExecutor.executeSingleTurn(TestDataFactory.afterInitConfiguration,
            input, TestDataFactory.afterInitCurrentState);

        verify(mockExecutor).validateInput(TestDataFactory.afterInitCurrentState, input);
        verify(mockExecutor).processPlayersTurn(TestDataFactory.afterInitConfiguration,
            input, TestDataFactory.afterInitCurrentState);
        verify(mockExecutor).executeComputerTurn(TestDataFactory.afterInitConfiguration,
            input, TestDataFactory.afterPlayerTurnCurrentState);
        verify(mockExecutor).evaluateAfterTurn(TestDataFactory.afterComputerTurnCurrentState);

        CurrentState actualState = output.currentState();
        Assertions.assertTrue(new ReflectionEquals(TestDataFactory.afterComputerTurnCurrentState).matches(actualState));

        Assertions.assertEquals(TestDataFactory.afterTurnStatus, output.status());
    }
}
