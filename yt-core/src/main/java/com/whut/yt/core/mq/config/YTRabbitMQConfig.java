package com.whut.yt.core.mq.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: 一大岐
 * @Date: 2021/4/18 11:54
 * @Description: 创建交换机、队列、绑定关系
 */
@Configuration
@Slf4j
public class YTRabbitMQConfig {

    @Bean
    public MessageConverter messageConverter(){
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public Exchange yTransactionEventExchange(){
        return new TopicExchange("ytransaction-event-exchange",true,false);
    }

    @Bean
    public Queue yTransactionQueue(){
        return new Queue("ytransaction.message.queue",true,false,false);
    }

    @Bean
    public Binding stockReleaseBinding(){
        return new Binding("ytransaction.message.queue",
                Binding.DestinationType.QUEUE,
                "ytransaction-event-exchange",
                "ytransaction.notice",
                null);
    }

}
