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
- ✅ Full Makefile automation for dev/test/prod + test lifecycle
- ✅ Unit testing setup (JUnit 5) + Xid generator covered
- ✅ Email Verification Flow (HTML template, token via mail, confirmation handler)
- ✅ Thymeleaf-based email templates
- ✅ Email GET confirmation with styled HTML views

---

## 🧱 Core Development Milestones

### 1. Backend Auth Enhancements
- [x] Password Reset Flow
- [x] Email Verification Flow
- [ ] 2FA via Authenticator App or SMS/Email

### 2. Web Interface Strategy
- [x] Bootstrap theme evaluation (e.g. SB Admin Pro)
- [ ] Decide on templating engine (Thymeleaf confirmed for email views)
- [ ] Define design guidelines (strict, minimal, consistent)
- [ ] Build responsive authentication and dashboard UI

### 3. Test Strategy Upgrade
- [x] Test lifecycle consistency across Makefile and Flyway
- [ ] Add integration tests with Testcontainers
- [ ] Increase test coverage for auth & user flows

### 4. Microservice Preparation (Future Phase)
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
- Audit logs on key flows (login, verify, reset)

---

## 🙌 Contributions

We welcome contributions! See [`CONTRIBUTING.md`](./CONTRIBUTING.md) for how to get started.

---

_Last updated: April 2025_
