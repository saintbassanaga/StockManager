# Stock Manager Project (v1.0)

## Overview
Stock Manager is a Spring Boot application designed to manage basic product inventories. In version 1.0, the application focuses on managing **Products** and **Categories**, offering a secure environment for administrators and users to handle product-related operations.

The project incorporates **Spring Security** for authentication and authorization, ensuring that only authenticated users can interact with the system.

**Version 1.0** is implemented as a **monolithic** architecture. In future releases, particularly **version 2.0**, the architecture will evolve into a **microservices-based** approach to enhance scalability and maintainability.

---

## Features (v1.0)
- **Manage Products**: Create, update, delete, and view products.
- **Manage Categories**: Create, update, delete, and view categories.
- **Spring Security Integration**: Secure endpoints for product and category management, requiring users to authenticate before performing operations.

---

## Technologies Used
- **Spring Boot**: Core framework for building the REST API.
- **Spring Security**: Ensures secure authentication and authorization.
- **Hibernate**: ORM for database interaction with JPA.
- **PostgreSQL**: Used as the database for development and production environments.
- **Lombok**: To reduce boilerplate code in entity and DTO classes.
- **OpenAPI (Swagger)**: For API documentation and testing.
  
---

## Requirements
- **Java 21**
- **Maven 3.6+**
- **PostgreSQL**: As the database for production environments.

---

## Setup Instructions
1. Clone the repository:
   ```bash
   git clone https://github.com/saintbassanaga/StockManager.git
