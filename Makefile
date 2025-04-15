# Application name (optional usage)
APP_NAME = sandbrick

# === Build the project ===
clean:
	./gradlew clean
test:
	./gradlew test
build: clean
	./gradlew build -x test
.PHONY: build clean test

# === Run Spring Boot in different profiles ===

run-clean-dev: migrate-down migrate-up build run-dev
run-clean-test: migrate-test-down migrate-test-up build run-test
.PHONY: run-clean-dev run-clean-test

run-dev:
	SPRING_PROFILES_ACTIVE=dev ./gradlew bootRun --args='--spring.config.import=optional:.env.dev[.properties]'
run-test:
	SPRING_PROFILES_ACTIVE=test ./gradlew bootRun --args='--spring.config.import=optional:.env.test[.properties]'
run-prod:
	SPRING_PROFILES_ACTIVE=prod ./gradlew bootRun --args='--spring.config.import=optional:.env.prod[.properties]'
.PHONY: run-dev run-test run-prod

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
.PHONY:	docker-build docker-up docker-up-rebuild docker-down docker-logs

# === Run Flyway migrations using dev config ===

migrate-up:
	env $(cat .env.dev | xargs) ./gradlew \
		-Dflyway.cleanDisabled=false \
		-Dspring.config.import=optional:.env.dev[.properties] \
		-Dspring.profiles.active=dev flywayMigrate
migrate-down:
	env $(cat .env.dev | xargs) ./gradlew \
		-Dflyway.cleanDisabled=false \
		-Dspring.config.import=optional:.env.dev[.properties] \
		-Dspring.profiles.active=dev flywayClean
.PHONY: migrate-up migrate-down

# === Run Flyway migrations using test config ===

migrate-test-up:
	env $(cat .env.test | xargs) ./gradlew \
		-Dflyway.cleanDisabled=false \
		-Dspring.config.import=optional:.env.test[.properties] \
		-Dspring.profiles.active=test flywayMigrate
migrate-test-down:
	env $(cat .env.test | xargs) ./gradlew \
		-Dflyway.cleanDisabled=false \
		-Dspring.config.import=optional:.env.test[.properties] \
		-Dspring.profiles.active=test flywayClean
.PHONY: migrate-test-up migrate-test-down

# === Reset test DB completely ===

migrate-reset-testdb: migrate-test-down migrate-test-up
.PHONY: migrate-reset-testdb
