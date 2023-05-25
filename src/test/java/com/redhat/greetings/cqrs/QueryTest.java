package com.redhat.greetings.cqrs;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectSpy;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
public class QueryTest {

    @Inject
    KafkaService kafkaService;

    @InjectSpy
    GreetingRepository greetingRepository;
    static final Logger LOGGER = LoggerFactory.getLogger(QueryTest.class);

    @Test
    @Transactional
    public void testAllValidatedGreetingsQuery() {

        List<GreetingRecord> greetingRecordList = kafkaService.allValidatedGreetings();
        greetingRecordList.forEach(greetingRecord -> LOGGER.info("{}", greetingRecord));
        assertEquals(3, greetingRecordList.size());
    }

    @BeforeEach @Transactional
    public void addGreetings() {

        addValidGreetings(2);
        addInvalidGreeting(3);
        LOGGER.info("Greetings added");
    }

    private void addInvalidGreeting(int desiredNumberOfGreetings) {
        addGreeting(desiredNumberOfGreetings, false);
    }

    private void addValidGreetings(int desiredNumberOfGreetings) {
        addGreeting(desiredNumberOfGreetings, true);
    }

    private void addGreeting(int desiredNumberOfGreetings, boolean valid) {
        for (int i = 0; i <= desiredNumberOfGreetings; i++) {
            Greeting greeting = new Greeting(SourceSystem.REST_API, "Hello, " + i, Timestamp.from(Instant.now()), valid);
            greeting.persist();
        }
    }
}
