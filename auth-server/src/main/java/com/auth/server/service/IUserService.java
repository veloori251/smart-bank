package com.auth.server.service;

import com.auth.server.dto.LoginRequest;
import com.auth.server.dto.RegisterRequest;
import com.auth.server.entity.User;

public interface IUserService {

    User register(RegisterRequest registerRequest);
    User validateUser(LoginRequest loginRequest);
}
