# RaniyaMart MVP Review — 2-Slide Summary Deck

**Student / Builder**: Solo  
**Course Context**: Anna University R2025, Semester 3 Capstone  
**Checkpoint**: Aug 10 MVP Review  

---

## 📽️ Slide 1: Problem Statement & Architecture Sketch

### Problem Statement
Modern e-commerce requires an end-to-end multi-role web platform (Buyer, Seller, Admin) that ensures strict security standards, real-time inventory management, transactional integrity during checkout, and seamless catalog discovery—without compromising performance or software layering.

### Architecture Sketch & Tech Stack
```text
[ Browser / Front-End (JSP + JSTL + CSS Glassmorphism + JS Chatbot) ]
                               │ HTTP / REST JSON (/api/v1/...)
                               ▼
  [ Servlet Layer (AuthFilter, Controller Servlets, EncodingFilter) ]
                               │ DTO Mapping (UserResponseDTO, ApiResponse)
                               ▼
        [ Service Layer (UserService, ProductService, OrderService) ]
                               │ Business Rules & Validation
                               ▼
          [ DAO Layer (UserDAO, ProductDAO, OrderDAO, ReviewDAO) ]
                               │ PreparedStatement Only (No SQL Injection)
                               ▼
          [ HikariCP Pool ──► Embedded H2 / MySQL Database ]
```

* **Core Stack**: Java 17, Java Servlets (`javax.servlet.*`), JDBC, HikariCP, Tomcat 9.0.x, jBCrypt, Jackson, JUnit 5 + Mockito.
* **Security**: Salted BCrypt password hashing, session ID regeneration on login, RBAC filters, custom error pages without stack traces.

---

## 📽️ Slide 2: Completed Work vs. Planned Roadmap

### ✅ Completed Work (Weeks 1 – 3 Deliverables)
1. **F1 (Authentication & Security)**: Buyer & Seller registration/login, seeded Admin account, BCrypt hashing, `AuthFilter` session guards, session timeout & regeneration.
2. **F2 (Seller Listing Management)**: Create, edit, and delete product listings with live stock level indicators.
3. **F3 (Buyer Browse & Search/Filter)**: Catalog category filter, keyword search, and price sorting (`Price: Low to High`, `Price: High to Low`, `Newest`).
4. **F4 & F5 (Cart & Checkout)**: Add/update/remove cart items with running totals; transactional checkout with mock payment confirmation and atomic stock deduction.
5. **F8 (Reviews & Ratings)**: Product review storage and star ratings.
6. **REST JSON APIs**: Standard response envelope format `{ "success": true, "data": ..., "error": null }` under `/api/v1/...`.
7. **CI & Automated Testing**: JUnit 5 + Mockito test suite passing cleanly in GitHub Actions (`mvn clean verify`).

### 🎯 Planned Roadmap (Weeks 4 – 11)
* **Week 4**: Admin Panel user/order management & listing moderation (F7), Buyer order history (F6).
* **Weeks 5–7**: Order status workflow (O2), security audit checklist, JMeter load testing.
* **Weeks 8–10**: Cloud deployment, `/api/v1/health` uptime monitor, and **AI Chatbot Assistant** (`ChatProvider` interface, rate-limiting, floating JS UI widget).
