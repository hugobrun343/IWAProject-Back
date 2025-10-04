# Testing Strategy for IWA Project Microservices

## Overview

This document outlines the comprehensive testing strategy for the IWA Project microservices architecture. Our testing approach includes unit tests, integration tests, code coverage, and quality checks that run both locally and in CI/CD pipelines.

## Project Structure

The project consists of 9 microservices:
- `Gateway-Service` - API Gateway
- `User-Service` - User management
- `Announcement-Service` - Announcements
- `Application-Service` - Applications
- `Chat-Service` - Chat functionality
- `Favorite-Service` - Favorites management
- `Log-Service` - Logging service
- `Payment-Service` - Payment processing
- `Rating-Service` - Rating system

## Testing Levels

### 1. Unit Tests
- **Purpose**: Test individual components in isolation
- **Framework**: JUnit 5, Mockito, AssertJ
- **Location**: `src/test/java/**/*Test.java`
- **Execution**: `mvn test` or `./run-all-tests.ps1 unit`

### 2. Integration Tests
- **Purpose**: Test service interactions with real databases
- **Framework**: Spring Boot Test, Testcontainers, RestAssured
- **Location**: `src/test/java/**/*IntegrationTest.java`
- **Execution**: `mvn verify -Pintegration-tests` or `./run-all-tests.ps1 integration`

### 3. Code Coverage
- **Tool**: JaCoCo
- **Minimum Coverage**: 70%
- **Reports**: `target/site/jacoco/index.html`
- **Exclusions**: DTOs, entities, configuration classes

### 4. Quality Checks
- **Static Analysis**: SpotBugs
- **Code Style**: Checkstyle (Google style)
- **Execution**: `mvn verify -Pquality` or `./run-all-tests.ps1 quality`

## CI/CD Testing Pipeline

### GitHub Actions Workflow: `test-microservices.yml`

The workflow includes the following jobs:

1. **detect-service-changes**: Determines which services have changes
2. **test-services**: Runs tests in parallel for each service
3. **integration-tests**: Runs integration tests with real databases
4. **code-quality**: Generates coverage reports and runs quality checks
5. **test-summary**: Provides a comprehensive test summary

### Workflow Triggers
- Push to `main` or `dev` branches
- Pull requests to `main` or `dev` branches

### Parallel Execution
- Each service runs tests independently
- Fail-fast disabled to see all test results
- Matrix strategy for efficient resource usage

## Local Testing

### Prerequisites
- Java 21
- Maven 3.6+
- Docker & Docker Compose (for integration tests)

### Running Tests Locally

#### PowerShell (Windows)
```powershell
# Run unit tests for all services
.\run-all-tests.ps1

# Run integration tests
.\run-all-tests.ps1 integration

# Run all tests
.\run-all-tests.ps1 all

# Run quality checks
.\run-all-tests.ps1 quality
```

#### Bash (Linux/Mac)
```bash
# Run unit tests for all services
./run-all-tests.sh

# Run integration tests
./run-all-tests.sh integration

# Run all tests
./run-all-tests.sh all

# Run quality checks
./run-all-tests.sh quality
```

#### Individual Service Testing
```bash
cd User-Service
./mvnw clean test                    # Unit tests
./mvnw clean verify -Pintegration-tests  # Integration tests
./mvnw clean verify -Pall-tests          # All tests
./mvnw clean verify -Pquality            # Quality checks
```

### Using Docker for Testing

#### Start Test Databases
```bash
docker-compose -f docker-compose.test.yml up -d
```

#### Run Tests in Docker
```bash
docker-compose -f docker-compose.test.yml run test-runner bash
# Inside container:
./run-all-tests.sh all
```

## Test Configuration

### Parent POM (`pom.xml`)
- Centralized dependency management
- Common testing dependencies (JUnit 5, Mockito, Testcontainers)
- JaCoCo coverage configuration
- Quality check plugins (SpotBugs, Checkstyle)

### Service-Specific Configuration
Each service includes:
- `application-test.properties` - Test-specific configuration
- H2 database for fast unit tests
- PostgreSQL Testcontainers for integration tests

### Test Profiles
- `unit-tests` (default) - Unit tests only
- `integration-tests` - Integration tests with Testcontainers
- `all-tests` - Both unit and integration tests
- `quality` - Static analysis and style checks

## Best Practices

### Test Organization
```
src/test/java/
├── com/iwaproject/service/
│   ├── ServiceApplicationTests.java     # Basic Spring context test
│   ├── controller/
│   │   ├── UserControllerTest.java      # Unit tests
│   │   └── UserControllerIntegrationTest.java  # Integration tests
│   ├── service/
│   │   └── UserServiceTest.java         # Unit tests
│   └── repository/
│       └── UserRepositoryTest.java      # Repository tests
└── integration/
    └── UserServiceIntegrationTest.java  # Full integration tests
```

### Naming Conventions
- Unit tests: `*Test.java`
- Integration tests: `*IntegrationTest.java` or `*IT.java`
- Test classes should be in same package as tested class

### Test Data Management
- Use `@Sql` annotations for test data setup
- Create test data builders/factories
- Use Testcontainers for realistic database testing

### Assertions
- Prefer AssertJ for fluent assertions
- Use specific assertions over generic ones
- Include meaningful error messages

## Coverage Reports

### Local Reports
After running tests, coverage reports are available at:
- `{service}/target/site/jacoco/index.html`

### CI/CD Reports
- GitHub Actions uploads coverage to Codecov
- Test results are displayed in GitHub PR checks
- Coverage trends tracked over time

## Troubleshooting

### Common Issues

1. **Tests fail in CI but pass locally**
   - Check database state between tests
   - Verify timezone settings
   - Ensure proper test isolation

2. **Slow test execution**
   - Use `@MockBean` instead of `@Autowired` where possible
   - Optimize database operations in tests
   - Use test slices (`@WebMvcTest`, `@DataJpaTest`)

3. **Testcontainers issues**
   - Ensure Docker is running
   - Check port conflicts
   - Verify sufficient memory allocation

### Debug Commands
```bash
# Check test output
./mvnw test -X

# Run specific test
./mvnw test -Dtest=UserServiceTest

# Skip tests
./mvnw install -DskipTests

# Run tests with coverage
./mvnw clean verify jacoco:report
```

## Metrics and Monitoring

### Key Metrics
- Test execution time
- Code coverage percentage
- Test failure rate
- Quality gate compliance

### Alerts
- Coverage drops below threshold
- Quality gates fail
- Test execution time increases significantly

## Future Enhancements

1. **Contract Testing**: Implement Pact for API contract testing
2. **Performance Tests**: Add JMeter or Gatling performance tests
3. **Security Tests**: Integrate OWASP dependency check
4. **Mutation Testing**: Add PIT mutation testing
5. **End-to-End Tests**: Implement full system tests with Cypress or Selenium

## Resources

- [Spring Boot Testing Documentation](https://spring.io/guides/gs/testing-web/)
- [Testcontainers Documentation](https://www.testcontainers.org/)
- [JaCoCo Documentation](https://www.jacoco.org/jacoco/trunk/doc/)
- [AssertJ Documentation](https://assertj.github.io/doc/)
- [Mockito Documentation](https://javadoc.io/doc/org.mockito/mockito-core/latest/org/mockito/Mockito.html)