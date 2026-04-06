package com.xptostream.core.usecase;

import com.xptostream.core.domain.UserEvent;
import com.xptostream.core.domain.UserInterest;
import com.xptostream.core.domain.EventType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProcessUserEventUseCaseTest {

    @Test
    void shouldAdd15PointsForPurchaseEvent() {
        ProcessUserEventUseCase useCase = new ProcessUserEventUseCaseImpl();
        UserInterest interest = new UserInterest("user1");
        UserEvent event = new UserEvent("user1", EventType.PURCHASE, "item1");

        useCase.process(event, interest);

        assertEquals(15, interest.getTotalPoints());
    }
}