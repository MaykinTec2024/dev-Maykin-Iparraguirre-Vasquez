#!/bin/bash

# Start script for Quarkus application
# Allows injection of custom application.properties file

set -e

echo "Starting Quarkus Application..."

# Default properties file
PROPERTIES_FILE="src/main/resources/application.properties"

# Check if custom properties file is provided
if [ "$1" ]; then
    if [ -f "$1" ]; then
        PROPERTIES_FILE="$1"
        echo "Using custom properties file: $PROPERTIES_FILE"
    else
        echo "Properties file not found: $1"
        echo "Using default properties file: $PROPERTIES_FILE"
    fi
fi

# Create logs directory if it doesn't exist
mkdir -p logs

# Export properties file location
export QUARKUS_CONFIG_LOCATIONS="$PROPERTIES_FILE"

# Start the application in development mode
echo "Starting application with properties: $PROPERTIES_FILE"
./mvnw quarkus:dev -Dquarkus.config.locations="$PROPERTIES_FILE"
