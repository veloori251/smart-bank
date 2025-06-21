package com.smart.bank.users.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponseDTO {

    @JsonIgnore
    private Long id;

    private String fullName;

    private String email;

    private String phone;

    private String address;

    private String country;

    private String username;
}
