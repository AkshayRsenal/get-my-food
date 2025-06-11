    # Delivery Service

Ein Microservice für die Verwaltung von Lieferungen und Lieferverfolgung im GetMyFood-System. Der Service empfängt Bestellungs-Events von der Order Service über RabbitMQ und orchestriert den Lieferprozess.

## Übersicht

- **Technologie**: Java 17+, Spring Boot, RabbitMQ, Spring Data JPA
- **Port**: 8061
- **Build Tool**: Gradle
- **Datenbank**: Relational DB (konfigurierbar in application.yaml)

## Voraussetzungen

- Java 17 oder höher
- Gradle 8.8 oder höher
- RabbitMQ Server (für Event-Consumer)
- Spring Boot 3.x

## Projektstruktur

```
src/main/java/bbq/delivery/
├── DeliveryApplication.java          # Spring Boot Einstiegspunkt
├── DeliveryRestController.java        # REST API Endpoints
├── DeliveryRepository.java            # JPA Repository für Datenbankzugriff
├── DeliveryScheduler.java             # Automatische Lieferplanung
├── OrderListener.java                 # RabbitMQ Message Consumer
├── HelloRestController.java           # Health Check Endpoint
├── config/
│   └── RabbitMqConfiguration.java     # RabbitMQ Konfiguration
└── model/
    ├── Delivery.java                  # Delivery Entity
    ├── Order.java                     # Order Reference
    ├── Cart.java                      # Einkaufswagen
    ├── BillingAddress.java            # Lieferadresse
```

## Hauptkomponenten

### 1. DeliveryRestController
REST API für Lieferverwaltung und Tracking:

```
GET  /deliveries              # Alle Lieferungen abrufen
GET  /deliveries/{id}         # Lieferung nach ID
PUT  /deliveries/{id}         # Lieferstatus aktualisieren
POST /deliveries              # Neue Lieferung erstellen
DELETE /deliveries/{id}       # Lieferung löschen
```

### 2. OrderListener
Empfängt "OrderCreated" Events von der Order Service über RabbitMQ:
- Erstellt automatisch Delivery-Record
- Initialisiert Lieferplanung
- Asynchrone Event-Verarbeitung

### 3. DeliveryScheduler
Automated Task Scheduler:
- Plant Lieferrouten
- Optimiert Lieferketten
- Aktualisiert Lieferstatus periodisch

### 4. RabbitMQ Integration
- **Queue**: `delivery-queue`
- **Exchange**: `order.events`
- **Routing Key**: `order.created`
- **Message Format**: JSON

## Build und Ausführung

### Build
```bash
./gradlew build
```

### Lokale Ausführung
```bash
./gradlew bootRun
```

Alternativ:
```bash
java -jar build/libs/delivery-*.jar
```

### Docker Build
```bash
docker build -t delivery-service .
docker run -p 8061:8061 \
  -e SPRING_RABBITMQ_HOST=rabbitmq \
  -e SPRING_DATASOURCE_URL=jdbc:mysql://db:3306/delivery \
  delivery-service
```

## Konfiguration

### application.yaml
```yaml
spring:
  application:
    name: delivery-service
  jpa:
    hibernate:
      ddl-auto: update
  rabbitmq:
    host: localhost
    port: 5672
    username: guest
    password: guest
  datasource:
    url: jdbc:mysql://localhost:3306/delivery
    username: root
    password: root
    
server:
  port: 8061
  servlet:
    context-path: /
```

### Umgebungsvariablen
```
SPRING_RABBITMQ_HOST=localhost
SPRING_RABBITMQ_PORT=5672
SPRING_DATASOURCE_URL=jdbc:mysql://localhost:3306/delivery
SERVER_PORT=8061
```

## Open API / Swagger Documentation

### Swagger UI
http://localhost:8061/swagger-ui.html

### Raw OpenAPI YAML
http://localhost:8061/v3/api-docs.yaml

### REST Client File
Siehe `delivery.http` für HTTP-Requests zum manuellen Testen:
```http
GET http://localhost:8061/deliveries
Authorization: Bearer token

### Get Delivery by ID
GET http://localhost:8061/deliveries/1

### Create Delivery
POST http://localhost:8061/deliveries
Content-Type: application/json

{
  "orderId": 1,
  "deliveryAddress": "Hauptstraße 123, 10115 Berlin",
  "estimatedDeliveryTime": "2026-01-24T18:30:00"
}

### Update Delivery Status
PUT http://localhost:8061/deliveries/1
Content-Type: application/json

{
  "status": "DELIVERED"
}
```
