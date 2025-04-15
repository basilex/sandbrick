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

```
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
│   │   └── templates/            # Thymeleaf templates (emails + views)
└── README.md
```

---

## 📚 API Docs

- Swagger UI: `http://localhost:8081/swagger-ui.html`
- OpenAPI JSON: `http://localhost:8081/v3/api-docs`
- All endpoints use Bearer token auth:  
  `Authorization: Bearer <access_token>`

---

## 🧪 Running Locally

### Development server

```bash
make run-clean-dev
```

This will clean, migrate, build, and start your local dev environment.

### PostgreSQL + App in Docker

```bash
make docker-up
```

Stop with:

```bash
make docker-down
```

> See `.env.dev`, `.env.prod`, `.env.test` for database and app configuration.

---

## 🔧 Migrations (Flyway)

```bash
# Run migrations for dev profile
make migrate-up

# Clean dev DB (⚠ dangerous!)
make migrate-down

# Run/reset test DB
make run-clean-test
```

---

## 📬 Email Verification Flow

- Verification tokens sent via SMTP (Mailtrap or other)
- HTML templates using Thymeleaf (`templates/email/verification.html`)
- Friendly confirmation page (also using Thymeleaf)
- Secure, token-based endpoint:  
  `GET /api/v1/auth/verify-email/confirm?token=...`

---

## 🌍 Live Preview

🧱 Hosted with HubSpot Landing Pages  
👉 *Coming soon*

---

## 📌 Roadmap

See [`ROADMAP.md`](./ROADMAP.md) for upcoming milestones and goals.

---

## 📄 Legal & Guidelines

- [MIT License](./LICENSE)
- [Contribution Guidelines](./CONTRIBUTING.md)
- [Code of Conduct](./CODE_OF_CONDUCT.md)
- [Security Policy](./SECURITY.md)

---

## 🙌 Maintainers

Built by [@basilex](https://github.com/basilex) with ❤️  
Contributions welcome!
