MERGE INTO users (email, name, password_hash, role)
KEY(email)
VALUES
(
    'admin@raniyamart.com',
    'RaniyaMart Admin',
    '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy',
    'ADMIN'
);