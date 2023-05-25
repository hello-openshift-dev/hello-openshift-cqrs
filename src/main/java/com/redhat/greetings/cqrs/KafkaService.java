package com.redhat.greetings.cqrs;


import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class KafkaService {

    static final Logger LOGGER = LoggerFactory.getLogger(KafkaService.class);

    @Inject
    GreetingRepository greetingRepository;

    @Transactional
    public void addGreeting(AddGreetingCommand addGreetingCommand) {

        LOGGER.debug("AddGreetingCommand received: {}", addGreetingCommand);
        Greeting greeting = Greeting.createFromCommand(addGreetingCommand);
        greetingRepository.persist(greeting);
    }

    public List<GreetingRecord> allValidatedGreetings() {
        return greetingRepository.listAll().stream().filter(
            greeting -> greeting.valid == true
        ).map(greeting -> {
            return new GreetingRecord(greeting.text, greeting.valid);
        }).collect(Collectors.toList());
    }
}
