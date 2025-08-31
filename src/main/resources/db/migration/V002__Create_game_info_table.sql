CREATE TABLE game_info (
    id UUID PRIMARY KEY,
    name TEXT NOT NULL,
    description TEXT NOT NULL,
    difficulty_levels VARCHAR(10)[]
);
