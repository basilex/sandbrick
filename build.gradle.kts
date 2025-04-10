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
		languageVersion = JavaLanguageVersion.of(21)
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("org.springframework.boot:spring-boot-starter-actuator")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-web")

	implementation("org.springframework.security:spring-security-crypto")

	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.3.0")

	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("io.github.cdimascio:dotenv-kotlin:6.3.1")
	implementation("org.jetbrains.kotlin:kotlin-reflect")

	implementation("org.flywaydb:flyway-core")

	implementation("io.jsonwebtoken:jjwt-api:0.11.5")
	runtimeOnly("io.jsonwebtoken:jjwt-impl:0.11.5")
	runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.11.5")

	runtimeOnly("org.postgresql:postgresql:42.7.2")

	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
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
		jvmTarget = "21"
	}
}

tasks.withType<JavaExec> {
	jvmArgs = listOf("-Duser.timezone=UTC")
}

tasks.withType<Test> {
	useJUnitPlatform()
}
