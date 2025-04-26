# 🛒 CommerceHub

CommerceHub is a backend application built with **Spring Boot** for managing e-commerce products.  
It supports CRUD operations, image uploads, and is documented with Swagger UI.

---

## 🚀 Features

- Product Management (Create, Read, Update, Delete)
- Image Upload with Multipart Data
- PostgreSQL Database Integration
- Swagger UI Documentation
- Docker-ready setup
- Deployment on Render.com

---

## 📚 Tech Stack

- **Java 21**
- **Spring Boot 3.4.3**
- **PostgreSQL** (Database)
- **Hibernate (JPA)**
- **Swagger** (OpenAPI)
- **Docker** (Containerization)

---

## 🛠️ Local Setup

1. **Clone the repository**
   ```bash
   git clone https://github.com/your-username/CommerceHub.git
   cd CommerceHub
2. **Configure Database**

Update your `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/your_database_name
spring.datasource.username=your_db_username
spring.datasource.password=your_db_password
spring.jpa.hibernate.ddl-auto=update
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
```

Open your browser and visit:
http://localhost:8092/swagger-ui/index.html
