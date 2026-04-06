package com.xptostream.core.usecase;

import com.xptostream.core.domain.UserEvent;
import com.xptostream.core.domain.UserInterest;
import com.xptostream.core.domain.EventType;

public class ProcessUserEventUseCaseImpl implements ProcessUserEventUseCase {
    @Override
    public void process(UserEvent event, UserInterest interest) {
        if (event.eventType() == EventType.PURCHASE) {
            interest.addPoints(15);
        }
    }
}