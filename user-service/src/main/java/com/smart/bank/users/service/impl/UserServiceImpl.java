package com.smart.bank.users.service.impl;

import com.smart.bank.users.dto.UserRequestDTO;
import com.smart.bank.users.dto.UserResponseDTO;
import com.smart.bank.users.entity.User;
import com.smart.bank.users.exception.ResourceNotFoundException;
import com.smart.bank.users.mapper.UserMapper;
import com.smart.bank.users.repository.UserRepository;
import com.smart.bank.users.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper mapper;

    @Override
    public UserResponseDTO createUser(UserRequestDTO userRequestDTO) {
        log.info("Creating user with email: {}",userRequestDTO.getEmail());
        User user = mapper.toEntity(userRequestDTO);
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
}
