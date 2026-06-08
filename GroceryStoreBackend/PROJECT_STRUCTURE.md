# Project Structure

```text
GroceryStoreBackend/
  src/main/java/com/grocerystore/
    GroceryStoreApplication.java
    config/
      SecurityConfig.java
    controller/
      AuthController.java
      ProductController.java
      OrderController.java
    service/
      AuthService.java
      JwtService.java
      ProductService.java
      OrderService.java
      WhatsAppService.java
    entity/
      User.java
      Product.java
      Order.java
      OrderItem.java
    repository/
      UserRepository.java
      ProductRepository.java
      OrderRepository.java
      OrderItemRepository.java
    dto/
      AuthRequest.java
      AuthResponse.java
      UserDTO.java
      ProductDTO.java
      OrderDTO.java
      OrderItemDTO.java
  src/main/resources/
    application.properties
  pom.xml
  database_setup.sql
  README.md
  QUICKSTART.md

Shri Bala Krishna Store Web/
  index.html
  style.css
  script.js
  images/

images/
  Shared product images used by the frontend
```

## Flow

```text
Browser frontend
  -> Spring Boot REST API
  -> Spring Data JPA
  -> MySQL grocery_store_db
```

## Main Features

- Signup and login with JWT.
- Product listing, search, and category filter.
- Cart in browser local storage.
- Authenticated order placement.
- English/Tamil product display.
- Demo WhatsApp order message logging.
