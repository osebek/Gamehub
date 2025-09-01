package cz.ondra.gamehub.testdata.factory;

import java.util.Set;
import java.util.UUID;

import cz.ondra.gamehub.model.CurrentState;
import cz.ondra.gamehub.model.Game;
import cz.ondra.gamehub.model.GameConfiguration;
import cz.ondra.gamehub.model.GameDifficulty;
import cz.ondra.gamehub.model.InitializationOutput;
import cz.ondra.gamehub.model.PlayerInput;
import cz.ondra.gamehub.model.Status;
import cz.ondra.gamehub.service.executor.GameExecutor;
import cz.ondra.gamehub.testdata.model.TestCurrentState;
import cz.ondra.gamehub.testdata.model.TestGameConfiguration;
import cz.ondra.gamehub.testdata.model.TestPlayerInput;

public class ItTestDataFactory {

    public static UUID gameId = UUID.randomUUID();

    public static Game prepareTestGame() {
        Game game = new Game() {

            @Override
            public UUID getGameId() {
                return gameId;
            }

            @Override
            public String getName() {
                return "IT test game name";
            }

            @Override
            public String getDescription() {
                return "IT test game description";
            }

            @Override
            public Set<GameDifficulty> getDifficultyLevels() {
                return Set.of(GameDifficulty.NORMAL);
            }

            @Override
            public <R extends GameConfiguration, S extends PlayerInput, T extends CurrentState> GameExecutor<R, S, T> getGameExecutor() {
                return prepareGameExecutor();
            }

            @Override
            public PlayerInput getExamplePlayerInput() {
                return new TestPlayerInput();
            }
        };
        return game;
    }

    private static GameExecutor prepareGameExecutor() {
        GameExecutor gameExecutor = new GameExecutor<TestGameConfiguration, TestPlayerInput, TestCurrentState>() {

            @Override
            public Class<TestGameConfiguration> getConfigClazz() {
                return TestGameConfiguration.class;
            }

            @Override
            public Class<TestPlayerInput> getInputClazz() {
                return TestPlayerInput.class;
            }

            @Override
            public Class<TestCurrentState> getCurrentStateClazz() {
                return TestCurrentState.class;
            }

            @Override
            protected TestCurrentState processPlayersTurn(TestGameConfiguration gameConfiguration, TestPlayerInput input,
                                                          TestCurrentState currentState) {
                return new TestCurrentState();
            }

            @Override
            protected TestCurrentState executeComputerTurn(TestGameConfiguration gameConfiguration, TestPlayerInput input,
                                                           TestCurrentState currentState) {
                return new TestCurrentState();
            }

            @Override
            protected void validateInput(TestCurrentState currentState, TestPlayerInput input) {

            }

            @Override
            protected Status evaluateAfterTurn(TestCurrentState currentState) {
                return Status.OPEN;
            }

            @Override
            public InitializationOutput configureNewGame(GameDifficulty gameDifficulty) {
                return new InitializationOutput(new TestGameConfiguration(), new TestCurrentState());
            }
        };
        return gameExecutor;
    }
}
