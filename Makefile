DEV_ENV_FILE = .env.dev
TEST_ENV_FILE = .env.test
PROD_ENV_FILE = .env.prod

APP_IMAGE_NAME = sandbrick:0.1.0
NETWORK_NAME = app-network
CONTAINER_NAME = postgres
PORT = 5432

.PHONY: up-dev up-test up-prod build

build:
	@docker build -t $(APP_IMAGE_NAME) .
up-dev: build
	@export $(cat $(DEV_ENV_FILE) | xargs) && docker-compose --env-file .env.dev up
up-test: build
	@export $(cat $(TEST_ENV_FILE) | xargs) && docker-compose --env-file .env.test up
up-prod: build
	@export $(cat $(PROD_ENV_FILE) | xargs) && docker-compose --env-file .env.prod up

.PHONY: down
down:
	@docker-compose down

.PHONY: restart
restart: down up-dev

.PHONY: status
status:
	@docker-compose ps

.PHONY: clean
clean:
	@docker-compose down -v
