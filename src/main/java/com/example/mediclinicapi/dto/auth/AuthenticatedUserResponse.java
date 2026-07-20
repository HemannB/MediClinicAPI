package com.example.mediclinicapi.dto.auth;

import com.example.mediclinicapi.domain.User;
import com.example.mediclinicapi.domain.enums.Role;

public record AuthenticatedUserResponse(
        Long id,
        String email,
        Role role
) {
    public static AuthenticatedUserResponse from(User user) {
        return new AuthenticatedUserResponse(
                user.getId(),
                user.getEmail(),
                user.getRole()
        );
    }
}
