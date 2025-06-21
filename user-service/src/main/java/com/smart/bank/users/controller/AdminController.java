package com.smart.bank.users.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/v1/admin")
public interface AdminController {

    @GetMapping("/test")
    String testAdminAccess();
}
