package com.xptostream.entrypoints.kafka;

import com.xptostream.core.domain.UserEvent;
import com.xptostream.core.usecase.ProcessUserEventUseCase;
import java.util.function.Consumer;

public class UserEventConsumer {
    private final ProcessUserEventUseCase processUserEventUseCase;

    public UserEventConsumer(ProcessUserEventUseCase processUserEventUseCase) {
        this.processUserEventUseCase = processUserEventUseCase;
    }

    public Consumer<UserEvent> userEventConsumer() {
        System.out.println("🚀 [Kafka] Event received: " + this.processUserEventUseCase + " | Thread: " + Thread.currentThread());
        return event -> processUserEventUseCase.execute(event);
    }
}
