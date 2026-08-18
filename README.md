# RaniyaMart — E-Commerce Capstone Application

**Anna University R2025, Semester 3 Capstone Project**  
**Tech Stack**: Java 17 · Java Servlets (`javax.servlet.*`) · JDBC · Apache Tomcat 9.0.x · HikariCP · jBCrypt · H2 / MySQL · JUnit 5  

---

## 🌟 Overview
**RaniyaMart** is a full-featured, secure, multi-role e-commerce web application built using standard Java EE 8 Servlets, JDBC, and Apache Tomcat 9.0.x. It supports complete user workflows for Buyers, Sellers, and Administrators, featuring password encryption via BCrypt, session security, parameterized queries, HikariCP connection pooling, interactive cart & checkout management, and an AI Chatbot assistant.

---

## 🛠️ Technology Stack Table

| Layer | Component / Library | Version | Description |
| :--- | :--- | :--- | :--- |
| **Language** | Java Development Kit (JDK) | 17 | Core programming platform |
| **Web Container** | Apache Tomcat / Servlet API | 9.0.x / 4.0.1 | Java EE 8 Web Application Server |
| **Database** | Embedded H2 / MySQL | 2.2.224 / 8.3.0 | Relational database (H2 for zero-config dev/test) |
| **Connection Pool**| HikariCP | 5.1.0 | High-performance JDBC connection pool |
| **Security** | jBCrypt | 0.4 | Salted BCrypt password hashing |
| **REST Serialization**| Jackson Databind | 2.17.0 | JSON REST API serialization (`/api/v1/...`) |
| **Testing** | JUnit 5 & Mockito | 5.10.2 / 5.11.0 | Unit and integration testing |
| **Build & CI** | Apache Maven / GitHub Actions| 3.9+ / Workflow | Build automation & automated CI pipeline |

---

## 🏗️ Architecture & Package Structure

The project strictly follows the **Controller $\rightarrow$ Service $\rightarrow$ DAO $\rightarrow$ Model/DTO** layered architecture:

```text
com.raniya.raniyamart/
├── controller/    # Servlet routers (Register, Login, Logout, Product, Cart, Checkout, Order, Health)
├── service/       # Business logic, input validation, and transaction boundaries
├── dao/           # Data Access Layer (PreparedStatement only, try-with-resources)
├── model/         # Database entities (User, Product, CartItem, Order, OrderItem, Review)
├── dto/           # Data Transfer Objects (UserRegisterDTO, UserResponseDTO, ApiResponse)
├── filter/        # EncodingFilter (UTF-8), AuthFilter (Session & RBAC checks)
├── listener/      # DBConnectionListener (HikariCP DataSource lifecycle manager)
├── util/          # PasswordUtil, ValidationUtil, JSONUtil, DBUtil
└── exception/     # Application exception hierarchy (AppException, ValidationException, etc.)
```

---

## 🔒 Security Standards & Checklist

- [x] **PreparedStatement Only**: 100% of SQL queries use parameterized arguments (`grep -rn "Statement)" src/`).
- [x] **BCrypt Hashing**: Passwords stored as salted BCrypt hashes (`jBCrypt`). Plaintext is never stored or logged.
- [x] **Session ID Regeneration**: Old session invalidated and new session created upon login to prevent session fixation.
- [x] **Role-Based Access Control**: `AuthFilter` protects `/cart`, `/checkout`, `/orders`, `/seller/*`, and `/admin/*`.
- [x] **No Stack Traces**: Custom `404.jsp` and `500.jsp` configured in `web.xml`.
- [x] **Output Escaping**: JSTL `<c:out>` and `fn:escapeXml` used on all user-supplied data in JSPs to prevent XSS.

---

## ⚡ Quick Start & Setup Instructions

### 1. Prerequisites
* JDK 17 installed
* Apache Maven 3.8+ installed

### 2. Build & Test
Clone the repository and run the test suite:
```bash
git clone https://github.com/your-username/raniyamart.git
cd raniyamart
mvn clean verify
```

### 3. Run Locally
Deploy the built `target/raniyamart.war` to Apache Tomcat 9.0.x or run via Maven:
```bash
mvn package
```
Access the web application at:
`http://localhost:8080/raniyamart`

### 4. Health Endpoint Verification
```http
GET /api/v1/health
```
Response:
```json
{
  "status": "UP",
  "db": "UP"
}
```

---

## 🔑 Demo Seed Accounts

| Role | Email | Password |
| :--- | :--- | :--- |
| **Admin** | `admin@raniyamart.com` | `Password123!` |
| **Seller** | `seller@raniyamart.com` | `Password123!` |
| **Buyer** | `buyer@raniyamart.com` | `Password123!` |

---

## 📄 License
This project is submitted as part of the Anna University R2025 Semester 3 Capstone Requirement.
