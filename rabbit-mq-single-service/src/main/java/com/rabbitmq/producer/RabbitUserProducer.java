package com.rabbitmq.producer;

import com.rabbitmq.config.RabbitMessageConfig;
import com.rabbitmq.dto.UserDto;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RabbitUserProducer {

    @Autowired
    public RabbitTemplate rabbitTemplate;

    @Autowired
    public RabbitMessageConfig rabbitMessageConfig;

    public void publishMessage(UserDto userDto){
        rabbitTemplate.convertAndSend(RabbitMessageConfig.USER_EXCHANGE, RabbitMessageConfig.USER_ROUTING, userDto);
    }

}
