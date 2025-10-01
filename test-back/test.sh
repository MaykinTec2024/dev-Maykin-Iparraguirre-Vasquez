#!/bin/bash

# Test script for running unit and integration tests

set -e

echo "Running Quarkus Tests..."

# Clean and compile
echo "Cleaning and compiling project..."
./mvnw clean compile

# Run unit tests
echo "Running unit tests..."
./mvnw test -Dtest.profile=test

# Run integration tests
echo "Running integration tests..."
./mvnw verify -Dtest.profile=test

echo "All tests completed successfully!"

# Optional: Generate test report
if command -v mvn &> /dev/null; then
    echo "Generating test reports..."
    ./mvnw surefire-report:report
    echo "Test reports generated in target/site/surefire-report.html"
fi
