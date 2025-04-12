# 🧱 Sandbrick

**Sandbrick** is a modern, modular backend system built with Kotlin and Spring Boot. Designed for enterprise-grade extensibility and clarity, it provides a solid foundation for secure user management, token-based authentication, and multi-role workflows.

---

## 🚀 Features

- ✅ Kotlin + Spring Boot 3.2
- ✅ REST API with Swagger/OpenAPI docs
- ✅ JWT-based Authentication (Access + Refresh tokens)
- ✅ Role-based Authorization (`ADMIN`, `USER`, etc.)
- ✅ Flyway Migrations
- ✅ Profile and Contact management
- ✅ PostgreSQL & Docker support
- ✅ Clean Architecture with DTO → Mapper → Entity pattern
- ✅ Extensible design ready for microservices

---

## 📁 Project Structure

```plaintext
.
├── build.gradle.kts           # Gradle build configuration
├── Dockerfile                 # Docker image definition
├── docker-compose.yaml        # Local dev container config
├── Makefile                   # CLI task automation
├── .env.*                     # Profile-specific environment configs
├── src/
│   ├── main/
│   │   ├── kotlin/
│   │   │   └── com/sandbrick/sbp/
│   │   │       ├── api/           # REST controllers & DTOs
│   │   │       ├── config/        # App config (security, props, etc.)
│   │   │       ├── domain/        # JPA entities
│   │   │       ├── mapper/        # DTO ↔ Entity mappers
│   │   │       ├── repository/    # Spring Data JPA interfaces
│   │   │       ├── service/       # Business logic
│   │   │       └── util/          # Utilities (ID gen, extensions, etc.)
│   ├── resources/
│   │   ├── application-*.yaml    # Per-profile configs
│   │   └── db/migration/         # Flyway SQL migrations
└── README.md
```

### 📚 API Docs

API is documented with OpenAPI 3 / Swagger UI.

🔐 All endpoints require authentication via Bearer tokens (Authorization: Bearer <token>)

### 🧪 Running Locally

### Run development server with dev profile

```bash
make run-dev
```

### Run PostgreSQL + app in Docker

```bash
make docker-up
```

See .env.dev, .env.prod, .env.test for configuration.

### 🔧 Migration & Database

```bash
# Run migrations (Flyway) for dev profile
make migrate-up

# Clean dev DB (⚠ dangerous!)
make migrate-down
```

### 🌍 Live Preview

🧱 Hosted with HubSpot Landing Pages

### 📌 Roadmap
See ROADMAP.md for detailed feature planning, tech debt, and goals.

### 📄 Legal & Guidelines

- License (MIT)
- Contribution Guidelines
- Code of Conduct
- Security Policy

### 🤝 Maintainers
Built by @basilex with ❤️

