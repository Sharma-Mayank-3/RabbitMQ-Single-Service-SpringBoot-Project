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
