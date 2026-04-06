package com.xptostream.infra.repository;

import com.xptostream.core.port.UserInterestRepository;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.UpdateItemRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class DynamoDbUserInterestRepository implements UserInterestRepository {
    private final DynamoDbClient dynamoDbClient;
    private final String tableName;

    public DynamoDbUserInterestRepository(DynamoDbClient dynamoDbClient, String tableName) {
        this.dynamoDbClient = Objects.requireNonNull(dynamoDbClient);
        this.tableName = Objects.requireNonNull(tableName);
    }

    @Override
    public void saveIncremental(String userId, String category, long points) {
        Map<String, AttributeValue> key = new HashMap<>();
        key.put("userId", AttributeValue.builder().s(userId).build());
        key.put("category", AttributeValue.builder().s(category).build());

        Map<String, String> expressionAttributeNames = new HashMap<>();
        expressionAttributeNames.put("#score", "score");

        Map<String, AttributeValue> expressionAttributeValues = new HashMap<>();
        expressionAttributeValues.put(":zero", AttributeValue.builder().n("0").build());
        expressionAttributeValues.put(":val", AttributeValue.builder().n(Long.toString(points)).build());

        UpdateItemRequest request = UpdateItemRequest.builder()
                .tableName(tableName)
                .key(key)
                .updateExpression("SET #score = if_not_exists(#score, :zero) + :val")
                .expressionAttributeNames(expressionAttributeNames)
                .expressionAttributeValues(expressionAttributeValues)
                .build();

        dynamoDbClient.updateItem(request);
    }
}
