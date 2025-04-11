# Application name (optional usage)
APP_NAME = sandbrick

# === Run Spring Boot in different profiles ===
run-dev:
	SPRING_PROFILES_ACTIVE=dev ./gradlew bootRun --args='--spring.config.import=optional:.env.dev[.properties]'
run-test:
	SPRING_PROFILES_ACTIVE=test ./gradlew bootRun --args='--spring.config.import=optional:.env.test[.properties]'
run-prod:
	SPRING_PROFILES_ACTIVE=prod ./gradlew bootRun --args='--spring.config.import=optional:.env.prod[.properties]'

# === Build the project ===
build:
	./gradlew clean build

# === Build the project for docker env ===
docker-build:
	docker build -t sandbrick:0.1.0 .
docker-up:
	docker-compose --env-file .env.prod up
docker-up-rebuild:
	docker-compose --env-file .env.prod up --build
docker-down:
	docker-compose --env-file .env.prod down
docker-logs:
	docker-compose logs -f

# === Run Flyway migrations using dev config ===
migrate:
	./gradlew flywayMigrate -Dspring.config.import=optional:.env.dev[.properties] -Dspring.profiles.active=dev

.PHONY: run-dev run-test run-prod build docker-build docker-up docker-up-rebuild docker-down docker-logs migrate
