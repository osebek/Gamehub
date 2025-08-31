CREATE TABLE game_session (
    id UUID PRIMARY KEY,
    game_info_id UUID NOT NULL,
    player_id UUID NOT NULL,
    create_timestamp TIMESTAMP NOT NULL,
    status VARCHAR(10) NOT NULL,
    difficulty VARCHAR(10) NOT NULL,
    configuration TEXT,
    current_state TEXT
);