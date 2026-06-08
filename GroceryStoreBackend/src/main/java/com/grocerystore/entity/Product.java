package com.grocerystore.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String name;
    
    @Column(nullable = false)
    private String tamilName;
    
    @Column(nullable = false)
    private String category;
    
    @Column(nullable = false)
    private Double price;
    
    @Column(nullable = false)
    private String unit;
    
    @Column(columnDefinition = "TEXT")
    private String description;
    
    @Column(columnDefinition = "TEXT")
    private String tamilDescription;
    
    @Column(nullable = false)
    private String image;
    
    @Column(nullable = false)
    private Integer stock = 100;
    
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

    public String getName() {
        return name;
    }

    public String getTamilName() {
        return tamilName;
    }

    public String getCategory() {
        return category;
    }

    public Double getPrice() {
        return price;
    }

    public String getUnit() {
        return unit;
    }

    public String getDescription() {
        return description;
    }

    public String getTamilDescription() {
        return tamilDescription;
    }

    public String getImage() {
        return image;
    }

    public Integer getStock() {
        return stock;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public Long getUpdatedAt() {
        return updatedAt;
    }

    // Manual builder since Lombok annotation processing is not working with Java 25
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long id;
        private String name;
        private String tamilName;
        private String category;
        private Double price;
        private String unit;
        private String description;
        private String tamilDescription;
        private String image;
        private Integer stock;
        private Long createdAt;
        private Long updatedAt;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder tamilName(String tamilName) {
            this.tamilName = tamilName;
            return this;
        }

        public Builder category(String category) {
            this.category = category;
            return this;
        }

        public Builder price(Double price) {
            this.price = price;
            return this;
        }

        public Builder unit(String unit) {
            this.unit = unit;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder tamilDescription(String tamilDescription) {
            this.tamilDescription = tamilDescription;
            return this;
        }

        public Builder image(String image) {
            this.image = image;
            return this;
        }

        public Builder stock(Integer stock) {
            this.stock = stock;
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

        public Product build() {
            Product product = new Product();
            product.id = this.id;
            product.name = this.name;
            product.tamilName = this.tamilName;
            product.category = this.category;
            product.price = this.price;
            product.unit = this.unit;
            product.description = this.description;
            product.tamilDescription = this.tamilDescription;
            product.image = this.image;
            product.stock = this.stock;
            product.createdAt = this.createdAt;
            product.updatedAt = this.updatedAt;
            return product;
        }
    }

    // Manual setters since Lombok annotation processing is not working with Java 25
    public void setName(String name) {
        this.name = name;
    }

    public void setTamilName(String tamilName) {
        this.tamilName = tamilName;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTamilDescription(String tamilDescription) {
        this.tamilDescription = tamilDescription;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(Long updatedAt) {
        this.updatedAt = updatedAt;
    }
}
