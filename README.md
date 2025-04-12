# Sandbrick Platform

**Sandbrick** is a clean, secure and extendable backend foundation designed for corporate systems, internal tools, and enterprise-level applications. Built with Kotlin and Spring Boot, it emphasizes clarity, modularity, and scalability.

---

## 🧱 Stack

- **Language**: Kotlin 1.9
- **Framework**: Spring Boot 3.2
- **Database**: PostgreSQL 15+
- **ORM**: JPA (Hibernate)
- **Migrations**: Flyway
- **Security**: Spring Security + JWT
- **Build Tool**: Gradle (KTS)
- **Docs**: Swagger/OpenAPI 3 (SpringDoc)
- **Dotenv**: `dotenv-kotlin` for local/dev config injection

---

## 🚀 Getting Started

```bash
# 1. Clone the repo
git clone https://github.com/your-org/sandbrick.git

# 2. Build and start in dev mode
make build
make run-dev

# 3. Run DB migrations (dev)
make migrate-up
make docker-build
make docker-up
```
You’ll need .env.dev, .env.test, and .env.prod files configured accordingly.

🛠️ Main Features

## Robust user and role management

- JWT-based auth system (access + refresh)
- Profile and contact model with validation
- Flexible contact types (email, phone, messenger...)
- Flyway-controlled migrations and data seeding
- Swagger documentation with OpenAPI annotations
- Clean service/repository structure
- Security layer with fine-grained access controls

# 📚 Roadmap
See ROADMAP.md for future features.

# 📄 License
MIT License. See LICENSE for more information.
