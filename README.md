# Spring Boot Vinyl Ecommerce Backend

A backend REST API for a vinyl record ecommerce platform, built with Spring Boot, MyBatis, and JWT authentication. Supports user management, product catalog, order processing, and secure authentication.

## Features

- User registration, login, and JWT-based authentication
- Product catalog management
- Order creation and tracking
- Category management
- Secure API endpoints
- Configurable database support (H2/MySQL)

## Tech Stack

- **Java 17+**
- **Spring Boot**
- **Gradle**
- **MyBatis**
- **H2/MySQL** (Database)
- **JWT** (Authentication)

## Getting Started

### Prerequisites

- Java 17 or higher
- Gradle 7.x or higher
- MySQL (or H2 for development)

### Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/Trikyngucii/springboot-vinyl-ecommerce-backend.git
   cd springboot-vinyl-ecommerce-backend
   ```
2. Configure your database in `src/main/resources/application.yml` or use the default H2 settings for development.
3. (Optional) For MySQL, ensure your database is running and update credentials in the config file.

### Build & Run

To build and start the application:

```bash
./gradlew build
./gradlew bootRun
```

Or use Docker:

```bash
docker build -t vinyl-ecommerce-backend .
docker-compose up
```

### API Documentation

API endpoints are defined in the `rest` package. You can use tools like Postman or Swagger (if enabled) to explore the API.

### Testing

Run unit and integration tests:

```bash
./gradlew test
```

### Contributing

Contributions are welcome! Please fork the repository, create a feature branch, and submit a pull request.

### License

This project is licensed under the MIT License.

---

For questions or support, open an issue on GitHub.
