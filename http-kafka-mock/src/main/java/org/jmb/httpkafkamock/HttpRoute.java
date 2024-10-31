package org.jmb.httpkafkamock;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.jboss.logging.Logger;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/")
@ApplicationScoped
public class HttpRoute {

    private static final Logger LOG = Logger.getLogger(HttpRoute.class);

    @ConfigProperty(name = "mock.kafka.sending.delay")
    private Integer delayInMilliSeconds;

    @ConfigProperty(name = "kafka.topic.name")
    private String topic;

    @Inject
    @Channel("input") // Canal de salida de Kafka
    private Emitter<Message> messageEmitter;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response processMessage(MessageDetails request) throws InterruptedException {

        final Message cloudEvent = new Message();
        cloudEvent.setData(request);
        LOG.info(String.format("Waiting %d seconds", delayInMilliSeconds));
        Thread.sleep(delayInMilliSeconds);
        LOG.info(String.format("Sending message to %s topic", topic));
        messageEmitter.send(cloudEvent);

        return Response.ok().build();
    }

}