package com.api.gateway.dto;

import com.api.gateway.model.ServiceErrorType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ApiErrorResponse {

    private int status;
    private String error;
    private String message;
    private boolean success;

    public ApiErrorResponse(ServiceErrorType serviceErrorType) {
        this.status = serviceErrorType.getStatus();
        this.error = serviceErrorType.getError();
        this.message = serviceErrorType.getMessage();
        this.success = false;
    }
}
