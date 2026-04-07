package com.xptostream.core.usecase;

import com.xptostream.core.domain.UserEvent;

public interface ProcessUserEventUseCase {
    void execute(UserEvent event);
}