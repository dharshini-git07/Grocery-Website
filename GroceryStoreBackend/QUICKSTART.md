# Quick Start

This guide runs the Spring Boot backend and opens the HTML/CSS/JS frontend for a college demo.

## Requirements

- Java 25, or Java 17+ if you update `pom.xml`
- Maven 3.9+
- MySQL 8+
- A browser

## 1. Create The Database

From `GroceryStoreBackend`:

```bash
mysql -u root -p < database_setup.sql
```

The script creates `grocery_store_db`, the required tables, and sample products with English and Tamil names.

## 2. Configure Credentials

Defaults are in `src/main/resources/application.properties`:

```properties
spring.datasource.username=${DATABASE_USER:root}
spring.datasource.password=${DATABASE_PASSWORD:root}
```

If your MySQL password is different, either edit the file or set environment variables:

```powershell
$env:DATABASE_USER="root"
$env:DATABASE_PASSWORD="your_mysql_password"
```

## 3. Run The Backend

```bash
mvn spring-boot:run
```

Backend URL:

```text
http://localhost:8080
```

## 4. Open The Frontend

Open this file in your browser:

```text
../Shri Bala Krishna Store Web/index.html
```

The frontend calls:

```text
http://localhost:8080/api
```

## 5. Demo Flow

1. View products loaded from the backend.
2. Search and filter products.
3. Sign up with a test account.
4. Add products to cart.
5. Checkout and place an order.
6. Confirm the order appears in backend logs and database tables.

## Quick API Checks

```bash
curl http://localhost:8080/api/products
```

```bash
curl -X POST http://localhost:8080/api/auth/signup ^
  -H "Content-Type: application/json" ^
  -d "{\"email\":\"test@example.com\",\"password\":\"test123\",\"name\":\"Test User\",\"phone\":\"9994411370\"}"
```

## Common Fixes

- `mvn is not recognized`: install Maven and add it to PATH.
- `Access denied for user root`: update `DATABASE_PASSWORD`.
- Empty product list: run `database_setup.sql`.
- Checkout fails: login first, then place the order.
