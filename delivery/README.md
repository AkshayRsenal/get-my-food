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
