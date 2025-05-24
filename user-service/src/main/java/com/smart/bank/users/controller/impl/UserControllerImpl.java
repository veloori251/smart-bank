package com.smart.bank.users.controller.impl;

import com.smart.bank.users.controller.UserController;
import com.smart.bank.users.dto.UserRequestDTO;
import com.smart.bank.users.dto.UserResponseDTO;
import com.smart.bank.users.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@Slf4j
public class UserControllerImpl implements UserController {

    private final UserService userService;

    @Override
    public ResponseEntity<UserResponseDTO> createUser(UserRequestDTO userRequestDTO,String origin) {
        log.info("Received request from: {}", origin);
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(userRequestDTO));
    }

    @Override
    public ResponseEntity<UserResponseDTO> getUserById(Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @Override
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @Override
    public ResponseEntity<UserResponseDTO> updateUser(Long id, UserRequestDTO userRequestDTO) {
        return ResponseEntity.ok(userService.updateUser(id, userRequestDTO));
    }

    @Override
    public ResponseEntity<String> deleteUser(Long id) {
        return ResponseEntity.ok(userService.deleteUser(id));
    }
}
