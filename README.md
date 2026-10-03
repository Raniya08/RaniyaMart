# RaniyaMart — Enterprise E-Commerce Platform & AI Assistant

[![Java 17](https://img.shields.io/badge/Java-17-orange.svg)](https://www.oracle.com/java/)
[![Servlet 4.0](https://img.shields.io/badge/Servlet-4.0-blue.svg)](https://tomcat.apache.org/)
[![Build Status](https://img.shields.io/badge/Build-Passing-brightgreen.svg)](https://github.com/Raniya08/RaniyaMart)
[![Live Deployment](https://img.shields.io/badge/Render-Live-success.svg)](https://raniyamart.onrender.com)

**RaniyaMart** is an enterprise-grade multi-role e-commerce web application built using **Java Servlets**, **JDBC**, and **Apache Tomcat 9.0.x** for **Anna University R2025 (Semester 3)**.

- **Live Deployed Web Application**: [https://raniyamart.onrender.com](https://raniyamart.onrender.com)
- **Live Health Endpoint**: [https://raniyamart.onrender.com/api/v1/health](https://raniyamart.onrender.com/api/v1/health)
- **GitHub Repository**: [https://github.com/Raniya08/RaniyaMart.git](https://github.com/Raniya08/RaniyaMart.git)

---

## 🚀 Key Features

| ID | Feature | Description | Target Role | Status |
|---|---|---|---|---|
| **F1** | Authentication & AuthFilter | User Signup/Login with BCrypt password hashing, session timeout, and RBAC filter | Buyer, Seller, Admin | ✅ Complete |
| **F2** | Seller Listing Management | Create, Edit, Delete product listings with image URLs, category, and stock quantities | Seller | ✅ Complete |
| **F3** | Catalog Search & Filter | Multi-filter browsing by category, price sorting, and title search | Buyer | ✅ Complete |
| **F4** | Cart Management | Add/Update/Remove cart items with dynamic running totals | Buyer | ✅ Complete |
| **F5** | Transactional Checkout | Place orders from cart contents with mock payment confirmation step | Buyer | ✅ Complete |
| **F6** | Order Tracking & Workflow | Order history view for buyers; `Pending → Confirmed → Shipped → Delivered` status workflow for sellers | Buyer, Seller | ✅ Complete |
| **F7** | Admin Moderation | View all registered users and orders, moderate/remove product listings | Admin | ✅ Complete |
| **F8** | Product Reviews & Ratings | 5-star rating submission and review history on product detail pages | Buyer | ✅ Complete |
| **O4** | AI Shopping Assistant | Floating AI Chatbot widget (`chatbot.js`), `/api/chat` proxy with rate limiting, response caching, and Gemini/Mock fallback | All Users | ✅ Complete |

---

## 🛠️ Technology Stack

| Layer | Technology Used |
|---|---|
| **Language & Runtime** | Java 17 (JDK 17 LTS) |
| **Web Framework** | Java Servlets 4.0 (`javax.servlet.*`), JSP 2.3, JSTL 1.2 |
| **Application Server** | Apache Tomcat 9.0.x / Embedded Jetty |
| **Database & Pooling** | Embedded Persistent H2 Database (`./data/raniyamartdb.mv.db`), HikariCP Connection Pool |
| **Security** | BCrypt (`jBCrypt 0.4`), HTTPS Session Management, `AuthFilter` |
| **AI Engine** | Google Gemini 1.5 Flash REST API + Offline Canned Domain FAQ (`MockChatProvider`) |
| **Build & Test** | Apache Maven 3.8+, JUnit 5, Mockito |
| **Cloud Deployment** | Render.com PaaS Container Deployment |

---

## 📐 System Architecture & Diagrams

### 1. D1 Entity-Relationship (ER) Diagram
```mermaid
erDiagram
    USERS ||--o{ PRODUCTS : "lists / sells"
    USERS ||--o{ ORDERS : "places (buyer)"
    USERS ||--o{ REVIEWS : "writes"
    USERS ||--o{ CART_ITEMS : "owns"
    PRODUCTS ||--o{ CART_ITEMS : "contains"
    PRODUCTS ||--o{ ORDER_ITEMS : "included_in"
    PRODUCTS ||--o{ REVIEWS : "receives"
    ORDERS ||--|{ ORDER_ITEMS : "consists_of"

    USERS {
        bigint id PK
        varchar full_name
        varchar email UK
        varchar password_hash
        varchar role "BUYER | SELLER | ADMIN"
        timestamp created_at
    }

    PRODUCTS {
        bigint id PK
        bigint seller_id FK
        varchar name
        text description
        decimal price "DECIMAL(10,2)"
        int stock_qty
        varchar category
        varchar image_url
        timestamp created_at
    }

    ORDERS {
        bigint id PK
        bigint buyer_id FK
        decimal total_amount "DECIMAL(10,2)"
        varchar status "PENDING | CONFIRMED | SHIPPED | DELIVERED | CANCELLED"
        timestamp created_at
    }

    ORDER_ITEMS {
        bigint id PK
        bigint order_id FK
        bigint product_id FK
        int quantity
        decimal price "DECIMAL(10,2)"
    }

    CART_ITEMS {
        bigint id PK
        bigint user_id FK
        bigint product_id FK
        int quantity
        timestamp created_at
    }

    REVIEWS {
        bigint id PK
        bigint user_id FK
        bigint product_id FK
        int rating "1 to 5"
        text comment
        timestamp created_at
    }
```

---

### 2. D2 Use Case Diagram
```mermaid
flowchart TD
    subgraph Actors
        B["Buyer 👤"]
        S["Seller 🏪"]
        A["Admin ⚙️"]
        C["AI Chatbot 🤖"]
    end

    subgraph Features ["RaniyaMart Platform Features"]
        F1["F1: Register / Login (BCrypt Auth)"]
        F2["F2: Listing Management (Create/Edit/Delete)"]
        F3["F3: Product Catalog Search & Category Filter"]
        F4["F4: Cart Management & Running Total"]
        F5["F5: Checkout & Mock Payment"]
        F6["F6: Order Tracking & Incoming Status Workflow"]
        F7["F7: Global User & Listing Moderation"]
        F8["F8: Product Reviews & 5-Star Ratings"]
        O4["O4: AI Shopping Assistant Proxy (/api/chat)"]
    end

    B --> F1
    B --> F3
    B --> F4
    B --> F5
    B --> F6
    B --> F8
    B --> O4

    S --> F1
    S --> F2
    S --> F6
    S --> O4

    A --> F1
    A --> F7
    A --> O4

    O4 -.-> C
```

---

### 3. D3 Sequence Diagram: Place Order Flow
```mermaid
sequenceDiagram
    autonumber
    actor Buyer
    participant JSP as Checkout JSP
    participant Servlet as CheckoutServlet
    participant Service as OrderServiceImpl
    participant CartDAO as CartItemDAOImpl
    participant OrderDAO as OrderDAOImpl
    participant DB as H2 Relational DB

    Buyer->>JSP: Click "Place Order" (Submit Payment)
    JSP->>Servlet: POST /checkout (Session Auth Check)
    Servlet->>Service: placeOrder(buyerId)
    Service->>CartDAO: findByUserId(buyerId)
    CartDAO->>DB: SELECT * FROM cart_items WHERE user_id = ?
    DB-->>CartDAO: Return Cart Items
    CartDAO-->>Service: List<CartItem>
    Service->>Service: Calculate Total & Verify Stock Qty
    Service->>OrderDAO: createOrderWithItems(buyerId, cartItems, total)
    OrderDAO->>DB: BEGIN TRANSACTION
    OrderDAO->>DB: INSERT INTO orders (buyer_id, total_amount, status)
    OrderDAO->>DB: INSERT INTO order_items (...)
    OrderDAO->>DB: UPDATE products SET stock_qty = stock_qty - qty
    OrderDAO->>DB: COMMIT TRANSACTION
    DB-->>OrderDAO: Order #ID Created
    OrderDAO-->>Service: Order Entity
    Service->>CartDAO: clearCart(buyerId)
    CartDAO->>DB: DELETE FROM cart_items WHERE user_id = ?
    Service-->>Servlet: Success (Order #ID)
    Servlet-->>JSP: Redirect to /order-confirmation?id=#ID
    JSP-->>Buyer: Render Order Summary & Success Message
```

---

## 🏃 Quick Start Local Setup

```bash
# 1. Clone repo
git clone https://github.com/Raniya08/RaniyaMart.git
cd RaniyaMart

# 2. Run automated JUnit tests
mvn clean verify

# 3. Launch application locally on http://localhost:8080
mvn jetty:run
```

---

## 🔑 Demo Account Credentials

- **Admin Account**: `admin@raniyamart.com` | Password: `Password123!`
- **Seller Account**: `seller@raniyamart.com` | Password: `Password123!`
- **Buyer Account**: `buyer@raniyamart.com` | Password: `Password123!`

---

## 📁 Key Project Documents

- 📄 [CONTRIBUTING.md](file:///C:/Users/Hi/RaniyaMart/CONTRIBUTING.md) — Local development & setup guide
- 📜 [RETRO.md](file:///C:/Users/Hi/RaniyaMart/RETRO.md) — Sprint retrospectives (Weeks 1 – 11)
- 📊 [docs/FINAL_REPORT.md](file:///C:/Users/Hi/RaniyaMart/docs/FINAL_REPORT.md) — Comprehensive capstone report
- 🖥️ [docs/PRESENTATION_SLIDES.md](file:///C:/Users/Hi/RaniyaMart/docs/PRESENTATION_SLIDES.md) — Final review slide deck
