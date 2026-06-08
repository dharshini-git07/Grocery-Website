package com.grocerystore.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<OrderItem> items;
    
    @Column(nullable = false)
    private Double totalAmount;
    
    @Column(nullable = false)
    private String status = "PENDING"; // PENDING, CONFIRMED, DISPATCHED, DELIVERED
    
    @Column(nullable = false)
    private String deliveryAddress;
    
    @Column(nullable = false)
    private String deliveryCity;
    
    @Column(nullable = true)
    private String deliveryPostalCode;
    
    @Column(nullable = true)
    private String notes;
    
    @Column(nullable = true)
    private String whatsappMessageId;
    
    @Column(name = "created_at")
    private Long createdAt;
    
    @Column(name = "updated_at")
    private Long updatedAt;
    
    @PrePersist
    protected void onCreate() {
        createdAt = System.currentTimeMillis();
        updatedAt = System.currentTimeMillis();
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = System.currentTimeMillis();
    }

    // Manual getters since Lombok annotation processing is not working with Java 25
    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public String getStatus() {
        return status;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public String getDeliveryCity() {
        return deliveryCity;
    }

    public String getDeliveryPostalCode() {
        return deliveryPostalCode;
    }

    public String getNotes() {
        return notes;
    }

    public String getWhatsappMessageId() {
        return whatsappMessageId;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public Long getUpdatedAt() {
        return updatedAt;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long id;
        private User user;
        private List<OrderItem> items;
        private Double totalAmount;
        private String status;
        private String deliveryAddress;
        private String deliveryCity;
        private String deliveryPostalCode;
        private String notes;
        private String whatsappMessageId;
        private Long createdAt;
        private Long updatedAt;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder user(User user) {
            this.user = user;
            return this;
        }

        public Builder items(List<OrderItem> items) {
            this.items = items;
            return this;
        }

        public Builder totalAmount(Double totalAmount) {
            this.totalAmount = totalAmount;
            return this;
        }

        public Builder status(String status) {
            this.status = status;
            return this;
        }

        public Builder deliveryAddress(String deliveryAddress) {
            this.deliveryAddress = deliveryAddress;
            return this;
        }

        public Builder deliveryCity(String deliveryCity) {
            this.deliveryCity = deliveryCity;
            return this;
        }

        public Builder deliveryPostalCode(String deliveryPostalCode) {
            this.deliveryPostalCode = deliveryPostalCode;
            return this;
        }

        public Builder notes(String notes) {
            this.notes = notes;
            return this;
        }

        public Builder whatsappMessageId(String whatsappMessageId) {
            this.whatsappMessageId = whatsappMessageId;
            return this;
        }

        public Builder createdAt(Long createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Builder updatedAt(Long updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        public Order build() {
            Order order = new Order();
            order.id = this.id;
            order.user = this.user;
            order.items = this.items;
            order.totalAmount = this.totalAmount;
            order.status = this.status != null ? this.status : "PENDING";
            order.deliveryAddress = this.deliveryAddress;
            order.deliveryCity = this.deliveryCity;
            order.deliveryPostalCode = this.deliveryPostalCode;
            order.notes = this.notes;
            order.whatsappMessageId = this.whatsappMessageId;
            order.createdAt = this.createdAt;
            order.updatedAt = this.updatedAt;
            return order;
        }
    }
}
