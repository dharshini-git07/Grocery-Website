# Shri Dev BalaKrishna Store

Full-stack grocery store project for Shri Dev BalaKrishna Store, Erode.

The project contains:

- A Spring Boot REST API backend
- A MySQL database setup script
- A plain HTML/CSS/JavaScript frontend
- JWT login/signup
- Product catalog, search, cart, and order placement
- English and Tamil product display

## Project Folders

```text
field study/
  GroceryStoreBackend/
  Shri Bala Krishna Store Web/
  images/
```

## Tech Stack

- Java 25
- Spring Boot 3.5.6
- Maven
- MySQL 8
- Spring Data JPA
- Spring Security
- JWT
- HTML, CSS, JavaScript

## Run Locally

### 1. Database

```bash
cd "d:\Java Lab\field study\GroceryStoreBackend"
mysql -u root -p < database_setup.sql
```

### 2. Backend

```bash
mvn spring-boot:run
```

Backend runs at:

```text
http://localhost:8080
```

### 3. Frontend

Open:

```text
d:\Java Lab\field study\Shri Bala Krishna Store Web\index.html
```

The frontend uses `http://localhost:8080/api`.

## Configuration

`src/main/resources/application.properties` supports environment variables:

```properties
DATABASE_URL
DATABASE_USER
DATABASE_PASSWORD
JWT_SECRET
JWT_EXPIRATION
WHATSAPP_API_TOKEN
```

Default local database:

```text
jdbc:mysql://localhost:3306/grocery_store_db
root / root
```

## API Endpoints

### Authentication

```text
POST /api/auth/signup
POST /api/auth/login
```

Signup body:

```json
{
  "name": "Test User",
  "email": "test@example.com",
  "password": "test123",
  "phone": "9994411370"
}
```

### Products

```text
GET    /api/products
GET    /api/products/{id}
GET    /api/products/category/{category}
GET    /api/products/search?q=rice
POST   /api/products
PUT    /api/products/{id}
DELETE /api/products/{id}
```

### Orders

Authenticated requests require:

```text
Authorization: Bearer <token>
```

```text
POST /api/orders?userId={id}
GET  /api/orders/user/{userId}
GET  /api/orders/{id}
PUT  /api/orders/{id}/status?status=CONFIRMED
```

Order body:

```json
{
  "items": [
    {
      "productId": 1,
      "quantity": 2,
      "price": 50
    }
  ],
  "totalAmount": 100,
  "deliveryAddress": "Erode Main Road",
  "deliveryCity": "Erode",
  "deliveryPostalCode": "638001",
  "notes": "Deliver in the evening"
}
```

## Demo Checklist

- Backend starts without errors.
- `GET /api/products` returns seeded products.
- Frontend products load from backend.
- Signup and login return a token.
- Cart count updates correctly.
- Checkout creates an order.
- Tamil toggle shows readable Tamil text.

## Notes

WhatsApp integration is demo-safe: the backend formats and logs the message. A real WhatsApp Business/Twilio integration can be added later without changing the checkout flow.
