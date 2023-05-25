package com.redhat.greetings.cqrs;

import java.time.Instant;

public record AddGreetingCommand(SourceSystem sourceSystem, Instant timestamp, boolean validated, String text) {
}
