package org.jmb.kafkakafkamock;

import org.apache.camel.builder.RouteBuilder;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class Route extends RouteBuilder {

    @ConfigProperty(name = "mock.kafka.process.time")
    private Integer delayInMilliSeconds;

    @Override
    public void configure() throws Exception {
        from("kafka:{{kafka.topic.input}}")
            .log("Message received from topic {{kafka.topic.input}}: ${body}")
            .log("Processing message for " + delayInMilliSeconds + " ms")
            .delay(delayInMilliSeconds)
            .to("kafka:{{kafka.topic.output}}")
            .log("Message forwarded to topic {{kafka.topic.output}}")
        .end();
    }
}