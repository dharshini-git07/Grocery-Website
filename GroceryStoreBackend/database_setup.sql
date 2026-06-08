CREATE DATABASE IF NOT EXISTS grocery_store_db
  CHARACTER SET utf8mb4
  COLLATE utf8mb4_unicode_ci;

USE grocery_store_db;
SET NAMES utf8mb4;
SET CHARACTER SET utf8mb4;

CREATE TABLE IF NOT EXISTS users (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    name VARCHAR(255) NOT NULL,
    phone VARCHAR(20) NOT NULL,
    address VARCHAR(500),
    city VARCHAR(100),
    postal_code VARCHAR(20),
    created_at BIGINT,
    updated_at BIGINT,
    INDEX idx_users_email (email)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS products (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    tamil_name VARCHAR(255) NOT NULL,
    category VARCHAR(100) NOT NULL,
    price DOUBLE NOT NULL,
    unit VARCHAR(50) NOT NULL,
    description TEXT,
    tamil_description TEXT,
    image VARCHAR(500) NOT NULL,
    stock INT DEFAULT 100,
    created_at BIGINT,
    updated_at BIGINT,
    UNIQUE KEY uk_products_name (name),
    INDEX idx_products_category (category),
    INDEX idx_products_name (name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS orders (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    total_amount DOUBLE NOT NULL,
    status VARCHAR(50) DEFAULT 'PENDING',
    delivery_address VARCHAR(500) NOT NULL,
    delivery_city VARCHAR(100) NOT NULL,
    delivery_postal_code VARCHAR(20),
    notes TEXT,
    whatsapp_message_id VARCHAR(255),
    created_at BIGINT,
    updated_at BIGINT,
    CONSTRAINT fk_orders_user FOREIGN KEY (user_id) REFERENCES users(id),
    INDEX idx_orders_user_id (user_id),
    INDEX idx_orders_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS order_items (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    order_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    quantity INT NOT NULL,
    price DOUBLE NOT NULL,
    subtotal DOUBLE NOT NULL,
    CONSTRAINT fk_order_items_order FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE,
    CONSTRAINT fk_order_items_product FOREIGN KEY (product_id) REFERENCES products(id),
    INDEX idx_order_items_order_id (order_id),
    INDEX idx_order_items_product_id (product_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

SET FOREIGN_KEY_CHECKS = 0;
TRUNCATE TABLE order_items;
TRUNCATE TABLE orders;
TRUNCATE TABLE products;
SET FOREIGN_KEY_CHECKS = 1;

INSERT INTO products
    (name, tamil_name, category, price, unit, description, tamil_description, image, stock, created_at, updated_at)
VALUES
    ('Rice', 'அரிசி', 'grains', 50, 'kg', 'Premium quality rice from local farms', 'உள்ளூர் பண்ணைகளில் இருந்து கிடைக்கும் தரமான அரிசி', 'images/rice.jpg', 100, UNIX_TIMESTAMP() * 1000, UNIX_TIMESTAMP() * 1000),
    ('Dhal', 'பருப்பு', 'grains', 120, 'kg', 'Nutritious lentils for everyday meals', 'சத்தான அன்றாட உணவுக்கான பருப்பு', 'images/dhal.jpg', 100, UNIX_TIMESTAMP() * 1000, UNIX_TIMESTAMP() * 1000),
    ('Cooking Oil', 'சமையல் எண்ணெய்', 'oils', 150, 'liter', 'Pure cooking oil for home cooking', 'வீட்டு சமையலுக்கான தூய எண்ணெய்', 'images/Oil.jpg', 80, UNIX_TIMESTAMP() * 1000, UNIX_TIMESTAMP() * 1000),
    ('Spice Powders', 'மசாலா தூள்', 'spices', 80, 'packet', 'Authentic spice mix for traditional flavors', 'பாரம்பரிய சுவைக்கான அசல் மசாலா கலவை', 'images/powders.jpg', 90, UNIX_TIMESTAMP() * 1000, UNIX_TIMESTAMP() * 1000),
    ('Cosmetics', 'அழகு பொருட்கள்', 'cosmetics', 200, 'set', 'Daily-use beauty and personal care products', 'தினசரி பயன்பாட்டிற்கான அழகு மற்றும் பராமரிப்பு பொருட்கள்', 'images/cosmetics.jpg', 60, UNIX_TIMESTAMP() * 1000, UNIX_TIMESTAMP() * 1000),
    ('Wheat Flour', 'கோதுமை மாவு', 'grains', 40, 'kg', 'Finely ground wheat flour', 'நன்றாக அரைக்கப்பட்ட கோதுமை மாவு', '../images/wheat flour.png', 100, UNIX_TIMESTAMP() * 1000, UNIX_TIMESTAMP() * 1000),
    ('Sugar', 'சர்க்கரை', 'grains', 45, 'kg', 'Pure white sugar', 'தூய வெள்ளை சர்க்கரை', '../images/sugar.png', 100, UNIX_TIMESTAMP() * 1000, UNIX_TIMESTAMP() * 1000),
    ('Tea Powder', 'தேயிலை தூள்', 'beverages', 180, 'packet', 'Premium tea powder for fresh tea', 'சுவையான தேநீருக்கான தரமான தேயிலை தூள்', '../images/tea.png', 75, UNIX_TIMESTAMP() * 1000, UNIX_TIMESTAMP() * 1000),
    ('Milk', 'பால்', 'dairy', 60, 'liter', 'Fresh packed milk', 'புதிய பாக்கெட் பால்', '../images/milk.png', 50, UNIX_TIMESTAMP() * 1000, UNIX_TIMESTAMP() * 1000),
    ('Butter', 'வெண்ணெய்', 'dairy', 250, 'packet', 'Pure butter for cooking and baking', 'சமையல் மற்றும் பேக்கிங்கிற்கான தூய வெண்ணெய்', '../images/butter.png', 40, UNIX_TIMESTAMP() * 1000, UNIX_TIMESTAMP() * 1000),
    ('Cheese', 'சீஸ்', 'dairy', 300, 'packet', 'Processed cheese slices', 'செயலாக்கப்பட்ட சீஸ் துண்டுகள்', '../images/cheese.png', 35, UNIX_TIMESTAMP() * 1000, UNIX_TIMESTAMP() * 1000),
    ('Bread', 'ரொட்டி', 'bakery', 35, 'loaf', 'Fresh baked bread', 'புதியதாக சுடப்பட்ட ரொட்டி', '../images/bread.png', 45, UNIX_TIMESTAMP() * 1000, UNIX_TIMESTAMP() * 1000),
    ('Eggs', 'முட்டை', 'dairy', 6, 'piece', 'Farm fresh eggs', 'பண்ணையில் இருந்து கிடைக்கும் புதிய முட்டைகள்', '../images/eggs.png', 120, UNIX_TIMESTAMP() * 1000, UNIX_TIMESTAMP() * 1000),
    ('Potatoes', 'உருளைக்கிழங்கு', 'vegetables', 25, 'kg', 'Fresh potatoes', 'புதிய உருளைக்கிழங்கு', '../images/potatoes.png', 100, UNIX_TIMESTAMP() * 1000, UNIX_TIMESTAMP() * 1000),
    ('Onions', 'வெங்காயம்', 'vegetables', 30, 'kg', 'Red onions', 'சிவப்பு வெங்காயம்', '../images/onion.png', 100, UNIX_TIMESTAMP() * 1000, UNIX_TIMESTAMP() * 1000),
    ('Tomatoes', 'தக்காளி', 'vegetables', 35, 'kg', 'Ripe tomatoes', 'பழுத்த தக்காளி', '../images/tomatoes.png', 100, UNIX_TIMESTAMP() * 1000, UNIX_TIMESTAMP() * 1000)
ON DUPLICATE KEY UPDATE
    price = VALUES(price),
    stock = VALUES(stock),
    updated_at = UNIX_TIMESTAMP() * 1000;
