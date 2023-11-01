package com.rabbitmq.consumer;

import com.rabbitmq.config.RabbitMessageConfig;
import com.rabbitmq.dto.UserDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RabbitUserConsumer {

    @RabbitListener(queues = RabbitMessageConfig.USER_QUEUE)
    public void consumerMessage(UserDto userDto){
      log.info("message : {}", userDto.toString());
    }

}
