package com.fiap.tech.conversor.infra.configuration.amqp;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fiap.tech.conversor.infra.annotations.ConversorReponseQueue;
import com.fiap.tech.conversor.infra.annotations.ConversorRequestQueue;
import com.fiap.tech.conversor.infra.configuration.properties.amqp.QueueProperties;

@Configuration
public class AmqpConfiguration {

    @Bean
    @ConversorReponseQueue
    @ConfigurationProperties("amqp.queues.conversor-response")
    public QueueProperties conversorResponseQueueProperties() {
        return new QueueProperties();
    }

    @Bean
    @ConversorRequestQueue
    @ConfigurationProperties("amqp.queues.conversor-request")
    public QueueProperties conversorRequestQueueProperties() {
        return new QueueProperties();
    }

    @Bean
    @ConversorReponseQueue
    public DirectExchange conversorResponseExchange(@ConversorReponseQueue QueueProperties props) {
        return new DirectExchange(props.getExchange());
    }

    @Bean
    @ConversorReponseQueue
    public Queue conversorResponseQueue(@ConversorReponseQueue QueueProperties props) {
        return new Queue(props.getQueue());
    }

    @Bean
    public Binding conversorResponseBinding(
            @ConversorReponseQueue DirectExchange exchange,
            @ConversorReponseQueue Queue queue,
            @ConversorReponseQueue QueueProperties props) {
        return BindingBuilder.bind(queue).to(exchange).with(props.getRoutingKey());
    }

    @Bean
    @ConversorRequestQueue
    public DirectExchange conversorRequestExchange(@ConversorRequestQueue QueueProperties props) {
        return new DirectExchange(props.getExchange());
    }

    @Bean
    @ConversorRequestQueue
    public Queue conversorRequestQueue(@ConversorRequestQueue QueueProperties props) {
        return new Queue(props.getQueue());
    }

    @Bean
    public Binding conversorRequestBinding(
            @ConversorRequestQueue DirectExchange exchange,
            @ConversorRequestQueue Queue queue,
            @ConversorRequestQueue QueueProperties props) {
        return BindingBuilder.bind(queue).to(exchange).with(props.getRoutingKey());
    }
}
