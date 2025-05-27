package com.smart.bank.users.service.impl;


import com.smart.bank.users.entity.User;
import com.smart.bank.users.service.ProducerService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProducerServiceImpl implements ProducerService {

    private final KafkaTemplate<String,Object> kafkaTemplate;

    @Override
    public void publishUserCreated(User user) {
        kafkaTemplate.send("user.created",user);
    }

    @Override
    public void publishUserUpdated(User user) {
        kafkaTemplate.send("user.updated",user);
    }

    @Override
    public void publishUserDeleted(String userName) {
        kafkaTemplate.send("user.deleted",userName);
    }
}
