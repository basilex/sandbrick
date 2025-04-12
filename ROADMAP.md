# 🧭 Sandbrick Roadmap

This file outlines the key development phases and upcoming milestones for the Sandbrick platform.  
The goal is to provide a clear, maintainable backend system, with strong security and a clean API-first design.

---

## ✅ Phase 1: Foundation (Completed)

- [x] Project skeleton with Gradle + Kotlin + Spring Boot
- [x] PostgreSQL + Flyway DB versioning
- [x] Clean domain-driven structure (Entity, DTO, Mapper, Controller, Service)
- [x] Role & User management
- [x] JWT-based authentication (Access + Refresh tokens)
- [x] Contact system with validation & flexible types (Email, Phone, etc.)
- [x] Swagger/OpenAPI documentation
- [x] Docker integration for production
- [x] Admin UI-ready APIs (filtered, paginated)
- [x] Security layers & custom access policies

---

## 🔄 Phase 2: Enhancement (In Progress)

- [ ] Complete Swagger annotations across all DTOs and endpoints
- [ ] Add full audit logging (create/update/delete timestamps + user context)
- [ ] Global validation rules from `AppProperties` (e.g. min/max password length)
- [ ] Fine-grained exception handling (custom handlers, error codes)
- [ ] Admin UI layout: decision on frontend stack (e.g. Thymeleaf vs React)
- [ ] Sample seed data and migrations in test profile

---

## 🔐 Phase 3: Advanced Auth & Security

- [ ] Password reset flow
- [ ] Email verification on signup
- [ ] Multi-factor authentication (2FA with TOTP or Email/SMS)
- [ ] Session-based token revocation
- [ ] Account lockout after multiple failed logins

---

## 🌐 Phase 4: Web Interface

- [ ] Define clean corporate UI guidelines (branding, color, layout)
- [ ] Setup template engine or SPA frontend (Bootstrap, Thymeleaf, or React)
- [ ] Develop reusable form components (inputs, selects, error handling)
- [ ] Integrate with existing backend via REST
- [ ] Implement login, registration, dashboard, admin panels

---

## 📦 Phase 5: Deployment & Monitoring

- [ ] Environment-specific Helm/Docker configurations
- [ ] Production-ready Docker builds
- [ ] Health checks, metrics, and actuator endpoints
- [ ] Setup basic monitoring with Prometheus/Grafana or ELK

---

## ✨ Nice to Have

- [ ] Internationalization (i18n) support
- [ ] Event-based architecture with Spring Events or Kafka
- [ ] Admin dashboard metrics
- [ ] Custom email templates for notifications
- [ ] Rate limiting, brute force prevention

---

## 🤝 Contribution Guidelines

Please read our [CONTRIBUTING.md](CONTRIBUTING.md) and [CODE_OF_CONDUCT.md](CODE_OF_CONDUCT.md) before opening issues or pull requests.

---

Last updated: `April 12, 2025`

Maintained by: `@basilex` and Sandbrick contributors
