package com.rabbitmq.controller;

import com.rabbitmq.dto.UserDto;
import com.rabbitmq.producer.RabbitUserProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/rabbit/user")
public class RabbitUserController {

    @Autowired
    private RabbitUserProducer rabbitUserProducer;

    @PostMapping("/")
    public String publishMessage(@RequestBody UserDto userDto){
        rabbitUserProducer.publishMessage(userDto);
        return "message published to queue";
    }

}
