package com.grocerystore.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItemDTO {
    private Long id;
    private Long productId;
    private String productName;
    private Integer quantity;
    private Double price;
    private Double subtotal;

    // Manual getters since Lombok annotation processing is not working with Java 25
    public Long getId() {
        return id;
    }

    public Long getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Double getPrice() {
        return price;
    }

    public Double getSubtotal() {
        return subtotal;
    }

    // Manual builder since Lombok annotation processing is not working with Java 25
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long id;
        private Long productId;
        private String productName;
        private Integer quantity;
        private Double price;
        private Double subtotal;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder productId(Long productId) {
            this.productId = productId;
            return this;
        }

        public Builder productName(String productName) {
            this.productName = productName;
            return this;
        }

        public Builder quantity(Integer quantity) {
            this.quantity = quantity;
            return this;
        }

        public Builder price(Double price) {
            this.price = price;
            return this;
        }

        public Builder subtotal(Double subtotal) {
            this.subtotal = subtotal;
            return this;
        }

        public OrderItemDTO build() {
            OrderItemDTO dto = new OrderItemDTO();
            dto.id = this.id;
            dto.productId = this.productId;
            dto.productName = this.productName;
            dto.quantity = this.quantity;
            dto.price = this.price;
            dto.subtotal = this.subtotal;
            return dto;
        }
    }
}
