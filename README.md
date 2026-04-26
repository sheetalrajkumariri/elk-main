# Microservices Monitoring System with ELK Stack (Spring Boot + Eureka + Gateway)

---

## Project Overview

This project demonstrates a microservices-based architecture built using Spring Boot, integrated with the ELK Stack (Elasticsearch, Logstash, Kibana) for centralized logging and monitoring.

The system consists of multiple services such as API Gateway, Eureka Server, Product Service, and Order Service. Logs from all services are collected, processed, and visualized using the ELK stack.

This setup helps in monitoring distributed systems efficiently and debugging issues in real time.

---

## Architecture

```
┌──────────────────────────────┐
│          Client              │
└──────────────┬───────────────┘
               │ HTTP Request
               ▼
┌──────────────────────────────┐
│        API Gateway           │
└──────────────┬───────────────┘
               ▼
┌──────────────────────────────┐
│       Eureka Server          │
│   (Service Discovery)        │
└──────────────┬───────────────┘
               ▼
     ┌───────────────┐     ┌───────────────┐
     │ Product Service│     │ Order Service │
     └───────┬───────┘     └───────┬───────┘
             │ Logs                │ Logs
             ▼                     ▼
        ┌──────────────────────────────┐
        │          Filebeat            │
        └──────────────┬───────────────┘
                       ▼
        ┌──────────────────────────────┐
        │          Logstash            │
        └──────────────┬───────────────┘
                       ▼
        ┌──────────────────────────────┐
        │       Elasticsearch          │
        └──────────────┬───────────────┘
                       ▼
        ┌──────────────────────────────┐
        │           Kibana             │
        └──────────────────────────────┘
```

---

## How It Works

### One-line Flow

Client → API Gateway → Microservices (Order/Product) → Logs → ELK Stack → Visualization

---

### Step-by-step Explanation

1. Client sends a request to the API Gateway  
2. API Gateway routes the request to appropriate microservice  
3. Services register and discover each other using Eureka Server  
4. Product and Order services process the request  
5. Each service generates logs  
6. Filebeat collects logs from services  
7. Logstash processes and transforms logs  
8. Elasticsearch stores logs  
9. Kibana visualizes logs for monitoring  

---

## Features

- Microservices architecture using Spring Boot  
- API Gateway for routing requests  
- Eureka Server for service discovery  
- Centralized logging using ELK Stack  
- Real-time monitoring using Kibana  
- Docker-based setup  
- Scalable architecture  

---

## Project Structure

```
project-root/

├── docker-compose.yml
├── filebeat.yml
│
├── api-gateway/
│   ├── pom.xml
│   └── src/main/java/
│
├── eureka-server/
│   ├── pom.xml
│   └── src/main/java/
│
├── product-service/
│   ├── pom.xml
│   └── src/main/java/
│
├── order-service/
│   ├── pom.xml
│   └── src/main/java/
│
└── elk/
    ├── logstash.conf
    └── elasticsearch.yml
```

---

## Tech Stack

- Java  
- Spring Boot  
- Spring Cloud Gateway  
- Eureka Server  
- Elasticsearch  
- Logstash  
- Kibana  
- Filebeat  
- Docker  
- Maven  

---

## Prerequisites

- Java 17 or higher  
- Maven  
- Docker and Docker Compose  

---

## Installation / Setup Steps

### Step 1: Start ELK Stack

```bash
docker-compose up
```

---

### Step 2: Start Eureka Server

```bash
cd eureka-server
mvn spring-boot:run
```

---

### Step 3: Start API Gateway

```bash
cd api-gateway
mvn spring-boot:run
```

---

### Step 4: Start Microservices

```bash
cd product-service
mvn spring-boot:run
```

```bash
cd order-service
mvn spring-boot:run
```

---

## Run the Application

All services should be running:

- Eureka Server: http://localhost:8761  
- API Gateway: http://localhost:8080  
- Kibana: http://localhost:5601  

---

## Use Cases

- Monitoring microservices architecture  
- Centralized logging for distributed systems  
- Debugging production issues  
- Observability and system health tracking  

---

## Conclusion

This project demonstrates how to build a scalable microservices architecture using Spring Boot and integrate it with the ELK stack for centralized logging and monitoring. It improves observability and simplifies debugging in distributed systems.
