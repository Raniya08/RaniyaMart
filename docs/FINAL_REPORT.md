# RaniyaMart Capstone Project — Final Technical Report
**Course**: Java Servlets · JDBC · Apache Tomcat — Anna University R2025 (Semester 3)  
**Project Name**: RaniyaMart  
**Package Name**: `com.raniya.raniyamart`  
**Live Deployment URL**: [https://raniyamart.onrender.com](https://raniyamart.onrender.com)  
**GitHub Repository**: [https://github.com/Raniya08/RaniyaMart.git](https://github.com/Raniya08/RaniyaMart.git)

---

##  EXECUTIVE SUMMARY

RaniyaMart is an enterprise-grade multi-role e-commerce web application engineered with pure Java Servlets (JAX-RS spec), JDBC, and Apache Tomcat 9.0.x. Designed around strict architectural layering, it provides end-to-end shopping capabilities for **Buyers**, listing management and order processing for **Sellers**, system-wide moderation for **Administrators**, and an intelligent **AI Chatbot Assistant** for real-time customer assistance.

All product prices are displayed in Indian Rupees (₹), and all data persists cleanly in an embedded file-backed H2 relational database (`./data/raniyamartdb.mv.db`).

---

## 📐 ARCHITECTURE & SYSTEM DESIGN

### System Layering Architecture
RaniyaMart follows a strict 4-tier MVC architecture:
1. **Presentation Layer (View)**: Modular JSP templates enhanced with JSTL `<c:out>` escaping, CSS3 custom properties, and asynchronous JavaScript widgets (`chatbot.js`, `app.js`).
2. **Controller Layer (Front Controller)**: Lightweight `HttpServlet` controllers (`ProductServlet`, `CartServlet`, `OrderServlet`, `AdminServlet`, `ChatServlet`) mapped via `@WebServlet` annotations.
3. **Service Layer (Business Logic)**: Enterprise services (`UserService`, `ProductService`, `CartService`, `OrderService`, `ReviewService`, `ChatProvider`) encapsulating input validation, domain rules, and transactional workflows.
4. **Data Access Layer (DAO)**: Pure JDBC implementations (`UserDAOImpl`, `ProductDAOImpl`, `OrderDAOImpl`, `CartItemDAOImpl`, `ReviewDAOImpl`) utilizing `PreparedStatement` and HikariCP connection pooling.

---

## 📊 DIAGRAMS

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

## 🛠️ DESIGN PATTERNS IMPLEMENTED

1. **DAO (Data Access Object) Pattern**: Decouples persistent database code (`UserDAOImpl`, `ProductDAOImpl`, `OrderDAOImpl`) from higher-level business services (`UserService`, `ProductService`).
2. **Front Controller Pattern**: `HttpServlet` instances (`ProductServlet`, `CartServlet`, `OrderServlet`, `AdminServlet`) act as centralized request dispatchers.
3. **Singleton Pattern**: Managed connection pool instance owned by `DBConnectionListener` and `ChatProviderFactory`.
4. **Factory Pattern**: `ChatProviderFactory` instantiates appropriate `ChatProvider` implementations (`MockChatProvider` vs `GeminiChatProvider`) dynamically based on runtime properties.
5. **Strategy Pattern**: `ChatProvider` interface allows swapping between canned offline FAQ logic and live external LLM API calls without altering controller handlers.
6. **Builder Pattern**: Constructing complex DTOs (`UserResponseDTO`, `UserRegisterDTO`, `ApiResponse`) cleanly.

---

## 🔒 SECURITY HARDENING CHECKLIST VERIFICATION

- ✅ **PreparedStatement Everywhere**: 100% of SQL operations utilize parameterized `PreparedStatement` parameters.
- ✅ **BCrypt Password Hashing**: Passwords stored as one-way salted BCrypt hashes (`$2a$10$...`).
- ✅ **Session Hijacking Protection**: Session invalidated and regenerated on successful login.
- ✅ **Role-Based Access Control (RBAC)**: `AuthFilter` protects `/seller/*` and `/admin/*` routes.
- ✅ **XSS Prevention**: Dynamic JSP text rendered with JSTL `<c:out>` and `fn:escapeXml`.
- ✅ **Excluded Credentials**: `config.properties` and `.env` excluded via `.gitignore`.
- ✅ **Error Handling**: Custom error pages (`404.jsp`, `500.jsp`) prevent stack trace exposure.

---

## ⚠️ KNOWN LIMITATIONS

1. **Payment Gateway Integration**: Payment processing currently executes via a simulated mock step rather than a live credit card payment gateway.
2. **H2 File Mode Locking**: H2 running in file mode requires single-process locking (`AUTO_SERVER=TRUE` enabled to allow concurrent administration).
