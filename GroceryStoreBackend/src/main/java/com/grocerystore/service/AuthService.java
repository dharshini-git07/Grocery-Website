package com.grocerystore.service;

import com.grocerystore.dto.AuthRequest;
import com.grocerystore.dto.AuthResponse;
import com.grocerystore.dto.UserDTO;
import com.grocerystore.entity.User;
import com.grocerystore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private JwtService jwtService;
    
    public AuthResponse signup(AuthRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            return AuthResponse.builder()
                    .message("Email already registered")
                    .build();
        }
        
        User user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .name(request.getName())
                .phone(request.getPhone())
                .build();
        
        User savedUser = userRepository.save(user);
        String token = jwtService.generateToken(savedUser.getEmail());
        
        return AuthResponse.builder()
                .token(token)
                .user(convertToDTO(savedUser))
                .message("Signup successful")
                .build();
    }
    
    public AuthResponse login(AuthRequest request) {
        return userRepository.findByEmail(request.getEmail())
                .map(user -> {
                    if (passwordEncoder.matches(request.getPassword(), user.getPassword())) {
                        String token = jwtService.generateToken(user.getEmail());
                        return AuthResponse.builder()
                                .token(token)
                                .user(convertToDTO(user))
                                .message("Login successful")
                                .build();
                    }
                    return AuthResponse.builder()
                            .message("Invalid credentials")
                            .build();
                })
                .orElse(AuthResponse.builder()
                        .message("User not found")
                        .build());
    }
    
    private UserDTO convertToDTO(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .phone(user.getPhone())
                .address(user.getAddress())
                .city(user.getCity())
                .postalCode(user.getPostalCode())
                .build();
    }
}
