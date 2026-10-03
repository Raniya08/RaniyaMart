# RaniyaMart Capstone Final Presentation Slide Deck

---

## Slide 1: Title & Project Overview
- **Project Name**: RaniyaMart E-Commerce Platform
- **Builder**: Solo Capstone Project
- **Tech Stack**: Java Servlets (JDK 17), JDBC, Apache Tomcat 9.0.x, H2 Database, Maven, Render Cloud PaaS
- **Domain**: Multi-Role E-Commerce with AI Shopping Assistance

---

## Slide 2: Problem Statement & Objectives
- **Problem**: Modern e-commerce platforms require seamless multi-role interactions (Buyer, Seller, Admin), secure transaction handling, Indian Rupee (₹) pricing, persistent database storage, and instant AI customer support.
- **Objectives**: Build a lightweight, high-performance web application satisfying all capstone requirements (F1–F8 + O4 Chatbot) with 100% test coverage and cloud deployment.

---

## Slide 3: System Architecture (4-Tier Layering)
- **View Layer**: JSP views + JSTL escaping + custom CSS3 + `chatbot.js`.
- **Controller Layer**: Front Controller Servlets (`ProductServlet`, `CartServlet`, `OrderServlet`, `AdminServlet`, `ChatServlet`).
- **Service Layer**: Business rules validation, transactional workflows (`UserService`, `ProductService`, `OrderService`).
- **DAO Layer**: Pure JDBC data access using `PreparedStatement` and HikariCP connection pool.

---

## Slide 4: Key Platform Features (F1 – F8)
- **F1**: BCrypt User Registration & Authentication (Buyer, Seller, Admin).
- **F2 & F3**: Seller Product Management & Buyer Category/Keyword Search.
- **F4 & F5**: Cart Management & Transactional Checkout.
- **F6**: Order History & Status Workflow (`PENDING → CONFIRMED → SHIPPED → DELIVERED`).
- **F7**: Global Admin Moderation (User listing audit & deletion).
- **F8**: 5-Star Product Reviews & Ratings.

---

## Slide 5: Deliverable O4 — AI Chatbot Assistant
- **Architecture**: `ChatProvider` Strategy Pattern (`MockChatProvider` + `GeminiChatProvider`).
- **Factory**: `ChatProviderFactory` dynamically instantiates provider based on runtime settings.
- **Proxy Endpoint**: `/api/chat` with input length limits (500 chars), per-session rate limiting (10 msgs/min), and response caching.
- **UI**: Floating widget (`chatbot.js`) with quick-action FAQ buttons on all pages.

---

## Slide 6: Security Checklist Verification
- **100% Parameterized Queries**: All SQL uses `PreparedStatement`.
- **Password Protection**: Salted BCrypt password hashing.
- **Access Control**: Session-based `AuthFilter` on protected routes.
- **XSS Prevention**: JSTL output escaping (`<c:out>`).
- **Clean Error Handling**: Stack traces hidden behind custom error pages (`404.jsp`, `500.jsp`).

---

## Slide 7: Database Design & Schema
- **Tables**: `users`, `products`, `orders`, `order_items`, `cart_items`, `reviews`.
- **Foreign Keys & Constraints**: Strict relational integrity, `DECIMAL(10,2)` for currency, `UNIQUE` on user emails.
- **Persistence**: File-backed persistent H2 database (`./data/raniyamartdb.mv.db`).

---

## Slide 8: Automated Testing & Verification
- **Test Suite**: JUnit 5 test suite targeting embedded H2 database.
- **Results**: **16 / 16 Unit Tests Passed Cleanly (100% Success Rate)**.
- **CI/CD Integration**: GitHub Actions automated Maven verify build on every push.

---

## Slide 9: Live Cloud Deployment
- **Live URL**: [https://raniyamart.onrender.com](https://raniyamart.onrender.com)
- **Health Check**: `GET /api/v1/health` returning `{"status":"UP","db":"UP"}`.
- **Auto Deployment**: Automated CD trigger on pushes to GitHub `main` branch.

---

## Slide 10: Conclusion & Demo Rehearsal
- **Achievements**: Completed all core features (F1–F8), mandatory AI chatbot (O4), security audit, documentation, and live cloud deployment.
- **Live Demo Overview**:
  1. Register & Login as Buyer / Seller / Admin.
  2. Browse, Filter, Add to Cart & Checkout.
  3. Update Order Status as Seller & Review as Buyer.
  4. Moderate Listings as Admin.
  5. Interact with AI Chatbot floating widget.
