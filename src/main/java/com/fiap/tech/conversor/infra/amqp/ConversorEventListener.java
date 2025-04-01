package com.fiap.tech.conversor.infra.amqp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import com.fiap.tech.conversor.application.processor.ConversorCommand;
import com.fiap.tech.conversor.application.processor.DefaultConversorVideoUseCase;
import com.fiap.tech.conversor.infra.configuration.json.Json;
import com.fiap.tech.conversor.infra.conversor.model.RabbitConversorResquest;

@Service
public class ConversorEventListener {

    private static final Logger log = LoggerFactory.getLogger(ConversorEventListener.class);

    static final String LISTENER_ID = "ConversorEventListener";

    private final DefaultConversorVideoUseCase defaultConversorVideoUseCase;

    public ConversorEventListener(DefaultConversorVideoUseCase defaultConversorVideoUseCase) {
        this.defaultConversorVideoUseCase = defaultConversorVideoUseCase;
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
            RabbitConversorResquest dto = Json.readValue(message, RabbitConversorResquest.class);

            if (dto == null) {
                log.error("[message:conversor.listener.income] [status:error] [reason:invalid payload]");
                return; 
            }

            log.info("[message:conversor.listener.income] [status:completed] [payload:{}]", message);

            final var cCmd = new ConversorCommand(
                dto.bucket(),
                dto.videoKey()
            );

            defaultConversorVideoUseCase.execute(cCmd);

        } catch (Exception e) {
            log.error("[message:conversor.listener.income] [status:error] [reason:exception] [error:{}]", e.getMessage());
        }
    }
}
