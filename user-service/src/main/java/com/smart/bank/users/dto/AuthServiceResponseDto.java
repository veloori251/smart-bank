package com.smart.bank.users.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthServiceResponseDto {
    private String username;
    private String password;
    private String role;
    private String status;
}
