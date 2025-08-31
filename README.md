# Gamehub

The application provides:
- Service handling game processing
- REST API supporting
  - game requests
  - game result dashboard
- Interface definition to support building of custom games


## Local testing
- Run docker compose `env/gamehub/docker-compose.yml`
- Run the application
- Test with Postman
  - Import collection `env/postman/Gamehub.postman_collection.json`
  - Authorize via Auth tab in collection root
  - Execute predefined requests