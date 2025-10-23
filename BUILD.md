# Build Instructions

## Prerequisites

- Java 21 or higher
- Gradle 8.10 (or use the wrapper if available)

## Known Issues

### Gradle Wrapper Issue

The Gradle wrapper (`gradlew.bat`) is currently not functional due to missing `GradleWrapperMain` class. 

**Workaround:**

1. **Install Gradle manually:**
   - Download from: https://gradle.org/releases/
   - Extract and add to PATH
   
2. **Use IntelliJ IDEA / Eclipse:**
   - Open project
   - Let IDE download dependencies automatically
   - Use IDE's built-in Gradle support

3. **Rebuild wrapper (if you have Gradle installed):**
   ```bash
   gradle wrapper --gradle-version 8.10
   ```

## Building the Project

### With IDE (Recommended)
1. Open project in IntelliJ IDEA or Eclipse
2. Wait for IDE to sync dependencies
3. Run/Debug using IDE tools

### With Gradle (if installed)
```bash
# Build without tests
gradle build -x test

# Build with tests
gradle build

# Run application
gradle bootRun
```

### Running JAR directly
```bash
# After successful build
java -jar build/libs/springbootvinylecommercebackend-0.0.1-SNAPSHOT.jar
```

## Troubleshooting

### Import Errors in IDE

If you see import errors for:
- `org.springframework.boot.actuate.*`
- `io.micrometer.core.*`

**Solution:**
1. Refresh/Reload Gradle project in IDE
2. Wait for dependency download
3. Rebuild project

### Dependencies Not Found

The following dependencies require successful Gradle sync:
- Spring Boot Actuator
- Micrometer Prometheus Registry
- Logstash Logback Encoder

These are correctly defined in `build.gradle` but need to be downloaded.
