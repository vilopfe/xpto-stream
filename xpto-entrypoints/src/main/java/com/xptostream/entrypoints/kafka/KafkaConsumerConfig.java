package com.xptostream.entrypoints.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xptostream.core.domain.UserEvent;
import com.xptostream.core.usecase.ProcessUserEventUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import java.util.function.Consumer;

@Configuration
public class KafkaConsumerConfig {

    private final ObjectMapper objectMapper;

    public KafkaConsumerConfig(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Bean
    public Consumer<Message<?>> userEventConsumer(ProcessUserEventUseCase processUserEventUseCase) {
        return message -> {
            try {
                UserEvent event;
                Object payload = message.getPayload();

                // Se o Spring entregar byte[] (o que está causando o erro atual)
                if (payload instanceof byte[] bytes) {
                    event = objectMapper.readValue(bytes, UserEvent.class);
                } 
                // Se o payload já vier como String (ex: via console producer sem headers)
                else if (payload instanceof String json) {
                    event = objectMapper.readValue(json, UserEvent.class);
                }
                // Fallback caso o Spring consiga converter no futuro
                else {
                    event = objectMapper.convertValue(payload, UserEvent.class);
                }

                System.out.println("✅ [xpto-stream] Evento recebido: " + event);
                processUserEventUseCase.execute(event);

            } catch (Exception e) {
                // Log detalhado para identificar se o problema é o Schema do JSON
                System.err.println("❌ Erro ao processar evento Kafka: " + e.getMessage());
                throw new RuntimeException("Falha na desserialização do UserEvent", e);
            }
        };
    }
}