package com.smart.bank.users.controller.impl;

import com.smart.bank.users.controller.AdminController;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;

@RequiredArgsConstructor
public class AdminControllerImpl implements AdminController {


    @Override
    @PreAuthorize("hasRole = ('ADMIN')")
    public String testAdminAccess() {
        return "Admin access granted";
    }
}
