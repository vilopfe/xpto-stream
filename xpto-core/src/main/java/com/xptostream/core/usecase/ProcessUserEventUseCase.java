package com.xptostream.core.usecase;

import com.xptostream.core.domain.UserEvent;
import com.xptostream.core.domain.UserInterest;

public interface ProcessUserEventUseCase {
    void process(UserEvent event, UserInterest interest);
}