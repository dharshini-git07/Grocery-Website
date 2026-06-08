package com.grocerystore.service;

import com.grocerystore.dto.ProductDTO;
import com.grocerystore.entity.Product;
import com.grocerystore.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {
    
    @Autowired
    private ProductRepository productRepository;
    
    public List<ProductDTO> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    public ProductDTO getProductById(Long id) {
        return productRepository.findById(id)
                .map(this::convertToDTO)
                .orElse(null);
    }
    
    public List<ProductDTO> getProductsByCategory(String category) {
        return productRepository.findByCategory(category)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    public List<ProductDTO> searchProducts(String searchTerm) {
        List<Product> results = productRepository.findByNameContainingIgnoreCase(searchTerm);
        if (results.isEmpty()) {
            results = productRepository.findByTamilNameContainingIgnoreCase(searchTerm);
        }
        return results.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    public ProductDTO createProduct(ProductDTO productDTO) {
        Product product = Product.builder()
                .name(productDTO.getName())
                .tamilName(productDTO.getTamilName())
                .category(productDTO.getCategory())
                .price(productDTO.getPrice())
                .unit(productDTO.getUnit())
                .description(productDTO.getDescription())
                .tamilDescription(productDTO.getTamilDescription())
                .image(productDTO.getImage())
                .stock(productDTO.getStock() != null ? productDTO.getStock() : 100)
                .build();
        Product saved = productRepository.save(product);
        return convertToDTO(saved);
    }
    
    public ProductDTO updateProduct(Long id, ProductDTO productDTO) {
        return productRepository.findById(id)
                .map(product -> {
                    product.setName(productDTO.getName());
                    product.setTamilName(productDTO.getTamilName());
                    product.setCategory(productDTO.getCategory());
                    product.setPrice(productDTO.getPrice());
                    product.setUnit(productDTO.getUnit());
                    product.setDescription(productDTO.getDescription());
                    product.setTamilDescription(productDTO.getTamilDescription());
                    product.setImage(productDTO.getImage());
                    product.setStock(productDTO.getStock());
                    Product updated = productRepository.save(product);
                    return convertToDTO(updated);
                })
                .orElse(null);
    }
    
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
    
    private ProductDTO convertToDTO(Product product) {
        return ProductDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .tamilName(product.getTamilName())
                .category(product.getCategory())
                .price(product.getPrice())
                .unit(product.getUnit())
                .description(product.getDescription())
                .tamilDescription(product.getTamilDescription())
                .image(product.getImage())
                .stock(product.getStock())
                .build();
    }
}
