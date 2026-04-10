package com.xptostream.app;

import com.xptostream.core.port.UserInterestRepository;
import com.xptostream.core.usecase.ProcessUserEventUseCase;
import com.xptostream.infra.repository.DynamoDbUserInterestRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import org.springframework.beans.factory.annotation.Value;

@SpringBootApplication(scanBasePackages = {"com.xptostream.core", "com.xptostream.infra", "com.xptostream.entrypoints"})
public class XptoStreamApplication {

    @Bean
    public UserInterestRepository userInterestRepository(DynamoDbClient dynamoDbClient, @Value("${dynamodb.table:user_interest}") String tableName) {
        return new DynamoDbUserInterestRepository(dynamoDbClient, tableName);
    }

    @Bean
    public ProcessUserEventUseCase processUserEventUseCase(UserInterestRepository userInterestRepository) {
        return new com.xptostream.core.usecase.ProcessUserEventUseCaseImpl(userInterestRepository);
    }

    public static void main(String[] args) {
        SpringApplication.run(XptoStreamApplication.class, args);
    }
}
