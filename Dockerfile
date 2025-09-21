FROM gradle:8.6-jdk21 AS builder
WORKDIR /home/gradle/project

# Cache Gradle dependencies
COPY --chown=gradle:gradle gradle gradle
COPY --chown=gradle:gradle gradlew gradlew
COPY --chown=gradle:gradle gradle/wrapper gradle/wrapper

COPY --chown=gradle:gradle build.gradle settings.gradle ./
COPY --chown=gradle:gradle pom.xml ./

RUN ./gradlew --version || true

# Copy the rest of the source and build
COPY --chown=gradle:gradle . .
RUN ./gradlew clean bootJar --no-daemon

# Run stage
FROM eclipse-temurin:21-jre-jammy
ARG JAR_FILE=build/libs/*.jar
COPY --from=builder /home/gradle/project/build/libs/*.jar /app/app.jar
EXPOSE 4242
ENTRYPOINT ["java","-jar","/app/app.jar"]
