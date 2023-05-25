package com.redhat.greetings.cqrs;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;

import java.sql.Timestamp;

@Entity
public class Greeting extends PanacheEntity {

    SourceSystem sourceSystem;

    String text;

    Timestamp timestamp;

    boolean valid;
    public static Greeting createFromCommand(AddGreetingCommand addGreetingCommand) {

        return new Greeting(addGreetingCommand.sourceSystem(),
                addGreetingCommand.text(),
                Timestamp.from(addGreetingCommand.timestamp()),
                addGreetingCommand.validated());
    }

    public Greeting(SourceSystem sourceSystem, String text, Timestamp timestamp, boolean valid) {
        this.sourceSystem = sourceSystem;
        this.text = text;
        this.timestamp = timestamp;
        this.valid = valid;
    }

    public Greeting() {
    }

    @Override
    public String toString() {
        return "Greeting{" +
                "sourceSystem=" + sourceSystem +
                ", text='" + text + '\'' +
                ", timestamp=" + timestamp +
                ", validated=" + valid +
                ", id=" + id +
                '}';
    }
}

