package com.grocerystore.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDTO {
    private Long id;
    private Long userId;
    private List<OrderItemDTO> items;
    private Double totalAmount;
    private String status;
    private String deliveryAddress;
    private String deliveryCity;
    private String deliveryPostalCode;
    private String notes;
    private Long createdAt;

    // Manual getters since Lombok annotation processing is not working with Java 25
    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public List<OrderItemDTO> getItems() {
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

    public Long getCreatedAt() {
        return createdAt;
    }

    // Manual builder since Lombok annotation processing is not working with Java 25
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long id;
        private Long userId;
        private List<OrderItemDTO> items;
        private Double totalAmount;
        private String status;
        private String deliveryAddress;
        private String deliveryCity;
        private String deliveryPostalCode;
        private String notes;
        private Long createdAt;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder userId(Long userId) {
            this.userId = userId;
            return this;
        }

        public Builder items(List<OrderItemDTO> items) {
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

        public Builder createdAt(Long createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public OrderDTO build() {
            OrderDTO dto = new OrderDTO();
            dto.id = this.id;
            dto.userId = this.userId;
            dto.items = this.items;
            dto.totalAmount = this.totalAmount;
            dto.status = this.status;
            dto.deliveryAddress = this.deliveryAddress;
            dto.deliveryCity = this.deliveryCity;
            dto.deliveryPostalCode = this.deliveryPostalCode;
            dto.notes = this.notes;
            dto.createdAt = this.createdAt;
            return dto;
        }
    }
}
