# 📍 Sandbrick Roadmap

This document outlines the current and upcoming priorities for the Sandbrick backend.

---

## ✅ Recently Completed

- ✅ Profile → Contact refactor (multi-channel contact support)
- ✅ Swagger documentation added across all DTOs and controllers
- ✅ Validation rules annotated and aligned across all entities
- ✅ Flyway migration modularity with `.env`-based profiles
- ✅ Token management: filtering, pagination, admin control
- ✅ SecurityService abstraction per entity (Contact, Profile, etc.)
- ✅ Unit testing setup (JUnit 5) + first test for Xid generator
- ✅ Full Makefile automation for dev/test/prod + test lifecycle

---

## 🧱 Core Development Milestones

### 1. Swagger Documentation Cleanup ✅ *(Completed)*
- [x] Consistent `@Schema` annotations across all DTOs
- [x] Example values and validation rules added

### 2. Backend Auth Enhancements
- [ ] Password Reset Flow
- [ ] Email Verification Flow
- [ ] 2FA via Authenticator App or SMS/Email

### 3. Web Interface Strategy
- [x] Bootstrap theme evaluation (e.g. SB Admin Pro)
- [ ] Decide on templating engine (e.g. Thymeleaf vs REST frontend)
- [ ] Define design guidelines (strict, minimal, consistent)
- [ ] Build responsive authentication and dashboard UI

### 4. Transition to Microservices (Future Phase)
- [ ] Define service boundaries:
  - API Gateway
  - Auth Service
  - Mailer
  - Audit Logging
  - User Service
- [ ] Add Kafka support for async communication
- [ ] Service discovery & configuration (e.g. Spring Cloud, Consul, Eureka)

---

## 📎 Organizational & Infrastructure

- [x] LICENSE (MIT)
- [x] CONTRIBUTING.md
- [x] CODE_OF_CONDUCT.md
- [x] SECURITY.md
- [x] GitHub Actions (future CI/CD)
- [ ] GitHub Discussions (community support)

---

## 🧠 Brainstorm & Ideas

- Invite-based registration
- Organization / Teams per user
- Rate-limiting on login & token refresh
- i18n/l10n support
- Extended contact types: Slack, Discord, LinkedIn

---

## 🙌 Contributions

We welcome contributions! See [`CONTRIBUTING.md`](./CONTRIBUTING.md) for how to get started.

---

_Last updated: April 2025_
