package com.redhat.greetings.cqrs;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;

import java.sql.Timestamp;
import java.time.Instant;

@Entity
public class Greeting extends PanacheEntity {

    SourceSystem sourceSystem;

    String text;

    Timestamp timestamp;

    boolean validated;
    public static Greeting createFromCommand(AddGreetingCommand addGreetingCommand) {

        return new Greeting(addGreetingCommand.sourceSystem(),
                addGreetingCommand.text(),
                Timestamp.from(addGreetingCommand.timestamp()),
                addGreetingCommand.validated());
    }

    public Greeting(SourceSystem sourceSystem, String text, Timestamp timestamp, boolean validated) {
        this.sourceSystem = sourceSystem;
        this.text = text;
        this.timestamp = timestamp;
        this.validated = validated;
    }

    public Greeting() {
    }

    @Override
    public String toString() {
        return "Greeting{" +
                "sourceSystem=" + sourceSystem +
                ", text='" + text + '\'' +
                ", timestamp=" + timestamp +
                ", validated=" + validated +
                ", id=" + id +
                '}';
    }
}

