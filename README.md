# RaniyaMart — Multi-Role E-Commerce Capstone Application

**Anna University R2025, Semester 3 Capstone Project**  
**Submission Milestone**: Sep 21 / Sep 23 Checkpoint — Full Build, Persistent Storage & Deployment Readiness  
**Tech Stack**: Java 17 · Java Servlets (`javax.servlet.*`) · JDBC · Apache Tomcat 9.0.x / Jetty 10.0.19 · HikariCP · jBCrypt · H2 File Database (`./data/raniyamartdb`) · JUnit 5  

---

## 🌟 Overview
**RaniyaMart** is a full-featured, secure, multi-role e-commerce web application built using standard Java EE 8 Servlets, JDBC, and Tomcat 9.0.x. It supports complete user workflows for Buyers, Sellers, and Administrators, featuring password encryption via BCrypt, session security, parameterized queries, HikariCP connection pooling, interactive cart & checkout management, order status tracking workflows, customer reviews, and persistent file-based database storage.

---

## 🛠️ Technology Stack Table

| Layer | Component / Library | Version | Description |
| :--- | :--- | :--- | :--- |
| **Language** | Java Development Kit (JDK) | 17 | Core programming platform |
| **Web Container** | Apache Tomcat / Servlet API / Jetty | 9.0.x / 4.0.1 / 10.0.19 | Java EE 8 Web Application Server |
| **Database** | Persistent H2 File DB / MySQL | 2.2.224 | Disk-persisted relational database (`./data/raniyamartdb`) |
| **Connection Pool**| HikariCP | 5.1.0 | High-performance JDBC connection pool |
| **Security** | jBCrypt | 0.4 | Salted BCrypt password hashing |
| **REST Serialization**| Jackson Databind | 2.17.0 | JSON REST API serialization (`/api/v1/...`) |
| **Testing** | JUnit 5 & Mockito | 5.10.2 / 5.11.0 | Unit and integration testing (`mvn clean verify`) |
| **Build & CI** | Apache Maven / GitHub Actions| 3.9+ / Workflow | Build automation & automated CI pipeline |

---

## 🚀 Features Implemented (Sep 21 / Sep 23 Checkpoint)

| Feature ID | Feature Name | Description & Workflow |
| :--- | :--- | :--- |
| **F1** | Authentication & Roles | Registration, Login, Logout with BCrypt password hashing. Session security (30 min timeout, fixation protection). Roles: Buyer, Seller, Admin. |
| **F2** | Seller Listings | Sellers create, edit, update stock levels, and delete product listings. |
| **F3** | Buyer Search & Filter | Browse catalog priced in Indian Rupees (₹). Search by keyword, filter by category (`Electronics`, `Fashion`, `Furniture`), and sort by price (`price_asc`, `price_desc`, `newest`). |
| **F4** | Cart Management | Add items to cart, update quantity, remove items, live grand total computation. |
| **F5** | Mock Checkout & Payment | Place order with shipping details, mock instant confirmation, and transactional stock deduction. |
| **F6 & O2** | Order Tracking & Workflow | Buyer order history view (`/orders`). Seller incoming orders view (`/seller/dashboard`) with order status transitions (`PENDING` $\rightarrow$ `CONFIRMED` $\rightarrow$ `SHIPPED` $\rightarrow$ `DELIVERED` $\rightarrow$ `CANCELLED`). |
| **F7** | Admin Panel & Moderation | Admin dashboard (`/admin/dashboard`) to inspect users, system orders, and moderate/remove inappropriate listings (`/admin/product/delete`). |
| **F8** | Product Reviews & Ratings | 1–5 star rating submission form (`/product/review`), average rating calculation, and customer review list on product detail view (`/product?id=...`). |
| **SEC** | Security Hardening | 100% PreparedStatements, top-of-service validation, XSS escaping (`c:out`), custom `404.jsp`/`500.jsp` pages, and `/api/v1/health`. |
| **DATA** | Persistent Storage | File-based H2 storage (`./data/raniyamartdb`). All user registrations, products, cart items, orders, and reviews persist permanently across server restarts. |

---

## 🏗️ Architecture & Package Structure

The project follows the **Controller $\rightarrow$ Service $\rightarrow$ DAO $\rightarrow$ Model/DTO** layered architecture:

```text
com.raniya.raniyamart/
├── controller/    # Servlets (RegisterServlet, LoginServlet, LogoutServlet, ProductServlet, CartServlet, CheckoutServlet, OrderServlet, SellerServlet, AdminServlet, ReviewServlet, HealthServlet, ProductApiController)
├── service/       # Business logic, top-of-method input validation, transaction boundaries
├── dao/           # Data Access Layer (PreparedStatement only, try-with-resources)
├── model/         # Database entities (User, Product, CartItem, Order, OrderItem, Review)
├── dto/           # Data Transfer Objects (UserRegisterDTO, UserResponseDTO, ApiResponse)
├── filter/        # EncodingFilter (UTF-8), AuthFilter (Session & RBAC checks)
├── listener/      # DBConnectionListener (HikariCP DataSource lifecycle manager)
├── util/          # PasswordUtil, ValidationUtil, JSONUtil, DBUtil
└── exception/     # Application exception hierarchy (AppException, ValidationException, etc.)
```

---

## 🔒 Security Standards Checklist

- [x] **PreparedStatement Only**: 100% of SQL queries use parameterized arguments (`grep -rn "Statement)" src/`).
- [x] **BCrypt Hashing**: Passwords stored as salted BCrypt hashes (`jBCrypt`). Plaintext is never stored or logged.
- [x] **Session Security**: Session invalidated and regenerated upon login; 30-minute timeout; HTTP-only cookies.
- [x] **Role-Based Access Control**: `AuthFilter` protects `/cart`, `/checkout`, `/orders`, `/seller/*`, and `/admin/*`.
- [x] **No Stack Traces**: Custom `404.jsp` and `500.jsp` configured in `web.xml`.
- [x] **Output Escaping**: JSTL `<c:out>` and `${fn:escapeXml(...)}` used on all user-supplied data in JSPs to prevent XSS.
- [x] **Git Hygiene**: `.gitignore` excludes `./data/`, `*.mv.db`, and `config.properties`.

---

## ⚡ Quick Start & Setup Instructions

### 1. Build & Test Suite
```bash
mvn clean verify
```
*Executes all 11 JUnit 5 & Mockito test cases with 0 errors and builds `target/raniyamart.war`.*

### 2. Run Web Application Server
```bash
mvn jetty:run
```
Access the application at: **[http://localhost:8080/RaniyaMart/](http://localhost:8080/RaniyaMart/)**

### 3. REST Health Check Verification
```http
GET http://localhost:8080/RaniyaMart/api/v1/health
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
This project is submitted as part of the Anna University R2025 Semester 3 Capstone Requirement (Sep 21 / Sep 23 Checkpoint Milestone).
