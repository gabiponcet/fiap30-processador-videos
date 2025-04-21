package com.fiap.tech.infra.configuration;

import org.springframework.amqp.rabbit.core.RabbitOperations;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fiap.tech.infra.configuration.annotations.ConversorReponseQueue;
import com.fiap.tech.infra.configuration.properties.amqp.QueueProperties;
import com.fiap.tech.infra.services.EventService;
import com.fiap.tech.infra.services.impl.ConversorEventServiceImpl;

@Configuration
public class EventServiceConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public EventService conversorEventService(
        @ConversorReponseQueue final QueueProperties props,
        final RabbitOperations ops
    ) {
        return new ConversorEventServiceImpl(props.getExchange(), props.getRoutingKey(), ops);
    }
}