Ein Microservice kann schnell entwickelt werden, autonom von einem Team. Der Microservices skaliert flexibel, ist performant und leicht zu erweitern. Ansonsten einfach austauschen. Aber wie funktioniert das?

Spring Boot bietet eine exzellente Runtime auf der Java Virtual Machine und ein riesiges Ökosystem. Spring Boot bringt alles mit, was Sie für Microservices brauchen.
Dieses Projekt zeigt die Grundlagen eines Microservice mit Spring Boot.

# Voraussetzungen

- Java 17 oder höher
- Maven 3.6 oder höher

# Projektstruktur

- `src/main/java`: Enthält den Quellcode des Microservice.
- `src/main/resources`: Enthält Konfigurationsdateien und statische Ressourcen.
- `pom.xml`: Maven-Projektdatei mit Abhängigkeiten und Build-Konfiguration.

---

## GetMyFood - Microservices Architecture

Das Projekt folgt einer **Microservices-Architektur** mit mehreren unabhängigen Services, die zusammen ein Lebensmittellieferungssystem bilden.

### Übersicht der Microservices

#### 1. **Order Microservice** (Java/Spring Boot)
- **Port**: 8060
- **Beschreibung**: Verwaltet Bestellungen, Menüs und Einkaufswagen
- **Technologie**: Spring Boot, RabbitMQ, REST Client
- **Hauptkomponenten**:
  - `OrderRestController`: Handles Bestellungs-APIs
  - `MenuRestController`: Verwaltet Menüdaten
  - `OrderRabbitMQPublisher`: Publiziert Events über RabbitMQ
  - `OrderRepository`: Datenbankzugriff
  - `RestClientConfiguration`: Externe Service-Kommunikation

**Key Endpoints**:
- `GET /orders`: Alle Bestellungen abrufen
- `POST /orders`: Neue Bestellung erstellen
- `GET /menus`: Menüs abrufen
- `POST /cart`: Artikel zum Einkaufswagen hinzufügen

**Swagger UI**: http://localhost:8060/swagger-ui.html

---

#### 2. **Delivery Microservice** (Java/Spring Boot)
- **Port**: 8061
- **Beschreibung**: Verwaltet Lieferungen und Tracking
- **Technologie**: Spring Boot, RabbitMQ, Scheduler
- **Hauptkomponenten**:
  - `DeliveryRestController`: Delivery API Endpoints
  - `DeliveryScheduler`: Plant automatische Lieferrouten
  - `OrderListener`: Empfängt Bestellungs-Events über RabbitMQ
  - `DeliveryRepository`: Datenbankzugriff
  - `RabbitMqConfiguration`: Event-Bus Konfiguration

