# RaniyaMart — API & Endpoint Documentation

## 1. Authentication Endpoints
- `GET /login`: Render login page.
- `POST /login`: Process user authentication (Email & BCrypt password verification).
- `GET /register`: Render registration page for BUYER or SELLER.
- `POST /register`: Register new account with validation.
- `GET /logout`: Invalidate session and redirect to login.

## 2. Catalog & Product Endpoints
- `GET /products`: Public catalog with search (`q`), category (`category`), and sort (`sort`) parameters.
- `GET /product?id={id}`: Product detail page with specifications and customer reviews.

## 3. Shopping Cart & Checkout Endpoints
- `GET /cart`: View items in cart with line item subtotals and grand total.
- `POST /cart/add`: Add product SKU to cart with specified quantity.
- `POST /cart/update`: Modify item quantity in cart.
- `POST /cart/remove`: Remove item from cart.
- `GET /checkout`: Secure checkout page with dynamic UPI, Card, and COD payment options.
- `POST /checkout`: Process order placement, clear cart, update inventory, and dispatch confirmation notification.

## 4. Seller Endpoints
- `GET /seller/dashboard`: Seller inventory overview and incoming order list.
- `POST /seller/product/add`: Add new product listing.
- `GET /seller/product/edit?id={id}`: Edit product listing form.
- `POST /seller/product/edit`: Update product details.
- `POST /seller/product/delete`: Delete product listing.
- `POST /seller/order/status`: Update fulfillment status (`CONFIRMED`, `SHIPPED`, `DELIVERED`).

## 5. Admin Endpoints
- `GET /admin/dashboard`: Global system metrics, user role overview, and catalog monitoring.
