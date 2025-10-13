#!/bin/bash

# Script to run tests for all microservices
# Usage: ./run-all-tests.sh [test-type]
# test-type: unit (default), integration, all, quality

set -e

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

# Default test type
TEST_TYPE=${1:-unit}

# Services to test
SERVICES=(
    "Gateway-Service"
    "User-Service"
    "Announcement-Service"
    "Application-Service"
    "Chat-Service"
    "Favorite-Service"
    "Payment-Service"
    "Rating-Service"
)

# Log-Service uses ELK stack (no database tests needed)

# Function to print colored output
print_status() {
    echo -e "${BLUE}[INFO]${NC} $1"
}

print_success() {
    echo -e "${GREEN}[SUCCESS]${NC} $1"
}

print_error() {
    echo -e "${RED}[ERROR]${NC} $1"
}

print_warning() {
    echo -e "${YELLOW}[WARNING]${NC} $1"
}

# Function to run tests for a service
run_tests_for_service() {
    local service=$1
    local test_type=$2
    
    print_status "Running $test_type tests for $service..."
    
    cd "$service" || {
        print_error "Directory $service not found"
        return 1
    }
    
    case $test_type in
        "unit")
            ./mvnw clean test -q
            ;;
        "integration")
            ./mvnw clean verify -Pintegration-tests -q
            ;;
        "all")
            ./mvnw clean verify -Pall-tests -q
            ;;
        "quality")
            ./mvnw clean verify -Pquality -q
            ;;
        *)
            print_error "Unknown test type: $test_type"
            cd ..
            return 1
            ;;
    esac
    
    if [ $? -eq 0 ]; then
        print_success "$service tests passed"
    else
        print_error "$service tests failed"
        cd ..
        return 1
    fi
    
    cd ..
}

# Function to generate test report
generate_test_report() {
    print_status "Generating test report..."
    
    echo "# Test Report - $(date)" > test-report.md
    echo "" >> test-report.md
    echo "## Test Results" >> test-report.md
    echo "" >> test-report.md
    echo "| Service | Status | Coverage |" >> test-report.md
    echo "|---------|--------|----------|" >> test-report.md
    
    for service in "${SERVICES[@]}"; do
        if [ -f "$service/target/site/jacoco/index.html" ]; then
            # Extract coverage percentage (this is a simplified approach)
            coverage=$(grep -o '[0-9]\+%' "$service/target/site/jacoco/index.html" | head -1 || echo "N/A")
            echo "| $service | ✅ | $coverage |" >> test-report.md
        elif [ -f "$service/target/surefire-reports" ]; then
            echo "| $service | ✅ | N/A |" >> test-report.md
        else
            echo "| $service | ❌ | N/A |" >> test-report.md
        fi
    done
    
    print_success "Test report generated: test-report.md"
}

# Main execution
main() {
    print_status "Starting $TEST_TYPE tests for all microservices..."
    print_status "Services to test: ${SERVICES[*]}"
    
    # Track results
    PASSED_SERVICES=()
    FAILED_SERVICES=()
    
    for service in "${SERVICES[@]}"; do
        if [ -d "$service" ]; then
            if run_tests_for_service "$service" "$TEST_TYPE"; then
                PASSED_SERVICES+=("$service")
            else
                FAILED_SERVICES+=("$service")
            fi
        else
            print_warning "Service directory $service not found, skipping..."
        fi
    done
    
    # Summary
    echo ""
    print_status "=== TEST SUMMARY ==="
    echo "Test Type: $TEST_TYPE"
    echo "Total Services: ${#SERVICES[@]}"
    echo "Passed: ${#PASSED_SERVICES[@]}"
    echo "Failed: ${#FAILED_SERVICES[@]}"
    
    if [ ${#PASSED_SERVICES[@]} -gt 0 ]; then
        print_success "Passed services: ${PASSED_SERVICES[*]}"
    fi
    
    if [ ${#FAILED_SERVICES[@]} -gt 0 ]; then
        print_error "Failed services: ${FAILED_SERVICES[*]}"
        exit 1
    fi
    
    # Generate report if tests passed
    if [ ${#FAILED_SERVICES[@]} -eq 0 ]; then
        generate_test_report
    fi
    
    print_success "All tests completed successfully!"
}

# Show usage if help is requested
if [ "$1" == "--help" ] || [ "$1" == "-h" ]; then
    echo "Usage: $0 [test-type]"
    echo ""
    echo "Test types:"
    echo "  unit        - Run unit tests only (default)"
    echo "  integration - Run integration tests only"
    echo "  all         - Run all tests (unit + integration)"
    echo "  quality     - Run quality checks (static analysis, style)"
    echo ""
    echo "Examples:"
    echo "  $0              # Run unit tests"
    echo "  $0 integration  # Run integration tests"
    echo "  $0 all          # Run all tests"
    echo "  $0 quality      # Run quality checks"
    exit 0
fi

# Run main function
main