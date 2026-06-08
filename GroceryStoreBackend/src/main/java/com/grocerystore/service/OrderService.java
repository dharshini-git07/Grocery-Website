package com.grocerystore.service;

import com.grocerystore.dto.OrderDTO;
import com.grocerystore.dto.OrderItemDTO;
import com.grocerystore.entity.Order;
import com.grocerystore.entity.OrderItem;
import com.grocerystore.entity.Product;
import com.grocerystore.entity.User;
import com.grocerystore.repository.OrderRepository;
import com.grocerystore.repository.ProductRepository;
import com.grocerystore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {
    
    @Autowired
    private OrderRepository orderRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private ProductRepository productRepository;
    
    @Autowired
    private WhatsAppService whatsAppService;
    
    public OrderDTO createOrder(Long userId, OrderDTO orderDTO) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        Order order = Order.builder()
                .user(user)
                .totalAmount(orderDTO.getTotalAmount())
                .status("PENDING")
                .deliveryAddress(orderDTO.getDeliveryAddress())
                .deliveryCity(orderDTO.getDeliveryCity())
                .deliveryPostalCode(orderDTO.getDeliveryPostalCode())
                .notes(orderDTO.getNotes())
                .build();
        
        Order savedOrder = orderRepository.save(order);
        
        // Add order items
        if (orderDTO.getItems() != null) {
            final Order finalSavedOrder = savedOrder;
            List<OrderItem> items = orderDTO.getItems().stream()
                    .map(itemDTO -> {
                        Product product = productRepository.findById(itemDTO.getProductId())
                                .orElseThrow(() -> new RuntimeException("Product not found"));
                        return OrderItem.builder()
                                .order(finalSavedOrder)
                                .product(product)
                                .quantity(itemDTO.getQuantity())
                                .price(itemDTO.getPrice())
                                .subtotal(itemDTO.getPrice() * itemDTO.getQuantity())
                                .build();
                    })
                    .collect(Collectors.toList());
            savedOrder.setItems(items);
            savedOrder = orderRepository.save(savedOrder);
        }
        
        // Send WhatsApp notification
        whatsAppService.sendOrderConfirmation(user, savedOrder);
        
        return convertToDTO(savedOrder);
    }
    
    public List<OrderDTO> getUserOrders(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return orderRepository.findByUser(user)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    public OrderDTO getOrderById(Long orderId) {
        return orderRepository.findById(orderId)
                .map(this::convertToDTO)
                .orElse(null);
    }
    
    public OrderDTO updateOrderStatus(Long orderId, String status) {
        return orderRepository.findById(orderId)
                .map(order -> {
                    order.setStatus(status);
                    Order updated = orderRepository.save(order);
                    return convertToDTO(updated);
                })
                .orElse(null);
    }
    
    private OrderDTO convertToDTO(Order order) {
        List<OrderItemDTO> items = order.getItems().stream()
                .map(item -> OrderItemDTO.builder()
                        .id(item.getId())
                        .productId(item.getProduct().getId())
                        .productName(item.getProduct().getName())
                        .quantity(item.getQuantity())
                        .price(item.getPrice())
                        .subtotal(item.getSubtotal())
                        .build())
                .collect(Collectors.toList());
        
        return OrderDTO.builder()
                .id(order.getId())
                .userId(order.getUser().getId())
                .items(items)
                .totalAmount(order.getTotalAmount())
                .status(order.getStatus())
                .deliveryAddress(order.getDeliveryAddress())
                .deliveryCity(order.getDeliveryCity())
                .deliveryPostalCode(order.getDeliveryPostalCode())
                .notes(order.getNotes())
                .createdAt(order.getCreatedAt())
                .build();
    }
}
