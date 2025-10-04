# Makefile for IWA Project Testing

.PHONY: help test test-unit test-integration test-all test-quality clean coverage report docker-test-up docker-test-down

# Default target
help: ## Show this help message
	@echo "Available targets:"
	@grep -E '^[a-zA-Z_-]+:.*?## .*$$' $(MAKEFILE_LIST) | sort | awk 'BEGIN {FS = ":.*?## "}; {printf "  \033[36m%-20s\033[0m %s\n", $$1, $$2}'

# Test targets
test: test-unit ## Run unit tests (default)

test-unit: ## Run unit tests for all services
	@echo "Running unit tests for all microservices..."
	@./run-all-tests.sh unit

test-integration: ## Run integration tests for all services
	@echo "Running integration tests for all microservices..."
	@./run-all-tests.sh integration

test-all: ## Run all tests (unit + integration)
	@echo "Running all tests for all microservices..."
	@./run-all-tests.sh all

test-quality: ## Run quality checks (static analysis, style)
	@echo "Running quality checks for all microservices..."
	@./run-all-tests.sh quality

# Coverage and reporting
coverage: ## Generate coverage reports
	@echo "Generating coverage reports..."
	@for service in Gateway-Service User-Service Announcement-Service Application-Service Chat-Service Favorite-Service Log-Service Payment-Service Rating-Service; do \
		if [ -d "$$service" ]; then \
			echo "Generating coverage for $$service..."; \
			cd "$$service" && ./mvnw jacoco:report && cd ..; \
		fi; \
	done

report: coverage ## Generate and open coverage reports
	@echo "Coverage reports generated. Open the following files in your browser:"
	@for service in Gateway-Service User-Service Announcement-Service Application-Service Chat-Service Favorite-Service Log-Service Payment-Service Rating-Service; do \
		if [ -f "$$service/target/site/jacoco/index.html" ]; then \
			echo "  $$service/target/site/jacoco/index.html"; \
		fi; \
	done

# Docker testing
docker-test-up: ## Start test databases with Docker Compose
	@echo "Starting test databases..."
	@docker-compose -f docker-compose.test.yml up -d
	@echo "Waiting for databases to be ready..."
	@sleep 10

docker-test-down: ## Stop test databases
	@echo "Stopping test databases..."
	@docker-compose -f docker-compose.test.yml down

docker-test: docker-test-up ## Run tests with Docker databases
	@echo "Running tests with Docker databases..."
	@sleep 5  # Give databases time to fully start
	@$(MAKE) test-integration
	@$(MAKE) docker-test-down

# Cleanup
clean: ## Clean all build artifacts
	@echo "Cleaning all services..."
	@for service in Gateway-Service User-Service Announcement-Service Application-Service Chat-Service Favorite-Service Log-Service Payment-Service Rating-Service; do \
		if [ -d "$$service" ]; then \
			echo "Cleaning $$service..."; \
			cd "$$service" && ./mvnw clean && cd ..; \
		fi; \
	done

# Individual service targets
test-gateway: ## Test Gateway Service only
	@cd Gateway-Service && ./mvnw clean test

test-user: ## Test User Service only
	@cd User-Service && ./mvnw clean test

test-announcement: ## Test Announcement Service only
	@cd Announcement-Service && ./mvnw clean test

test-application: ## Test Application Service only
	@cd Application-Service && ./mvnw clean test

test-chat: ## Test Chat Service only
	@cd Chat-Service && ./mvnw clean test

test-favorite: ## Test Favorite Service only
	@cd Favorite-Service && ./mvnw clean test

test-log: ## Test Log Service only
	@cd Log-Service && ./mvnw clean test

test-payment: ## Test Payment Service only
	@cd Payment-Service && ./mvnw clean test

test-rating: ## Test Rating Service only
	@cd Rating-Service && ./mvnw clean test

# CI/CD simulation
ci-test: clean test-all test-quality ## Simulate CI/CD testing pipeline
	@echo "CI/CD simulation completed successfully!"

# Development targets
dev-test: ## Quick development test (unit tests only)
	@echo "Running quick development tests..."
	@$(MAKE) test-unit

dev-watch: ## Watch for changes and run tests (requires entr)
	@echo "Watching for changes... (requires 'entr' tool)"
	@find . -name "*.java" | entr -r make dev-test