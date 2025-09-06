# E-Commerce REST API 🛍️

This is a robust backend service for a modern e-commerce platform, built with Java and the Spring Boot framework. It provides a complete set of RESTful endpoints for managing users, products, shopping carts, and orders, with a secure authentication system based on JSON Web Tokens (JWT).

## Features ✨

  * **User Authentication:** Secure sign-up and login endpoints using JWT.
  * **Role-Based Access Control:** Distinction between regular users (`ROLE_USER`) and administrators (`ROLE_ADMIN`).
  * **Product Management:**
      * Public endpoints to view and search for products.
      * Admin-only endpoints to create, update, and delete products.
  * **Shopping Cart:** Authenticated users can add items to, view, and modify their personal shopping cart.
  * **Order & Checkout:** A secure endpoint for users to convert their cart into an order, which updates product inventory.

## Technology Stack 🛠️

  * **Backend:** Java 17+, Spring Boot 3
  * **Security:** Spring Security 6, JSON Web Tokens (JWT)
  * **Database:** Spring Data JPA (Hibernate), PostgreSQL
  * **Build Tool:** Apache Maven
  * **Validation:** Jakarta Bean Validation (Hibernate Validator)
  * **Utilities:** Lombok

## Prerequisites

Before you begin, ensure you have the following installed on your system:

  * **Java Development Kit (JDK)**: Version 17 or higher.
  * **Apache Maven**: For building the project and managing dependencies.
  * **PostgreSQL**: A running instance of the PostgreSQL database.
  * **Postman**: (Recommended) For testing the API endpoints.

## Getting Started 🚀

Follow these steps to get the application running on your local machine.

### 1\. Clone the Repository

```bash
git clone https://github.com/M-Sayed939/ecommerce.git
cd ecommerce-app
```

### 2\. Set Up the PostgreSQL Database

You need to create a database and a dedicated user for the application.

1.  Connect to PostgreSQL:
    ```bash
    psql -U postgres
    ```
2.  Run the following SQL commands:
    ```sql
    -- Create the database
    CREATE DATABASE ecommerce_db;

    -- Create a user with a secure password
    CREATE USER ecommerce_user WITH PASSWORD 'your_secure_password';

    -- Grant permissions to the user
    GRANT ALL ON SCHEMA public TO ecommerce_user;
    ```

### 3\. Configure the Application

Open the `src/main/resources/application.properties` file and update it with your database credentials and a JWT expiration time.

```properties
# PostgreSQL Datasource Configuration
spring.datasource.url=jdbc:postgresql://localhost:5432/ecommerce_db
spring.datasource.username=ecommerce_user
spring.datasource.password=your_secure_password
spring.datasource.driver-class-name=org.postgresql.Driver

# JPA & Hibernate Configuration
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

# Server Configuration
server.port=8081

# JWT Configuration (86400000 ms = 24 hours)
app.jwt.expiration-in-ms=86400000
```

### 4\. Build and Run the Application

Use Maven to build and run the project.

```bash
# Build the project
mvn clean install

# Run the application
mvn spring-boot:run
```

The application will start on `http://localhost:8081`.

## API Endpoints 📖

All requests and responses use JSON. Authenticated endpoints require a `Bearer` token in the `Authorization` header.

### Authentication (`/api/auth`)

| Method | Endpoint         | Description                   | Access |
| :----- | :--------------- | :---------------------------- | :----- |
| `POST` | `/signup`        | Register a new user.          | Public |
| `POST` | `/login`         | Authenticate a user and get a JWT. | Public |

### Products (`/api/products` & `/api/admin/products`)

| Method   | Endpoint           | Description                      | Access       |
| :------- | :----------------- | :------------------------------- | :----------- |
| `GET`    | `/products`        | Get a list of all products.      | Public       |
| `GET`    | `/products/{id}`   | Get a single product by its ID.  | Public       |
| `POST`   | `/admin/products`  | Create a new product.            | Admin Only   |
| `PUT`    | `/admin/products/{id}` | Update an existing product.      | Admin Only   |
| `DELETE` | `/admin/products/{id}` | Delete a product.                | Admin Only   |

### Shopping Cart (`/api/cart`)

| Method | Endpoint   | Description                  | Access          |
| :----- | :--------- | :--------------------------- | :-------------- |
| `GET`  | `/`        | Get the current user's cart. | User Authenticated |
| `POST` | `/items`   | Add an item to the cart.     | User Authenticated |

### Orders (`/api/orders`)

| Method | Endpoint | Description                          | Access          |
| :----- | :------- | :----------------------------------- | :-------------- |
| `GET`  | `/`      | Get the current user's order history. | User Authenticated |
| `POST` | `/`      | Create an order from the cart.       | User Authenticated |
