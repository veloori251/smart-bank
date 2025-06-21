package com.api.gateway.model;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public enum ServiceErrorType {
    USER_SERVICE_DOWN(503,"Service Unavailable","User service is currently unavialble."),
    AUTH_SERVICE_DOWN(503,"Service Unavailable","Auth service is currently unavialble.");

    private int status;
    private String error;
    private String message;



}
