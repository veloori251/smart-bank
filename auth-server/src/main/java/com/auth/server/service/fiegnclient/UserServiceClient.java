package com.auth.server.service.fiegnclient;

import com.auth.server.dto.UserResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service",url = "http://localhost:8081/api/v1/users")
public interface UserServiceClient {
    @GetMapping("/username/{username}")
    UserResponseDto getUserByUsername(@PathVariable(name = "username") String username);
}
