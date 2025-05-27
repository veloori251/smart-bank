package com.smart.bank.users.service;

import com.smart.bank.users.entity.User;

public interface ProducerService {
    void publishUserCreated(User user);
    void publishUserUpdated(User user);
    void publishUserDeleted(String userName);
}
