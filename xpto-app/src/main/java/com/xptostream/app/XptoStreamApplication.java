package com.xptostream.app;

import com.xptostream.core.domain.UserEvent;
import com.xptostream.core.port.UserInterestRepository;
import com.xptostream.core.usecase.ProcessUserEventUseCase;
import com.xptostream.infra.repository.DynamoDbUserInterestRepository;
import com.xptostream.entrypoints.kafka.UserEventConsumer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import org.springframework.beans.factory.annotation.Value;
import java.util.function.Consumer;

@SpringBootApplication(scanBasePackages = {"com.xptostream.core", "com.xptostream.infra", "com.xptostream.entrypoints"})
public class XptoStreamApplication {

    @Bean
    public UserInterestRepository userInterestRepository(DynamoDbClient dynamoDbClient, @Value("${dynamodb.table:user-interest}") String tableName) {
        return new DynamoDbUserInterestRepository(dynamoDbClient, tableName);
    }

    @Bean
    public ProcessUserEventUseCase processUserEventUseCase(UserInterestRepository userInterestRepository) {
        return new com.xptostream.core.usecase.ProcessUserEventUseCaseImpl(userInterestRepository);
    }

    @Bean
    public UserEventConsumer userEventConsumerBean(ProcessUserEventUseCase processUserEventUseCase) {
        return new UserEventConsumer(processUserEventUseCase);
    }

    @Bean
    public Consumer<UserEvent> userEventConsumer(UserEventConsumer userEventConsumerBean) {
        return userEventConsumerBean.userEventConsumer();
    }

    public static void main(String[] args) {
        SpringApplication.run(XptoStreamApplication.class, args);
    }
}
