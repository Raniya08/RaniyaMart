# RaniyaMart — Database Schema & Data Dictionary

## 1. Relational Entity Relationship Summary
The database is structured using Relational SQL tables with strict foreign key constraints:

- `users` (id, full_name, email, password_hash, role, created_at)
- `products` (id, seller_id, name, description, price, stock_qty, category, image_url, created_at)
- `cart_items` (id, buyer_id, product_id, quantity, created_at)
- `orders` (id, buyer_id, total_amount, status, created_at)
- `order_items` (id, order_id, product_id, quantity, price_at_purchase)
- `reviews` (id, product_id, buyer_id, rating, comment, created_at)

## 2. Table Specifications

### `users` Table
| Column | Type | Constraints | Description |
| :--- | :--- | :--- | :--- |
| `id` | BIGINT | PRIMARY KEY, AUTO_INCREMENT | Unique user identifier |
| `full_name` | VARCHAR(100) | NOT NULL | User's full display name |
| `email` | VARCHAR(150) | NOT NULL, UNIQUE | Login email address |
| `password_hash`| VARCHAR(255) | NOT NULL | BCrypt salted hash |
| `role` | VARCHAR(20) | NOT NULL | BUYER, SELLER, or ADMIN |
| `created_at` | TIMESTAMP | DEFAULT CURRENT_TIMESTAMP | Account creation date |

### `products` Table
| Column | Type | Constraints | Description |
| :--- | :--- | :--- | :--- |
| `id` | BIGINT | PRIMARY KEY, AUTO_INCREMENT | Unique product SKU |
| `seller_id` | BIGINT | FOREIGN KEY (`users.id`) | Seller owner ID |
| `name` | VARCHAR(200) | NOT NULL | Product name/title |
| `description` | TEXT | NOT NULL | Detailed description |
| `price` | DECIMAL(10,2)| NOT NULL | Unit price in INR (₹) |
| `stock_qty` | INT | NOT NULL, DEFAULT 0 | Available inventory |
| `category` | VARCHAR(100) | NOT NULL | Product category |
| `image_url` | VARCHAR(500) | NULL | HTTPS image link |

## 3. Data Integrity & Indexing
- Unique index on `users(email)` prevents duplicate account registrations.
- Foreign key constraints with cascading options protect order and cart integrity.
- Money values are stored as `DECIMAL(10,2)` to prevent floating-point calculation errors.
