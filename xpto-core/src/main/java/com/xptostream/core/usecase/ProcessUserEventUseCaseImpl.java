package com.xptostream.core.usecase;

import com.xptostream.core.domain.UserEvent;
import com.xptostream.core.port.UserInterestRepository;
import java.util.Objects;

public class ProcessUserEventUseCaseImpl implements ProcessUserEventUseCase {
    private final UserInterestRepository repository;

    public ProcessUserEventUseCaseImpl(UserInterestRepository repository) {
        this.repository = repository;
    }

    @Override
    public void execute(UserEvent event) {
        // Validação defensiva do contrato
        if (event == null || event.type() == null) {
            throw new IllegalArgumentException("[CONTRATO INVÁLIDO] UserEvent ou EventType não podem ser nulos");
        }
        
        Objects.requireNonNull(event.userId(), "userId é obrigatório");
        Objects.requireNonNull(event.category(), "category é obrigatório");
        
        long points = event.type().getPoints();
        repository.saveIncremental(event.userId(), event.category(), points);
    }
}
