# RaniyaMart Sprint Retrospectives (Weeks 1 – 11)

This log records sprint retrospectives across the 11-week capstone development cycle (Jul 27 – Oct 10, 2026) for RaniyaMart.

---

### Sprint 1 (Jul 27 – Aug 2): Authentication & Base DAO Layer
- **What Worked**: Established clean Servlet + HikariCP connection pool setup with embedded H2 database for zero-config local testing.
- **What Didn't**: Initial password hashing had slight discrepancy in salt round configuration across test cases.
- **Action Item**: Standardize BCrypt hashing with 10 salt rounds inside `PasswordUtil`.

---

### Sprint 2 (Aug 3 – Aug 9): Core Shopping Flow (Browse → Cart → Order)
- **What Worked**: Strict separation of Servlet controllers, Service business rules, and DAO database queries.
- **What Didn't**: Dynamic cart totals occasionally failed to recalculate when item stock quantity was reduced.
- **Action Item**: Implement transactional cart validation inside `CartServiceImpl`.

---

### Sprint 3 (Aug 10 – Aug 16): MVP Review & Seller Dashboard Foundation
- **What Worked**: Successfully presented full end-to-end user journey (Register → Browse → Cart → Checkout) at the MVP Review checkpoint.
- **What Didn't**: Direct model objects were being passed to JSP views, exposing internal schema fields.
- **Action Item**: Enforce DTO transformation (`UserResponseDTO`) for all controller-to-view data passes.

---

### Sprint 4 (Aug 17 – Aug 23): Seller Dashboard & Admin Moderation
- **What Worked**: Built seller product management (create, edit, delete listings) and global admin user/listing moderation.
- **What Didn't**: Non-admin users were able to manually type `/admin/dashboard` URL without getting blocked.
- **Action Item**: Update `AuthFilter` URL patterns to enforce strict role authorization on `/admin/*` and `/seller/*`.

---

### Sprint 5 (Aug 24 – Aug 30): Search Refinement & Order Workflow (O2)
- **What Worked**: Combined category + keyword multi-filter queries in `ProductDAOImpl` using parameterized dynamic SQL.
- **What Didn't**: Order status transitions were unconstrained (e.g. allowing `DELIVERED` directly from `PENDING`).
- **Action Item**: Enforce strict status workflow validation: `PENDING → CONFIRMED → SHIPPED → DELIVERED`.

---

### Sprint 6 (Aug 31 – Sep 6): Product Reviews (F8) & App Hardening
- **What Worked**: Integrated 5-star customer review submission and average rating calculations on product detail pages.
- **What Didn't**: Empty cart checkout attempts resulted in unhandled null pointers.
- **Action Item**: Add pre-condition validation checks at top of `OrderServiceImpl.placeOrder()`.

---

### Sprint 7 (Sep 7 – Sep 13): Security Hardening & Audit
- **What Worked**: Audited codebase for 100% `PreparedStatement` usage, HTML output escaping via JSTL, and zero hardcoded credentials.
- **What Didn't**: Jetty port 8080 binding conflicts occurred during rapid background test executions.
- **Action Item**: Configured clean socket teardown and explicit port checks in test environment scripts.

---

### Sprint 8 (Sep 14 – Sep 20): Deployment & Full Build Review
- **What Worked**: Deployed WAR container to Render.com with persistent H2 file storage and automated GitHub deployment trigger (`main` branch).
- **What Didn't**: Health check endpoint `/api/v1/health` initially lacked database connection status checks.
- **Action Item**: Updated `HealthServlet` to verify active DB connection status (`{"status":"UP","db":"UP"}`).

---

### Sprint 9 (Sep 21 – Sep 27): AI Chatbot Backend (O4)
- **What Worked**: Created extensible `ChatProvider` interface, `MockChatProvider` for offline domain FAQ, and `GeminiChatProvider` proxy with automatic degraded fallback.
- **What Didn't**: Rapid API calls from single session could overwhelm external LLM providers.
- **Action Item**: Implemented 10 msgs/min per-session rate limiter and in-session query caching in `ChatServlet`.

---

### Sprint 10 (Sep 28 – Oct 4): Chatbot UI & Floating Widget Integration
- **What Worked**: Integrated sleek floating chat widget (`chatbot.js`) with instant quick-FAQ buttons across all web pages.
- **What Didn't**: Chat panel initially blocked mobile view navigation buttons.
- **Action Item**: Refactored CSS positioning and responsive Z-index styling for floating widget container.

---

### Sprint 11 (Oct 5 – Oct 10): Final Regression, Documentation & Review
- **What Worked**: Passed 100% of unit tests (16/16 green), updated complete architectural documentation (D1, D2, D3 diagrams), and verified live URL (`https://raniyamart.onrender.com`).
- **What Didn't**: Initial README lacked embedded sequence diagram visual specs.
- **Action Item**: Embedded Mermaid diagrams directly into `README.md` and finalized `docs/FINAL_REPORT.md`.
