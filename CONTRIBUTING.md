# Contributing to RaniyaMart Capstone

Thank you for your interest in contributing to **RaniyaMart**, an enterprise-grade e-commerce application built using Java Servlets, JDBC, and Apache Tomcat 9.0.x for Anna University R2025 (Semester 3).

---

## 🛠️ Prerequisites

Before building or running RaniyaMart, ensure your development environment has:
- **Java Development Kit (JDK)**: JDK 17 or higher (`java -version`)
- **Apache Maven**: Version 3.8+ (`mvn -version`)
- **Git**: Version 2.x+ (`git --version`)
- **Browser**: Modern web browser (Chrome, Edge, Firefox)

---

## 🚀 Quick Start Guide

### 1. Clone the Repository
```bash
git clone https://github.com/Raniya08/RaniyaMart.git
cd RaniyaMart
```

### 2. Configure Environment Variables / Properties
Copy `config.properties.example` to `config.properties` or set environment variables:
```bash
cp config.properties.example config.properties
```

Default configuration contents:
```properties
db.driver=org.h2.Driver
db.url=jdbc:h2:file:./data/raniyamartdb;DB_CLOSE_DELAY=-1;MODE=MySQL
db.user=sa
db.password=
app.session.timeoutMinutes=30
app.ai.chatbot.provider=mock
```

### 3. Build & Test the Project
Run Maven clean verify to compile all Java source files and execute JUnit 5 unit tests against the embedded H2 database:
```bash
mvn clean verify
```

### 4. Run Locally using Jetty / Tomcat
To launch the local web server on port `8080`:
```bash
mvn jetty:run
```
Access the application locally at: **`http://localhost:8080/RaniyaMart/`** or **`http://localhost:8080/`**.

---

## 🧪 Running Automated Tests

RaniyaMart uses JUnit 5 and an in-memory H2 database for zero-dependency testing.

To run all unit tests:
```bash
mvn test
```

To run a specific test class (e.g., Chatbot service tests):
```bash
mvn test -Dtest=ChatServiceTest
```

---

## 🔑 Pre-seeded Demo Credentials

For testing various role-based access controls:
- **Admin**: `admin@raniyamart.com` | `Password123!`
- **Seller**: `seller@raniyamart.com` | `Password123!`
- **Buyer**: `buyer@raniyamart.com` | `Password123!`

---

## 📋 Coding Standards & Guidelines

- **PreparedStatement**: All database access MUST use `PreparedStatement` with try-with-resources. String concatenation in SQL is strictly forbidden.
- **DTO Separation**: Internal DB models (`User`, `Product`, `Order`) must not be directly exposed to JSP views without DTO mapping (`UserResponseDTO`).
- **Security Check**: Enforce session-based RBAC checks in `AuthFilter` for all `/seller/*` and `/admin/*` endpoints.
- **XSS Prevention**: Escape all dynamic JSP outputs using JSTL `<c:out value="${...}"/>` or `fn:escapeXml(...)`.
