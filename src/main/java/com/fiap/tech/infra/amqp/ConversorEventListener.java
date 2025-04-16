package com.fiap.tech.infra.amqp;

import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import com.fiap.tech.application.conversor.ConversorVideoCommand;
import com.fiap.tech.application.conversor.ConversorVideoUseCase;
import com.fiap.tech.infra.conversor.model.RabbitConversorRequest;
import com.fiap.tech.infra.util.json.Json;

@Service
public class ConversorEventListener {

    private static final Logger log = LoggerFactory.getLogger(ConversorEventListener.class);

    static final String LISTENER_ID = "ConversorEventListener";

    private final ConversorVideoUseCase conversorVideoUseCase;

    public ConversorEventListener(final ConversorVideoUseCase conversorVideoUseCase) {
        this.conversorVideoUseCase = Objects.requireNonNull(conversorVideoUseCase);
    }

    @RabbitListener(
        id = LISTENER_ID,
        queues = "${amqp.queues.conversor-request.queue}"
    )
    public void consumeConversorEvent(String message) {
        if (message == null || message.trim().isEmpty()) {
            log.error("[message:conversor.listener.income] [status:error] [reason:empty payload]");
            return;
        }

        try {
            RabbitConversorRequest dto = Json.readValue(message, RabbitConversorRequest.class);

            if (dto == null) {
                log.error("[message:conversor.listener.income] [status:error] [reason:payload invalido.]");
                return; 
            }

            log.info("[message:conversor.listener.income] [status:completed] [payload:{}]", message);

            final var command = 
                ConversorVideoCommand.with(
                    dto.resourceId(),
                    dto.id(),
                    dto.clientId(), 
                    dto.filePath());
            
            this.conversorVideoUseCase.execute(command);        
            
        } catch (Exception e) {
            log.error("[message:conversor.listener.income] [status:error] [reason:exception] [error:{}]", e.getMessage());
        }
    }
}
