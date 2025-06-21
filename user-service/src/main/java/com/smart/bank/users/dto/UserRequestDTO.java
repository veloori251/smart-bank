package com.smart.bank.users.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRequestDTO {

    @NotBlank(message = "Full name is required")
    private String fullName;

    @Email(message = "Invalid email address")
    private String email;

    @Pattern(regexp = "[0-9]{10}",message = "Phone number must be 10 digits")
    private String phone;

    @NotBlank(message = "Country is required")
    private String country;

    private String address;
    @NotBlank(message = "Password is required")
    private String password;

    @NotBlank(message = "Confirm password is required")
    private String confirmPassword;

    @NotBlank(message = "Username is required")
    private String username;

}
