package com.grocerystore.service;

import com.grocerystore.entity.Order;
import com.grocerystore.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class WhatsAppService {

    private static final Logger log = LoggerFactory.getLogger(WhatsAppService.class);

    @Value("${whatsapp.phone.number:9994411370}")
    private String storePhoneNumber;

    public void sendOrderConfirmation(User user, Order order) {
        try {
            String message = buildOrderConfirmationMessage(user, order);
            sendMessage(user.getPhone(), message);
            log.info("Order confirmation prepared for: {}", user.getPhone());
        } catch (Exception e) {
            log.error("Error preparing WhatsApp order confirmation: {}", e.getMessage());
        }
    }

    public void sendMessage(String phoneNumber, String message) {
        String formattedPhone = formatPhoneNumber(phoneNumber);
        log.info("WhatsApp message to {}: {}", formattedPhone, message);
    }

    private String buildOrderConfirmationMessage(User user, Order order) {
        StringBuilder message = new StringBuilder();
        message.append("*Order Confirmation - Shri Dev BalaKrishna Store*\n\n");
        message.append("Hi ").append(user.getName()).append("!\n\n");
        message.append("Your order has been placed successfully.\n\n");
        message.append("*Order Details:*\n");
        message.append("Order ID: #").append(order.getId()).append("\n");
        message.append("Total Amount: Rs. ").append(order.getTotalAmount()).append("\n");
        message.append("Items: ").append(order.getItems() == null ? 0 : order.getItems().size()).append("\n\n");
        message.append("*Delivery Address:*\n");
        message.append(order.getDeliveryAddress()).append("\n");
        message.append(order.getDeliveryCity()).append("\n\n");
        message.append("Estimated Delivery: 2-3 hours\n\n");
        message.append("For any queries, call us at: +91 ").append(storePhoneNumber).append("\n");
        message.append("Thank you for shopping with us!");

        return message.toString();
    }

    private String formatPhoneNumber(String phoneNumber) {
        String digitsOnly = phoneNumber.replaceAll("[^0-9]", "");

        if (digitsOnly.length() == 10) {
            return "91" + digitsOnly;
        }
        if (digitsOnly.startsWith("0")) {
            return "91" + digitsOnly.substring(1);
        }
        return digitsOnly;
    }

    public void sendCustomMessage(String phoneNumber, String message) {
        sendMessage(phoneNumber, message);
    }
}
