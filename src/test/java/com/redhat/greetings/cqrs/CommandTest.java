package com.redhat.greetings.cqrs;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectSpy;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;

@QuarkusTest
public class CommandTest {

    static final Logger LOGGER = LoggerFactory.getLogger(CommandTest.class);

    @Inject
    KafkaService kafkaService;

    @InjectSpy
    GreetingRepository greetingRepository;


    @Test
    public void testCommand() {

        AddGreetingCommand greetingPayload = new AddGreetingCommand(SourceSystem.REST_API, Instant.now(), true, "Hi, there!");
        kafkaService.addGreeting(greetingPayload);
        ArgumentCaptor<Greeting> argumentCaptor = ArgumentCaptor.forClass(Greeting.class);
        Mockito.verify(greetingRepository, Mockito.times(1)).persist(argumentCaptor.capture());
    }

}
