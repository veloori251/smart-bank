package com.smart.bank.users.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ErrorResponseDTO {

    private String error;
    private String message;
    private int staus;
    private LocalDateTime timestamp;
    private String path;
}
