package com.fiap.tech.conversor.infra.service.impl.event;

import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitOperations;

import com.fiap.tech.conversor.infra.configuration.json.Json;
import com.fiap.tech.conversor.infra.service.EventService;

public class ConversorEventService implements EventService {

    private static final Logger log = LoggerFactory.getLogger(ConversorEventService.class);

    private final String exchange;
    private final String routingKey;
    private final RabbitOperations ops;

    public ConversorEventService(final String exchange, final String routingKey, final RabbitOperations ops) {
        this.exchange = Objects.requireNonNull(exchange, "Exchange não pode ser nulo");
        this.routingKey = Objects.requireNonNull(routingKey, "RoutingKey não pode ser nulo");
        this.ops = Objects.requireNonNull(ops, "RabbitOperations não pode ser nulo");
    }

    @Override
    public void send(final Object event) {
        try {
            String message = Json.writeValueAsString(event);
            log.info("Enviando evento para o RabbitMQ: exchange='{}', routingKey='{}', message='{}'",
                exchange, routingKey, message);
            
            ops.convertAndSend(this.exchange, this.routingKey, message);
            
            log.info("Evento enviado com sucesso!");
        } catch (Exception e) {
            log.error("Erro ao enviar evento para RabbitMQ: exchange='{}', routingKey='{}', evento='{}'",
                exchange, routingKey, event, e);
        }
    }
}
