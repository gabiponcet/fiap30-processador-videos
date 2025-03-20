package com.fiap.tech.conversor.infra.configuration.service;

import org.springframework.amqp.rabbit.core.RabbitOperations;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fiap.tech.conversor.infra.annotations.ConversorReponseQueue;
import com.fiap.tech.conversor.infra.configuration.properties.amqp.QueueProperties;
import com.fiap.tech.conversor.infra.service.EventService;
import com.fiap.tech.conversor.infra.service.impl.event.ConversorEventService;

@Configuration
public class EventServiceConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public EventService conversorEventService(
        @ConversorReponseQueue final QueueProperties props,
        final RabbitOperations ops
    ) {
        return new ConversorEventService(props.getExchange(), props.getRoutingKey(), ops);
    }
}
