package com.smart.bank.users.service.impl;

import com.smart.bank.users.entity.User;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class ConsumerService {

    @KafkaListener(topics = "user.created",groupId = "user-events-group")
    public void handleUserCreated(User user){
        System.out.println("📥 Received User Created Event: "+user);
    }

    @KafkaListener(topics = "user.updated",groupId = "user-events-group")
    public void handleUserUpdated(User user){
        System.out.println("📥 Received User Updated Event: "+user);
    }

    @KafkaListener(topics = "user.deleted",groupId = "user-events-group")
    public void handleUserDeleted(String username){
        System.out.println("📥 Received User Deleted Event: "+username);
    }
}
