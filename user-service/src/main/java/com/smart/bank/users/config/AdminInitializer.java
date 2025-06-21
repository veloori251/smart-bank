package com.smart.bank.users.config;

import com.smart.bank.users.entity.User;
import com.smart.bank.users.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class AdminInitializer {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${app.admin.username}")
    private String adminUsername;

    @Value("${app.admin.password}")
    private String adminPassword;

    @Value("${app.admin.email}")
    private String adminEmail;

    @PostConstruct
    public void init() {
        createAdminIfNotExists();
    }

    private void createAdminIfNotExists() {
        if(!userRepository.existsByEmail(adminUsername)){
            log.info("Creating admin user: {}", adminUsername);
            User admin = User.builder()
                    .fullName("Admin User")
                    .username(adminUsername)
                    .password(passwordEncoder.encode(adminPassword))
                    .email(adminEmail)
                    .role("ADMIN")
                    .status("ACTIVE")
                    .build();
            userRepository.save(admin);
        }else{
            log.info("Admin user already exists: {}", adminUsername);
        }
    }

}
