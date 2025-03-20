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
        final var aResult = Json.readValue(message, RabbitConversorResquest.class);
        
        if (aResult instanceof RabbitConversorResquest) {
            log.info("[message:conversor.listener.income] [status:completed] [payload:{}]", message);

            RabbitConversorResquest dto = (RabbitConversorResquest) aResult;

            final var cCmd = new ConversorCommand(
                dto.bucket(),
                dto.videoKey()
            );

            defaultConversorVideoUseCase.execute(cCmd);
        } else {
            log.error("[message:conversor.listener.income] [status:error] [payload:{}]", message);
        }
    }
    
}
