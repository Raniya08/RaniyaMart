# RaniyaMart — System Architecture & Design Specification

## 1. Architectural Overview
RaniyaMart follows a **3-Tier Model-View-Controller (MVC)** enterprise pattern built with Java EE technologies:

```
[ Client Browser / Mobile Web ]
            │ (HTTP/HTTPS)
            ▼
┌──────────────────────────────────────────────┐
│  Presentation Layer                          │
│  - JSP (JavaServer Pages) & JSTL 1.2         │
│  - Responsive CSS3, JavaScript, AI Chatbot   │
└──────────────────────┬───────────────────────┘
                       │
                       ▼
┌──────────────────────────────────────────────┐
│  Controller Layer (Servlets & Filters)       │
│  - AuthenticationFilter, SecurityFilter      │
│  - ProductServlet, CartServlet, Checkout     │
│  - SellerServlet, AdminServlet, ChatServlet  │
└──────────────────────┬───────────────────────┘
                       │
                       ▼
┌──────────────────────────────────────────────┐
│  Service Layer (Business Logic)              │
│  - ProductService, OrderService, CartService │
│  - UserService, NotificationService, Chat    │
└──────────────────────┬───────────────────────┘
                       │
                       ▼
┌──────────────────────────────────────────────┐
│  Data Access Layer (DAO Pattern)             │
│  - UserDAO, ProductDAO, OrderDAO, CartDAO    │
│  - HikariCP Connection Pooling               │
│  - H2 Relational Database Engine (MySQL Mode)│
└──────────────────────────────────────────────┘
```

## 2. Key Design Patterns
1. **Model-View-Controller (MVC)**: Clean separation of presentation from business logic and persistence.
2. **Data Access Object (DAO)**: Abstracting and encapsulating all access to the data source.
3. **Data Transfer Object (DTO)**: Safe data encapsulation (`UserResponseDTO`) preventing sensitive field leakage.
4. **Connection Pool (HikariCP)**: Efficient reuse of database connections to minimize latency.
5. **Role-Based Access Control (RBAC)**: Enforced via `AuthFilter` for BUYER, SELLER, and ADMIN roles.
