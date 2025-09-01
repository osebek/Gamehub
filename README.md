# Gamehub
Backend server for player-vs-computer game processing. The application provides:
- Service handling game processing
- REST API supporting
  - game related requests
  - game result dashboard
- Interface definition to support building of custom games

## Available games
Current implementation provides following games:
- Rock, Scissors, Paper

## Further development
- Implement more games (e.g., Pexeso, Prší, Šachy :D)
- Support player-2-player game mode


## Local testing
- Run docker compose `env/gamehub/docker-compose.yml`
- Run the application
- Test with Postman
  - Import collection `env/postman/Gamehub.postman_collection.json`
  - Authorize via Auth tab in collection root
  - Execute predefined requests