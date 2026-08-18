-- RaniyaMart Demo Seed Dataset
-- Currency: Indian Rupee (₹)

-- Password for all demo accounts is 'Password123!' hashed with BCrypt
INSERT INTO users (full_name, email, password_hash, role) VALUES
('Raniya Admin', 'admin@raniyamart.com', '$2a$10$8.UnVuG9HHgffUDAlk8qfOuVGkqRzgVymGe07Xd0D1BPH6.J5v.2a', 'ADMIN'),
('Apex Tech Sellers', 'seller@raniyamart.com', '$2a$10$8.UnVuG9HHgffUDAlk8qfOuVGkqRzgVymGe07Xd0D1BPH6.J5v.2a', 'SELLER'),
('Jane Buyer', 'buyer@raniyamart.com', '$2a$10$8.UnVuG9HHgffUDAlk8qfOuVGkqRzgVymGe07Xd0D1BPH6.J5v.2a', 'BUYER');

-- Expanded Sample Products with Indian Rupee (₹) Pricing
INSERT INTO products (seller_id, name, description, price, stock_qty, category, image_url) VALUES
(2, 'UltraBook Pro 15', 'High-performance laptop with Intel Core i7, 32GB RAM, 1TB NVMe SSD, and 4K OLED display.', 89999.00, 15, 'Electronics', 'https://images.unsplash.com/photo-1496181133206-80ce9b88a853?auto=format&fit=crop&w=600&q=80'),
(2, 'Wireless Noise-Canceling Headphones', 'Premium over-ear Bluetooth headphones with active noise cancellation and 30-hour battery life.', 18490.00, 40, 'Electronics', 'https://images.unsplash.com/photo-1505740420928-5e560c06d30e?auto=format&fit=crop&w=600&q=80'),
(2, 'Flagship Smartphone 5G 256GB', 'Next-gen 5G smartphone with 108MP quad camera, 120Hz AMOLED display, and 5000mAh battery.', 54999.00, 25, 'Electronics', 'https://images.unsplash.com/photo-1511707171634-5f897ff02aa9?auto=format&fit=crop&w=600&q=80'),
(2, '4K Ultra HD Smart OLED TV 55"', 'Cinematic 55-inch Smart TV with Dolby Vision, HDR10+, and built-in surround sound speakers.', 64990.00, 10, 'Electronics', 'https://images.unsplash.com/photo-1593784991095-a205069470b6?auto=format&fit=crop&w=600&q=80'),
(2, 'Mechanical RGB Gaming Keyboard', 'Tactile mechanical gaming keyboard with customizable per-key RGB backlighting and wrist rest.', 5999.00, 50, 'Electronics', 'https://images.unsplash.com/photo-1587829741301-dc798b83add3?auto=format&fit=crop&w=600&q=80'),
(2, 'Ergonomic Wireless Mouse', 'Precision optical mouse with dual Bluetooth/2.4G connectivity and silent click buttons.', 2499.00, 60, 'Electronics', 'https://images.unsplash.com/photo-1615663245857-ac93bb7c39e7?auto=format&fit=crop&w=600&q=80'),
(2, 'Minimalist Leather Wristwatch', 'Classic analog chronograph wristwatch with genuine Italian leather strap and sapphire glass.', 9999.00, 30, 'Fashion', 'https://images.unsplash.com/photo-1523275335684-37898b6baf30?auto=format&fit=crop&w=600&q=80'),
(2, 'Premium Denim Jacket', 'Classic vintage-washed indigo denim jacket crafted from 100% breathable organic cotton.', 3499.00, 35, 'Fashion', 'https://images.unsplash.com/photo-1576995853123-5a10305d93c0?auto=format&fit=crop&w=600&q=80'),
(2, 'Pro Athletic Running Shoes', 'Lightweight cushioned performance sneakers engineered for long-distance marathon running.', 4299.00, 45, 'Fashion', 'https://images.unsplash.com/photo-1542291026-7eec264c27ff?auto=format&fit=crop&w=600&q=80'),
(2, 'Ergonomic Mesh Office Chair', 'Adjustable lumbar support chair with breathable mesh back, 3D armrests, and tilt lock mechanism.', 14999.00, 15, 'Furniture', 'https://images.unsplash.com/photo-1580481072645-022f9a6d1270?auto=format&fit=crop&w=600&q=80'),
(2, 'Solid Teak Wood Study Table', 'Spacious wooden desk featuring integrated storage drawers and cable management ports.', 12499.00, 12, 'Furniture', 'https://images.unsplash.com/photo-1518455027359-f3f8164ba6bd?auto=format&fit=crop&w=600&q=80'),
(2, 'Smart HEPA Air Purifier', 'Real-time air quality sensor purifier with true HEPA filtration removing 99.97% of pollutants.', 8999.00, 20, 'Furniture', 'https://images.unsplash.com/photo-1585771724684-38269d6639fd?auto=format&fit=crop&w=600&q=80'),
(2, 'Espresso & Coffee Machine', '15-bar Italian pump espresso coffee maker with integrated milk frother steam wand.', 6499.00, 25, 'Furniture', 'https://images.unsplash.com/photo-1517668808822-9ebe02f2a6e8?auto=format&fit=crop&w=600&q=80');
