package com.api.gateway.controller;

import com.api.gateway.dto.ApiErrorResponse;
import com.api.gateway.model.ServiceErrorType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/fallback")
public class FallbackController {

    @RequestMapping(value = "/user",method = {RequestMethod.GET,RequestMethod.POST,RequestMethod.DELETE,RequestMethod.PUT})
    public ResponseEntity<ApiErrorResponse> fallbackUser() {
        return ResponseEntity.status(ServiceErrorType.USER_SERVICE_DOWN.getStatus()).body(new ApiErrorResponse(ServiceErrorType.USER_SERVICE_DOWN));
    }

    @RequestMapping(value = "/auth",method = {RequestMethod.GET,RequestMethod.POST,RequestMethod.DELETE,RequestMethod.PUT})
    public ResponseEntity<ApiErrorResponse> fallbackAuthService() {
        return ResponseEntity.status(ServiceErrorType.AUTH_SERVICE_DOWN.getStatus()).body(new ApiErrorResponse(ServiceErrorType.AUTH_SERVICE_DOWN));
    }
}
