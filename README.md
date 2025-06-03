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

**Key Endpoints**:
- `GET /deliveries`: Alle Lieferungen abrufen
- `GET /deliveries/{id}`: Lieferung verfolgen
- `PUT /deliveries/{id}`: Lieferstatus aktualisieren

**Swagger UI**: http://localhost:8061/swagger-ui.html

---

#### 3. **Kitchen Microservice** (Go)
- **Port**: 8062
- **Beschreibung**: Verwaltet Bestellvorbereitung und Küchenlogistik
- **Technologie**: Go, REST API
- **Datei**: `main.go`

**Key Endpoints**:
- `GET /kitchen/orders`: Ausstehende Bestellungen
- `PUT /kitchen/orders/{id}`: Bestellstatus aktualisieren

---

#### 4. **Shop Frontend** (Angular)
- **Port**: 4200
- **Beschreibung**: Web-Benutzeroberfläche für Kunden
- **Technologie**: Angular 17+, SCSS, TypeScript
- **Struktur**:
  - `app/order`: Bestellverwaltung
  - `app/delivery`: Lieferverfolgung
  - `app/delivery/services`: API-Services

---

### Kommunikation zwischen Services

#### RabbitMQ Message Bus
- **Order Service** publiziert "OrderCreated" Events
- **Delivery Service** empfängt Events und erstellt Lieferungen
- **Asynchrone Kommunikation** für Skalierbarkeit

#### REST API Calls
- **Order Service** ruft externe Menü-Services auf
- **Frontend** kommuniziert mit Order und Delivery Services

---

### Docker Deployment

Jeder Service hat ein `Dockerfile` für Container-Deployment:

```bash
# Build und Start aller Services
docker-compose up --build

# Einzelne Services starten
docker build -t order-service ./order
docker run -p 8060:8060 order-service

docker build -t delivery-service ./delivery
docker run -p 8061:8061 delivery-service

docker build -t kitchen-service ./kitchen
docker run -p 8062:8062 kitchen-service
```

---

### Entwicklungsumgebung

#### Voraussetzungen
- **Java 17+** (für Order & Delivery Services)
- **Go 1.21+** (für Kitchen Service)
- **Node.js 18+** (für Angular Frontend)
- **Docker & Docker Compose**
- **RabbitMQ** (für asynchrone Kommunikation)

#### Lokale Ausführung

**Order Service**:
```bash
cd order
./gradlew bootRun
```

**Delivery Service**:
```bash
cd delivery
./gradlew bootRun
```

**Kitchen Service**:
```bash
cd kitchen
go run main.go
```

**Frontend**:
```bash
cd shop-frontend
npm install
ng serve
```

---

### API Dokumentation

- **Order Service Swagger**: http://localhost:8060/swagger-ui.html
- **Delivery Service Swagger**: http://localhost:8061/swagger-ui.html
- **Order Service HTTP Client**: `order/order.http`
- **Delivery Service HTTP Client**: `delivery/delivery.http`
- **Kitchen Service HTTP Client**: `kitchen/kitchen.http`

---

### Konfiguration

Jeder Service verwendet `application.yaml` für Konfiguration:

- **Database**: Spring Data JPA mit relativer DB
- **RabbitMQ**: Message Broker für Event-Streaming
- **Server Port**: Individuell konfiguriert pro Service
- **Logging**: SLF4J Logging

---

### Best Practices

**Microservices Prinzipien**:
- Jeder Service hat eine eigene Datenbank
- Asynchrone Kommunikation über Message Broker
- REST APIs für synchrone Aufrufe
- Unabhängige Deployment und Skalierung

**Docker Integration**:
- `.dockerignore` optimiert Build-Kontext
- Multi-stage Builds für kleinere Images
- Environment-basierte Konfiguration

**Monitoring & Debugging**:
- REST HTTP Client Files für einfaches Testen
- Swagger UI für API-Exploration
- Structured Logging für Troubleshooting