# RabbitMQ-Single-Service-SpringBoot-Project

# NOTE : 
```properties
Here I am going to create a single project to demo the rabbit-MQ example.
```

# Steps for Rabbit MQ
1. After downloading the rabbit-mq start the rabbit mq and go to the link.
```properties
brew services start rabbitmq
http://localhost:15672/#/
userName = guest
password = guest
```

2. Download Dependencies
```xml
Spring for rabbit-mq

<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-amqp</artifactId>
</dependency>
```

3. Create a RabbitMessageConfig class in config
```java
package com.rabbitmq.config;

import com.rabbitmq.client.AMQP;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMessageConfig {

    public static final String USER_EXCHANGE = "user_exchange";
    public static final String USER_QUEUE = "user_queue";
    public static final String USER_ROUTING = "user_routing";

    // create a queue
    @Bean
    public Queue queue(){
        return new Queue(USER_QUEUE);
    }

    // create a topic
    @Bean
    public TopicExchange exchange(){
        return new TopicExchange(USER_EXCHANGE);
    }

    // create a binding between queue and exchange.
    @Bean
    public Binding binding(Queue queue, TopicExchange exchange){
        return BindingBuilder.bind(queue).to(exchange).with(USER_ROUTING);
    }

    // this is needed since we are going to use object to pass between producer and consumer.
    @Bean
    public MessageConverter messageConverter(){
        return new Jackson2JsonMessageConverter();
    }

    // will this template we can publish the message to the queue.
    @Bean
    public AmqpTemplate template(ConnectionFactory connectionFactory){
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter());
        return rabbitTemplate;
    }

}

```

4. Create a producer
```java
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
```

5. publish a message using controller
```properties
we can see that a message is published in queue ui
go to this link : http://localhost:15672/#/
go to queue and get message.
```

6. create a consumer 
```java
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
```

# NOTE :
```properties
After the consumer a message has been removed form the quere 
so this is a basic difference between queue and kafka.
in kafka after cosnumer also a message remains but not in queue
```

# Note :
```properties
Even a new cosnumer service can be created with different package and I have seen 
no issue in message consuming.
```
