# 🧱 Sandbrick Backend

This is the backend service for the **Sandbrick** project, built with **Kotlin**, **Spring Boot**, and **PostgreSQL**. The application is modular, secure, and includes full support for authentication, authorization, refresh tokens, role-based access control, and more.

---

## 🚀 Tech Stack

- **Language**: Kotlin
- **Framework**: Spring Boot
- **Database**: PostgreSQL
- **ORM**: Spring Data JPA + Hibernate
- **Security**: Spring Security + JWT (access/refresh)
- **API Docs**: SpringDoc OpenAPI (Swagger UI)
- **Validation**: Hibernate Validator
- **Token Format**: XID (compact string-based IDs)

---

## ✅ Features

- JWT-based **authentication** and **authorization**
- **Refresh token** management with expiration tracking
- Secure **role-based access control**
- One-to-one **User/Profile** entity mapping
- Centralized **exception handling**
- Built-in **pagination** (example: `/api/v1/countries`)
- Clean DTO separation for requests/responses
- Swagger UI at `/swagger-ui.html` or `/v3/api-docs`
- Timezone-aware timestamps (UTC)

---

## 🔐 Auth Flow

- `POST /api/v1/auth/register` — create a new user, auto-login
- `POST /api/v1/auth/login` — get access & refresh tokens
- `POST /api/v1/auth/refresh` — renew access token
- `POST /api/v1/auth/logout` — invalidate tokens

---

## 🔐 Authentication Flow

### Login/Register
- Both endpoints return an `accessToken` and `refreshToken`.

### Refresh Token
- POST `/api/v1/auth/refresh` with valid refresh token.
- Generates and stores a new access + refresh token pair.

### Logout
- Revokes all active user tokens on login or manually via service.

---

## 📄 Swagger UI

Available at:

http://localhost:8081/swagger-ui.html

---

## ⚙️ Setup Instructions

1. Clone repo:

```bash
git clone https://github.com/your-org/sandbrick-platform.git
cd sandbrick-platform
Configure environment variables via .env or application.yml.

Start PostgreSQL and update connection credentials.

Run the app:

./gradlew bootRun
```

📦 Example Endpoints

Register

```
POST /api/v1/auth/register
{
"username": "sepa",
"email": "sepa@domain.com",
"password": "password123"
}
```

Login

```
POST /api/v1/auth/login
{
"username": "sepa",
"password": "password123"
}
```

Refresh Token

```
POST /api/v1/auth/refresh
{
"refreshToken": "eyJhbGciOi..."
}
```

🧪 Running Tests

```
./gradlew test
```

📌 Notes

Makefile added with useful entry points for dev process.

Tokens are stored in a token table with revoked and expired flags.

Profile entity is 1:1 with User.

Country-Currency relationship is many-to-many with mapping table.

Uses XID over UUIDs for lighter keys & DB performance.

📃 License
MIT or your custom license.

👨‍💻 Author
Created with ☕ by your team at Sandbrick
