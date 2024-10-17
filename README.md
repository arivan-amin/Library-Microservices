# Library Microservices

## Overview

This project is a **Library Management System** developed as a **Microservices Architecture**
using **Java**. It serves as a project to demonstrate the implementation of microservices using modern **Java** technologies.

## Architecture Overview

The Microservices architecture is designed with clean separation of concerns, enabling scalability, maintainability, and testability. Each microservice is divided into three key layers: Domain, Infrastructure, and Application, ensuring clear boundaries between business logic, technical concerns, and framework-specific components. Also, it uses **Command and query separation** pattern

### Domain Layer
The Domain layer represents the core of the service. It contains the essential business logic and entities, following domain-driven design principles. This layer is entirely framework-agnostic and free of any external dependencies, making it easy to test and highly portable. Key elements include:

- Entities: Represent the core business models.
- Command/Query: Contain the business logic and enforce rules specific to the domain.
- Interfaces: Define contracts (abstractions) that the outer layers (like Infrastructure) must implement, ensuring **inversion of control** and high modularity.

### Infrastructure Layer
The Infrastructure layer deals with the technical aspects of the application, providing implementations for interacting with external systems such as databases or third-party APIs. It serves as the bridge between the domain and the underlying technology stack. Key components include:

- Persistence Layer: Implements domain interfaces (e.g., repositories) using Spring Data JPA to interact with a MySQL database, ensuring seamless data management.
- External Services: Manages any API calls or third-party service interactions necessary for the business.
- Data Mappers: Transforms data between the domain model and persistence model.

### Application Layer
The Application layer, also known as the Spring layer, is where the service integrates with the Spring framework. It handles the configuration, external APIs, and communication with other services. This layer encapsulates:

- Controllers: Handle HTTP requests and direct them to the appropriate domain services.
- Exception Handling: Global error handling through Controller Advice, ensuring standardized responses across the service.
- Configuration: Contains all the necessary Spring configurations such as beans, security settings, and transactional boundaries.

## Technologies Used

- **Java 21**: Core programming language.
- **Spring Boot**: Framework for building microservices.
- **Spring Cloud**: For managing microservices, service discovery, and load balancing.
- **Eureka**: Service registry for microservices.
- **MySQL**: Relational database for persistent storage.
- **Docker**: Containerization of microservices.
- **Swagger/OpenAPI**: API documentation and testing.
- **JUnit & Mockito**: Testing frameworks for unit and integration testing.
- **Flyway**: Database migration tools.
- **Lombok**: Reducing boilerplate code.
- **Grafana**: For observability and monitoring services through dashboards.
- **loki**: For centralized logging.
- **tempo**: For distributed tracing.

## Installation

### Prerequisites

- **Java 21**
- **Maven**
- **Docker**
- **Docker Compose**

### Steps to Run the Project

1. ### Clone the repository:

    ```bash
    git clone https://github.com/arivan-amin/Library-Microservices.git
    cd Library-Microservices
    ```

2. ### Build and install the Core Module because all modules depend on it:

    ```bash
    cd Core
    ../mvnw clean install
    cd ..
    ```
3. ### Go to project root and build the project, and it will load the images to your local docker repository using JIB:

    ```bash
    ./mvnw clean package
    ```

4. ### Set Eureka username and password environment variables and docker host ip:
    - on **Linux**: add the below variables to your **.bashrc** file and reload or reboot
    ```bash
    export EUREKA_USER=admin
    export EUREKA_PASSWORD=admin
    ```
    - on **Windows**: run the below commands
    ```bash
    set EUREKA_USER=admin
    set EUREKA_PASSWORD=admin
    ```
5. ### Run the services using Docker Compose:
    ```bash
    docker compose up -d
    ```

6. ### Access the services:
    - **API Gateway:** `http://localhost:8080`
    - **Eureka Dashboard:** `http://localhost:8080/eureka/web`
    - credentials are username: admin password: admin
    - **Swagger UI:** `http://localhost:8080/swagger-ui.html`
    - **Grafana:** `http://localhost:3000/dashboards`

## Testing

- Unit and tests are available for each microservice.
- Run the tests using Maven:

    ```bash
    ./mvnw test
    ```
## API Documentation

- ### Documentation for API is provided with Open API and Swagger UI at the below link
    ```
    http://localhost:8080/swagger-ui.html
    ```

# Calling API Endpoints Secured with Basic Authentication
### Since you run the docker compose command all the services are running now, you can call the endpoints 

### Postman import file is provided In the root project directory named Library-Microservices.postman_collection.json.

## Books Service 

- ### Get all books:
    ```
    admin:admin@localhost:8080/api/books
    ```
- ### Get Single book details:
    ```
    admin:admin@localhost:8080/api/books/0425f682-0781-4565-bd01-caf3585b2ff6
    ```
- ### Delete single book:
    ```
    admin:admin@localhost:8080/api/books/0001b624-24ba-4f45-bb50-0eebc6d509e8
    ```
- ### Create book:
    ```
    admin:admin@localhost:8080/api/books
    ```
- ### Update book:
    ```
    admin:admin@localhost:8080/api/books/0425f682-0781-4565-bd01-caf3585b2ff6
    ```
  
### Sample JSON for a Book
    ```
    {
        "isbn":"4523523452345",
        "title":"Jude the Obscure",
        "author":"Thomas Hardy",
        "publicationYear":"2020"
    }
    ```

## Patrons Service

- ### Get all patrons:
    ```
    admin:admin@localhost:8080/api/patrons
    ```
- ### Get Single patron details:
    ```
    admin:admin@localhost:8080/api/patrons/0425f682-0781-4565-bd01-caf3585b2ff6
    ```
- ### Delete single patron:
    ```
    admin:admin@localhost:8080/api/patrons/0001b624-24ba-4f45-bb50-0eebc6d509e8
    ```
- ### Create patron:
    ```
    admin:admin@localhost:8080/api/patrons
    ```
- ### Update patron:
    ```
    admin:admin@localhost:8080/api/patrons/0425f682-0781-4565-bd01-caf3585b2ff6
    ```

### Sample JSON for a Patron
    ```
    {
        "name":"john smith",
        "contactInformation":"new york, 445th street"
    }
    ```

## Borrow Service

- ### Borrow Book:
    ```
    admin:admin@localhost:8080/api/borrow/0425f682-0781-4565-bd01-caf3585b2ff6/patron/2e82413d-48c8-4fb9-b592-1df88723a756
    ```
- ### Return Book:
    ```
    admin:admin@localhost:8080/api/return/0425f682-0781-4565-bd01-caf3585b2ff6/patron/2e82413d-48c8-4fb9-b592-1df88723a756
    ```

## Microservices

The system is divided into several microservices, each responsible for a specific domain:

- **API Gateway**: Serves as the single entry point for all client requests, routing them to the
  appropriate microservices. It handles request aggregation, load balancing, and provides a
  centralized point for managing cross-cutting concerns such as authentication and logging.
- **Core Service**: Provides foundational services and shared functionality used across other
  microservices. This could include utility functions, common data access layers, or basic service
  management features.
- **Discovery Server**: Implements service discovery mechanisms, allowing microservices to find and
  communicate with each other dynamically. It maintains a registry of available services and their
  locations.
- **Book Service**: Manages book records.
- **Patron Service**: Manages patron records.
- **Borrow Service**: Manages borrowing records.

## Contact
- **Name: Arivan Amin**
- **Email: arivanamin@gmail.com**
