    # Kitchen Service

A lightweight microservice for managing food order preparation in the kitchen. The service consumes orders from RabbitMQ and manages order status from receipt to ready for delivery.

## Overview

| Property | Value |
|----------|-------|
| **Language** | Go 1.22+ |
| **Framework** | Chi Router (lightweight HTTP framework) |
| **Port** | 8062 |
| **Message Broker** | RabbitMQ |
| **Protocol** | REST API over HTTP |
| **Build System** | Go Modules |
| **Container** | Docker support |

## Prerequisites

- Go 1.22 or higher
- RabbitMQ Server (running)
- Docker (optional, for containerized deployment)
- Git for cloning the repository

## Quick Start

### 1. Clone & Setup
```bash
git clone <repo>
cd kitchen
go mod download
go mod tidy
```

### 2. Run Locally
```bash
# Start with default RabbitMQ on localhost
go run main.go

# Or with custom RabbitMQ host
KITCHEN_RABBITMQHOST=rabbitmq-server go run main.go
```
