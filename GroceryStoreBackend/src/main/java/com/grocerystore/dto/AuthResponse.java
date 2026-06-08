package com.grocerystore.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthResponse {
    private String token;
    private UserDTO user;
    private String message;

    public String getToken() {
        return token;
    }

    public UserDTO getUser() {
        return user;
    }

    public String getMessage() {
        return message;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String token;
        private UserDTO user;
        private String message;

        public Builder token(String token) {
            this.token = token;
            return this;
        }

        public Builder user(UserDTO user) {
            this.user = user;
            return this;
        }

        public Builder message(String message) {
            this.message = message;
            return this;
        }

        public AuthResponse build() {
            AuthResponse response = new AuthResponse();
            response.token = this.token;
            response.user = this.user;
            response.message = this.message;
            return response;
        }
    }
}
