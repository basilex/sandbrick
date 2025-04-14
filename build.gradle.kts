import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

group = "com.sandbrick"
version = "0.1.0-SNAPSHOT"

plugins {
	kotlin("jvm") version "1.9.25"
	kotlin("plugin.jpa") version "1.9.25"
	kotlin("plugin.spring") version "1.9.25"
	id("org.springframework.boot") version "3.2.4"
	id("io.spring.dependency-management") version "1.1.4"
	id("org.flywaydb.flyway") version "9.22.3"

}

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(17)
	}
}

repositories {
	mavenCentral()
}

dependencies {
	// Core Spring Boot
	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("org.springframework.boot:spring-boot-starter-actuator")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-web")

	// Security crypto
	implementation("org.springframework.security:spring-security-crypto")

	// OpenAPI / Swagger
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.3.0")

	// JSON & Kotlin
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")

	// Env file support
	implementation("io.github.cdimascio:dotenv-kotlin:6.3.1")

	// Flyway migrations
	implementation("org.flywaydb:flyway-core")

	// JWT (JSON Web Tokens)
	implementation("io.jsonwebtoken:jjwt-api:0.11.5")
	runtimeOnly("io.jsonwebtoken:jjwt-impl:0.11.5")
	runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.11.5")

	// PostgreSQL driver
	runtimeOnly("org.postgresql:postgresql:42.7.2")

	// TESTING
	testImplementation("org.springframework.boot:spring-boot-starter-test") {
		exclude(group = "org.junit.vintage", module = "junit-vintage-engine") // отключаем JUnit 4
	}
	testImplementation("org.junit.jupiter:junit-jupiter-api:5.10.0")
	testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.10.0")
}

flyway {
	url = "jdbc:postgresql://${System.getenv("POSTGRES_HOST") ?: "localhost"}:${System.getenv("POSTGRES_PORT") ?: "5432"}/${System.getenv("POSTGRES_DB") ?: "sbpdb_dev"}"
	user = System.getenv("POSTGRES_USER") ?: "system"
	password = System.getenv("POSTGRES_PASSWORD") ?: "passw0rd"
	locations = arrayOf("classpath:db/migration")
	baselineOnMigrate = true
}

allOpen {
	annotation("jakarta.persistence.Entity")
	annotation("jakarta.persistence.MappedSuperclass")
	annotation("jakarta.persistence.Embeddable")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "17"
	}
}

tasks.withType<JavaExec> {
	jvmArgs = listOf("-Duser.timezone=UTC")
}

tasks.withType<Test> {
	useJUnitPlatform()
}
