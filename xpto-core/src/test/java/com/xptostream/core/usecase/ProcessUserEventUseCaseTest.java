package com.xptostream.core.usecase;

import com.xptostream.core.domain.EventType;
import com.xptostream.core.domain.UserEvent;
import com.xptostream.core.port.UserInterestRepository;

import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class ProcessUserEventUseCaseTest {

    @Test
    void shouldCallRepositoryWith15PointsForPurchase() {
        UserInterestRepository repo = mock(UserInterestRepository.class);
        ProcessUserEventUseCase useCase = new ProcessUserEventUseCaseImpl(repo);

        UserEvent event = new UserEvent("user1", "electronics", EventType.PURCHASE);
        useCase.execute(event);

        verify(repo, times(1)).saveIncremental("user1", "electronics", 15L);
    }

    @Test
    void shouldCallRepositoryWith1PointForView() {
        UserInterestRepository repo = mock(UserInterestRepository.class);
        ProcessUserEventUseCase useCase = new ProcessUserEventUseCaseImpl(repo);

        UserEvent event = new UserEvent("user2", "books", EventType.VIEW);
        useCase.execute(event);

        verify(repo, times(1)).saveIncremental("user2", "books", 1L);
    }

    @Test
    void shouldThrowWhenEventTypeIsNull() {
        UserInterestRepository repo = mock(UserInterestRepository.class);
        ProcessUserEventUseCase useCase = new ProcessUserEventUseCaseImpl(repo);

        UserEvent event = new UserEvent("user3", "category", null);
        
        assertThrows(IllegalArgumentException.class, () -> useCase.execute(event),
            "Deve lançar exceção quando EventType é nulo");
    }

    @Test
    void shouldThrowWhenUserEventIsNull() {
        UserInterestRepository repo = mock(UserInterestRepository.class);
        ProcessUserEventUseCase useCase = new ProcessUserEventUseCaseImpl(repo);

        assertThrows(IllegalArgumentException.class, () -> useCase.execute(null),
            "Deve lançar exceção quando UserEvent é nulo");
    }
}