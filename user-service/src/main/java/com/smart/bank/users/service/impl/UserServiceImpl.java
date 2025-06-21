package com.smart.bank.users.service.impl;

import com.smart.bank.users.dto.AuthServiceResponseDto;
import com.smart.bank.users.dto.UserRequestDTO;
import com.smart.bank.users.dto.UserResponseDTO;
import com.smart.bank.users.entity.User;
import com.smart.bank.users.exception.PasswordMismatchException;
import com.smart.bank.users.exception.ResourceAlreadyExistsException;
import com.smart.bank.users.exception.ResourceNotFoundException;
import com.smart.bank.users.mapper.UserMapper;
import com.smart.bank.users.repository.UserRepository;
import com.smart.bank.users.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper mapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserResponseDTO createUser(UserRequestDTO userRequestDTO) {
        log.info("Creating user with email: {}",userRequestDTO.getEmail());
        userRegisterCheck(userRequestDTO);
        User user = mapper.toEntity(userRequestDTO);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setStatus("ACTIVE");
        user.setRole("USER");
        User savedUser = userRepository.save(user);
        return mapper.toResponseDTO(savedUser);
    }

    @Override
    public UserResponseDTO getUserById(Long id) {
        log.info("fetching user with id: {}",id);
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("user not found with id: "+id));

        return mapper.toResponseDTO(user);
    }

    @Override
    public List<UserResponseDTO> getAllUsers() {
        return userRepository.findAll().stream().map(mapper::toResponseDTO).toList();
    }

    @Override
    public UserResponseDTO updateUser(Long id, UserRequestDTO userRequestDTO) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("user not found with id: "+id));
        user.setFullName(userRequestDTO.getFullName());
        user.setEmail(userRequestDTO.getEmail());
        user.setPhone(userRequestDTO.getPhone());
        user.setAddress(userRequestDTO.getAddress());
        User updatedUser = userRepository.save(user);
        return mapper.toResponseDTO(updatedUser);
    }

    @Override
    public String deleteUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("user not found with id: "+id));
        userRepository.delete(user);
        return "User deleted successfully";
    }

    @Override
    public AuthServiceResponseDto getUserByUsername(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException("user not found with username: "+username));
        return AuthServiceResponseDto.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .role(user.getRole())
                .status(user.getStatus())
                .build();
    }

    private void userRegisterCheck(UserRequestDTO userRequestDTO){
        boolean existingUser = userRepository.existsByUsername(userRequestDTO.getUsername());
        if(existingUser){
            throw new ResourceAlreadyExistsException("User already exists with username: "+userRequestDTO.getUsername());
        }
        boolean existingEmail = userRepository.existsByEmail(userRequestDTO.getEmail());
        if(existingEmail){
            throw new ResourceAlreadyExistsException("User already exists with email: "+userRequestDTO.getEmail());
        }
        if(!userRequestDTO.getPassword().equals(userRequestDTO.getConfirmPassword())){
            throw new PasswordMismatchException("Password and confirm password do not match");
        }
    }
}
