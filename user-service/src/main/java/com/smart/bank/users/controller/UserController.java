package com.smart.bank.users.controller;

import com.smart.bank.users.dto.AuthServiceResponseDto;
import com.smart.bank.users.dto.UserRequestDTO;
import com.smart.bank.users.dto.UserResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/v1/users")
public interface UserController {

    @PostMapping("/register")
    ResponseEntity<UserResponseDTO> createUser(@RequestBody UserRequestDTO userRequestDTO);

    @GetMapping("/{id}")
    ResponseEntity<UserResponseDTO> getUserById(@PathVariable(name = "id") Long id);

    @GetMapping
    ResponseEntity<List<UserResponseDTO>> getAllUsers();

    @PutMapping("/{id}")
    ResponseEntity<UserResponseDTO> updateUser(@PathVariable(name = "id") Long id, @RequestBody UserRequestDTO userRequestDTO);

    @DeleteMapping("/{id}")
    ResponseEntity<String> deleteUser(@PathVariable(name = "id") Long id);

    @GetMapping("/username/{username}")
    ResponseEntity<AuthServiceResponseDto> getUserByUsername(@PathVariable(name = "username") String username);
}
