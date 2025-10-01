# Diagramas de Clases - Servicios

Este directorio contiene los diagramas de clases para los servicios del proyecto test-back.

## Archivos de Diagramas

### 1. `services-class-diagram.puml`
Diagrama completo de clases que incluye:
- **Servicios principales**: PokemonService, TextConcatenationService, ValidationService
- **Clientes REST**: PokemonApiClient
- **DTOs**: PokemonMoveResponse, TextConcatenationRequest, TextConcatenationResponse
- **Excepciones**: ValidationException
- **Dependencias externas**: EventBus (Vert.x)

### 2. `services-simplified.puml`
Diagrama simplificado enfocado únicamente en la capa de servicios y sus relaciones.

## Arquitectura de Servicios

### PokemonService
- **Propósito**: Maneja operaciones con la API de Pokémon
- **Características**:
  - Implementa estrategias de tolerancia a fallos (Circuit Breaker, Retry, Timeout)
  - Usa programación reactiva con Uni
  - Publica eventos asincrónicos
- **Dependencias**: PokemonApiClient, EventBus

### TextConcatenationService
- **Propósito**: Procesa solicitudes de concatenación de texto
- **Características**:
  - Orquesta la validación y concatenación
  - Publica eventos sincrónicos
- **Dependencias**: ValidationService, EventBus

### ValidationService
- **Propósito**: Valida parámetros de entrada y proporciona seguridad
- **Características**:
  - Previene inyección SQL
  - Valida parámetros nulos/vacíos
  - Servicio reutilizable sin dependencias
- **Dependencias**: Ninguna (servicio base)

## Patrones de Diseño Identificados

1. **Dependency Injection**: Todos los servicios usan `@Inject` para inyección de dependencias
2. **Event-Driven Architecture**: Uso del EventBus para comunicación asíncrona
3. **Fault Tolerance**: Implementación de Circuit Breaker, Retry y Timeout patterns
4. **Validation Layer**: Separación de la lógica de validación en un servicio dedicado
5. **DTO Pattern**: Uso de objetos de transferencia de datos para request/response

## Cómo Ver los Diagramas

1. Asegúrate de tener la extensión PlantUML instalada en VS Code
2. Abre cualquier archivo `.puml`
3. Usa `Ctrl+Shift+P` y busca "PlantUML: Preview Current Diagram"
4. O usa `Alt+D` para vista previa rápida

## Tecnologías Utilizadas

- **Quarkus**: Framework principal
- **MicroProfile**: Para REST clients y fault tolerance
- **Vert.x**: Event Bus para messaging
- **Jakarta EE**: Para CDI y annotations
- **Lombok**: Para reducir boilerplate code