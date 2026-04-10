package com.xptostream.infra.repository;

import com.xptostream.core.port.UserInterestRepository;
import org.junit.jupiter.api.*;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.*;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Testcontainers
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserInterestRepositoryIT {
    private static final String TABLE_NAME = "user_interest";

    @Container
    private static final GenericContainer<?> dynamoDB = createContainer();

    private static GenericContainer<?> createContainer() {
        GenericContainer<?> container = new GenericContainer<>(DockerImageName.parse("amazon/dynamodb-local:latest"));
        container.withExposedPorts(8000);
        return container;
        }

    private DynamoDbClient dynamoDbClient;
    private UserInterestRepository repository;

    @BeforeAll
    void setUp() {
        dynamoDB.start();
        String endpoint = String.format("http://%s:%d", dynamoDB.getHost(), dynamoDB.getMappedPort(8000));
        dynamoDbClient = DynamoDbClient.builder()
                .endpointOverride(URI.create(endpoint))
                .region(Region.US_EAST_1)
                .credentialsProvider(StaticCredentialsProvider.create(
                        AwsBasicCredentials.create("fakeMyKey", "fakeMySecret")
                ))
                .build();

        CreateTableRequest createTableRequest = CreateTableRequest.builder()
                .tableName(TABLE_NAME)
                .keySchema(
                        KeySchemaElement.builder().attributeName("userId").keyType(KeyType.HASH).build(),
                        KeySchemaElement.builder().attributeName("category").keyType(KeyType.RANGE).build()
                )
                .attributeDefinitions(
                        AttributeDefinition.builder().attributeName("userId").attributeType(ScalarAttributeType.S).build(),
                        AttributeDefinition.builder().attributeName("category").attributeType(ScalarAttributeType.S).build()
                )
                .provisionedThroughput(ProvisionedThroughput.builder().readCapacityUnits(5L).writeCapacityUnits(5L).build())
                .build();
        dynamoDbClient.createTable(createTableRequest);
        repository = new DynamoDbUserInterestRepository(dynamoDbClient, TABLE_NAME);
    }

    @AfterAll
    void tearDown() {
        if (dynamoDbClient != null) {
            dynamoDbClient.close();
        }
        dynamoDB.stop();
    }

    @Test
    void saveIncremental_shouldBeAtomicAndAccurate() {
        String userId = "user1";
        String category = "catA";
        long increment = 3L;

        repository.saveIncremental(userId, category, increment);
        repository.saveIncremental(userId, category, increment);

        Map<String, AttributeValue> key = new HashMap<>();
        key.put("userId", AttributeValue.builder().s(userId).build());
        key.put("category", AttributeValue.builder().s(category).build());

        GetItemRequest getItemRequest = GetItemRequest.builder()
                .tableName(TABLE_NAME)
                .key(key)
                .build();
        Map<String, AttributeValue> item = dynamoDbClient.getItem(getItemRequest).item();
        long score = Long.parseLong(item.get("score").n());
        assertEquals(6L, score);
    }
}
