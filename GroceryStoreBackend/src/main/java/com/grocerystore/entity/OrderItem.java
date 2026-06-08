package com.grocerystore.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "order_items")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItem {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;
    
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;
    
    @Column(nullable = false)
    private Integer quantity;
    
    @Column(nullable = false)
    private Double price;
    
    @Column(nullable = false)
    private Double subtotal;
    
    @PrePersist
    protected void onCreate() {
        subtotal = price * quantity;
    }

    // Manual getters since Lombok annotation processing is not working with Java 25
    public Long getId() {
        return id;
    }

    public Order getOrder() {
        return order;
    }

    public Product getProduct() {
        return product;
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
        private Order order;
        private Product product;
        private Integer quantity;
        private Double price;
        private Double subtotal;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder order(Order order) {
            this.order = order;
            return this;
        }

        public Builder product(Product product) {
            this.product = product;
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

        public OrderItem build() {
            OrderItem item = new OrderItem();
            item.id = this.id;
            item.order = this.order;
            item.product = this.product;
            item.quantity = this.quantity;
            item.price = this.price;
            item.subtotal = this.subtotal;
            return item;
        }
    }
}
