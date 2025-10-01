# Quarkus Test Backend

A comprehensive Quarkus microservice implementing text concatenation and Pokemon API integration with advanced features like fault tolerance, event bus, and scheduled tasks.

## Features

### S.a) Text Concatenation Service
- **Endpoint**: `POST /api/v1/test`
- **Content-Type**: `application/json`
- **Response-Type**: `text/plain`
- **Functionality**: Concatenates 5 string parameters
- **Security**: Input validation and SQL injection prevention
- **Event Bus**: Uses blocking events for synchronous processing

### S.b) Pokemon API Client
- **Endpoint**: `GET /api/v2/move`
- **Content-Type**: `application/json`
- **Functionality**: Consumes Pokemon API moves endpoint
- **Features**: Header propagation, fault tolerance (Circuit Breaker + Retry)
- **Event Bus**: Uses non-blocking events for asynchronous processing

### S.c) Scheduled Tasks
- **Frequency**: Every 5 minutes (configurable via cron expression)
- **Functionality**: Automatically calls Pokemon API
- **Configuration**: `scheduler.pokemon.cron` property

### S.d) Additional Features
- **Ports**: HTTP 15050 (dev), 15055 (test)
- **CORS**: Enabled for all origins
- **Logging**: Asynchronous, 10MB max, 3 rotations
- **Interceptors**: Request/response logging
- **Exception Handling**: Global exception handler
- **Documentation**: Swagger UI (dev only)
- **Event Bus**: Quarkus Vert.x event bus integration

## Prerequisites

- Java 17
- Maven 3.8+
- Visual Studio Code with Java extensions

## Quick Start

### 1. Clone and Setup
```bash
cd c:\Users\LENOVO\Documents\javaBanco\test-back
```

### 2. Start Application
```bash
# Make scripts executable (Git Bash/WSL)
chmod +x start.sh test.sh

# Start with default configuration
./start.sh

# Start with custom properties file
./start.sh path/to/custom/application.properties
```

### 3. Run Tests
```bash
./test.sh
```

## API Documentation

### Text Concatenation API

**Request:**
```bash
POST http://localhost:15050/api/v1/test
Content-Type: application/json

{
    "param1": "Hello",
    "param2": "World",
    "param3": "from",
    "param4": "Quarkus",
    "param5": "API"
}
```

**Response:**
```
Hello World from Quarkus API
```

### Pokemon Moves API

**Request:**
```bash
GET http://localhost:15050/api/v2/move
Accept: application/json
```

**Response:**
```json
{
    "count": 919,
    "next": "https://pokeapi.co/api/v2/move/?offset=20&limit=20",
    "previous": null,
    "results": [
        {
            "name": "pound",
            "url": "https://pokeapi.co/api/v2/move/1/"
        }
    ]
}
```

## Configuration

### Application Properties
Key configurations in `application.properties`:

```properties
# Server
quarkus.http.port=15050
quarkus.http.test-port=15055

# Logging
quarkus.log.file.rotation.max-file-size=10M
quarkus.log.file.rotation.max-backup-index=3

# Scheduler
scheduler.pokemon.cron=0 */5 * * * ?

# Pokemon API
pokemon.api.url=https://pokeapi.co/api/v2
```

### Custom Configuration
Create a custom `application.properties` file and use:
```bash
./start.sh path/to/your/custom.properties
```

## Development

### VS Code Setup
1. Install Java Extension Pack
2. Install Quarkus extension
3. Open project folder
4. Use Ctrl+Shift+P → "Java: Reload Projects"

### Development Mode
```bash
./mvnw quarkus:dev
```
- Live reload enabled
- Swagger UI: http://localhost:15050/q/swagger-ui/
- Dev UI: http://localhost:15050/q/dev/

### Testing
```bash
# Unit tests only
./mvnw test

# Integration tests
./mvnw verify

# All tests with script
./test.sh
```

## Architecture

### Class Diagram
```
┌─────────────────────┐    ┌─────────────────────┐
│  TextConcatenation  │    │   PokemonResource   │
│     Resource        │    │                     │
├─────────────────────┤    ├─────────────────────┤
│ +concatenateText()  │    │ +getPokemonMoves()  │
└─────────────────────┘    └─────────────────────┘
           │                           │
           ▼                           ▼
┌─────────────────────┐    ┌─────────────────────┐
│ TextConcatenation   │    │   PokemonService    │
│     Service         │    │                     │
├─────────────────────┤    ├─────────────────────┤
│ +concatenateText()  │    │ +getPokemonMoves()  │
└─────────────────────┘    └─────────────────────┘
           │                           │
           ▼                           ▼
┌─────────────────────┐    ┌─────────────────────┐
│  ValidationService  │    │  PokemonApiClient   │
├─────────────────────┤    │    (REST Client)    │
│ +validateString()   │    ├─────────────────────┤
│ +validateParams()   │    │ +getMoves()         │
└─────────────────────┘    └─────────────────────┘
```

### Event Flow
1. **Text Concatenation**: Request → Validation → Processing → Blocking Event → Response
2. **Pokemon API**: Request → External API → Non-blocking Event → Response
3. **Scheduler**: Timer → Pokemon Service → Non-blocking Event

## Fault Tolerance

### Strategies Implemented
- **Retry**: 3 attempts with 1-second delay
- **Circuit Breaker**: Opens after 50% failure rate
- **Timeout**: 30-second limit for external calls

## Security

### Input Validation
- Null/blank parameter checking
- SQL injection pattern detection
- Parameter sanitization

### CORS Configuration
- Enabled for development
- Configurable origins and headers

## Logging

### Configuration
- Asynchronous logging for performance
- File rotation: 10MB max, 3 backups
- Console and file output
- Request/response interceptor logging

### Log Locations
- Console output during development
- File: `logs/application.log`

## Troubleshooting

### Common Issues

1. **Port Already in Use**
   ```bash
   # Change port in application.properties
   quarkus.http.port=15051
   ```

2. **Java Version Issues**
   ```bash
   # Verify Java 17
   java -version
   
   # Set JAVA_HOME if needed
   export JAVA_HOME=/path/to/java17
   ```

3. **VS Code Java Issues**
   ```bash
   # Reload VS Code window
   Ctrl+Shift+P → "Developer: Reload Window"
   
   # Clean workspace
   Ctrl+Shift+P → "Java: Clean Workspace"
   ```

4. **Maven Issues**
   ```bash
   # Clean and reinstall dependencies
   ./mvnw clean install
   ```

## Production Deployment

### Build Application
```bash
./mvnw package
```

### Run Production Build
```bash
java -jar target/quarkus-app/quarkus-run.jar
```

### Environment Variables
```bash
export QUARKUS_HTTP_PORT=8080
export QUARKUS_PROFILE=prod
export SCHEDULER_POKEMON_CRON="0 */10 * * * ?"
```

## Monitoring

### Health Checks
- **Health**: http://localhost:15050/q/health
- **Metrics**: http://localhost:15050/q/metrics
- **Info**: http://localhost:15050/q/info

### Swagger Documentation
- **Dev Only**: http://localhost:15050/q/swagger-ui/
- **Disabled in Production**

## Support

For issues or questions:
1. Check logs in `logs/application.log`
2. Verify configuration in `application.properties`
3. Ensure Java 17 and Maven are properly installed
4. Restart VS Code if Java extensions malfunction
