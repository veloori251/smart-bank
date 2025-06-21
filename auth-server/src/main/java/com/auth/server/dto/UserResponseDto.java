package com.auth.server.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponseDto {
    private String username;
    private String password;
    private String role;
    private String status="ACTIVE";
}
