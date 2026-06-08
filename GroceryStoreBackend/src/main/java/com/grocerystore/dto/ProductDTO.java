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
public class ProductDTO {
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

        public ProductDTO build() {
            ProductDTO dto = new ProductDTO();
            dto.id = this.id;
            dto.name = this.name;
            dto.tamilName = this.tamilName;
            dto.category = this.category;
            dto.price = this.price;
            dto.unit = this.unit;
            dto.description = this.description;
            dto.tamilDescription = this.tamilDescription;
            dto.image = this.image;
            dto.stock = this.stock;
            return dto;
        }
    }
}
