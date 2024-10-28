package org.jmb.httpkafkamock;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class HttpRoute extends RouteBuilder {

    @ConfigProperty(name = "mock.kafka.sending.delay")
    private Integer delayInMilliSeconds;

    @Override
    public void configure() throws Exception {
        from("netty-http:http://0.0.0.0:{{netty.server.port}}?httpMethodRestrict=POST")
            .log("Message received from HTTP POST: ${body}")
            // Unmarshal and marshal are performed to send a standart message and remove not supported fields
            .unmarshal().json(JsonLibrary.Jackson, MessageDetails.class)
            .process(createEventMessage)
            .marshal().json(JsonLibrary.Jackson)
            .log("Waiting " + delayInMilliSeconds + " ms")
            .delay(delayInMilliSeconds)
            .log("Sending message to kafka topic {{kafka.topic.name}}")
            .to("kafka:{{kafka.topic.name}}")
        .end();
    }

    private Processor createEventMessage = exchange -> {
        final MessageDetails operationRequest = exchange.getIn().getBody(MessageDetails.class);
        
        final Message operationEvent = new Message();
        operationEvent.setId(UUID.randomUUID().toString());
        operationEvent.setTime(Instant.now().atZone(java.time.ZoneOffset.UTC).format(DateTimeFormatter.ISO_INSTANT));
        operationEvent.setContentType("application/json");
        operationEvent.setData(operationRequest);

        exchange.getIn().setBody(operationEvent);
    };
}