# Spring Boot Vinyl Ecommerce Backend

A backend REST API for a vinyl record ecommerce platform, built with Spring Boot, MyBatis, and JWT authentication. This README was expanded to include setup, common troubleshooting, and recommended improvements to help you get back into and improve the project quickly.

## Highlights

- User registration, JWT authentication
- Product catalog, categories, orders
- MyBatis mappers and SQL mapping files
- Configurable DB (H2 for dev, MySQL for production)

## Quick start

### Prerequisites

- Java 17+ installed and JAVA_HOME set
- Gradle wrapper (project includes gradlew)
- (Optional) Docker and Docker Compose for containerized run

### Run locally

1. From project root:
   ```bash
   ./gradlew clean build
   ./gradlew bootRun
   ```

### Run with Docker

1. Build and run:
   ```bash
   docker build -t vinyl-ecommerce-backend .
   docker-compose up
   ```

## API

- Controllers are under `src/main/java/.../rest`. Use Postman or your preferred HTTP client. (Swagger may be added later.)

## Tests

- Run tests with: `./gradlew test`

## Troubleshooting (common issues you encountered)

1) "No candidates found for method call" in the editor or long delays when reopening the project
- Likely an IDE-sync/indexing or annotation-processing issue (IntelliJ/VS Code).
- Actions to try:
  - Invalidate caches / Restart (IntelliJ: File > Invalidate Caches / Restart).
  - Re-import the Gradle project (refresh Gradle in the IDE).
  - Enable annotation processing (IntelliJ: Settings > Build, Execution, Deployment > Compiler > Annotation Processors).
  - Install/enable Lombok plugin if Lombok is used and enable annotation processing.
  - Run a full build from terminal: `./gradlew clean build --refresh-dependencies`.

2) "Java HotSpot(TM) 64-Bit Server VM warning: Sharing is only supported for boot loader classes because bootstrap classpath has been appended"
- This is a JVM warning related to Class Data Sharing (CDS) and bootstrap classpath modifications. It's usually harmless for development.
- To suppress it: remove any custom `-Xbootclasspath/a` or similar JVM options that append to the bootstrap classpath, or disable CDS when running the app (e.g., set `-Xshare:off` in your run configuration or `JAVA_TOOL_OPTIONS`).

3) "Deprecated Gradle features were used in this build, making it incompatible with Gradle 9.0."
- Identify the deprecations: run the build with full warnings: `./gradlew build --warning-mode=all`
- Update the Gradle wrapper to a current 8.x release and then fix deprecation warnings in build scripts (`build.gradle`, `settings.gradle`, plugins block). Example to update wrapper:
  ```bash
  ./gradlew wrapper --gradle-version 8.x
  ```
- Fix the warnings reported and re-run the build until no deprecations remain.

## Suggested improvements and next steps
- CI: Add GitHub Actions to run build and tests on push/PR.
- Code quality: Add SpotBugs, Checkstyle/PMD, and configure a code formatter (e.g., Spotless).
- Documentation: Add OpenAPI/Swagger for API documentation.
- Containerization: Multi-stage Dockerfile and healthcheck in docker-compose.
- Secrets: Move credentials out of application.yml and into environment variables or Vault for production.
- Gradle modernisation: Move legacy configuration to plugins DSL and replace deprecated APIs.
- Tests: Add integration tests that run against an ephemeral database (Testcontainers).

## Contributing
- Fork, create a branch, make changes, run tests, open a PR.

## License
MIT

---
If you want, I can:
- Update this README in the repo (done).
- Run the Gradle deprecation scan and list exact places to fix.
- Create a CI workflow and a Gradle wrapper update.
Tell me which you'd like next.
