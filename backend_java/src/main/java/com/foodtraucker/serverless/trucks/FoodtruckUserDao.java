package com.foodtraucker.serverless.trucks;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.foodtraucker.serverless.dynamo.DynamoDBUtils;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author palmithor
 * @since 2017-10-31
 */
class FoodtruckUserDao {

    private final DynamoDBMapper dynamoDBMapper;

    FoodtruckUserDao(final DynamoDBMapper dynamoDBMapper) {
        this.dynamoDBMapper = dynamoDBMapper;
    }

    List<FoodtruckUser> findByCognitoId(final String cognitoId) {
        Map<String, String> expressionAttributesNames = new HashMap<>();
        expressionAttributesNames.put("#cognitoId", "cognito_id");

        Map<String, AttributeValue> expressionAttributeValues = new HashMap<>();
        expressionAttributeValues.put(":cognitoId", new AttributeValue().withS(cognitoId));

        DynamoDBQueryExpression<FoodtruckUser> queryExpression = new DynamoDBQueryExpression<FoodtruckUser>()
                .withKeyConditionExpression("#cognitoId = :cognitoId")
                .withExpressionAttributeNames(expressionAttributesNames)
                .withExpressionAttributeValues(expressionAttributeValues);

        return dynamoDBMapper.query(FoodtruckUser.class, queryExpression, DynamoDBUtils.getFoodtruckUsersMapperConfig());
    }

    FoodtruckUser create(final String cognitoId, final String foodtruckId, final String role) {
        final long now = Instant.now().toEpochMilli();
        final FoodtruckUser foodtruckUser = new FoodtruckUser();
        foodtruckUser.setCognitoId(cognitoId);
        foodtruckUser.setFoodtruckId(foodtruckId);
        foodtruckUser.setRole(role);
        foodtruckUser.setCreated(now);
        foodtruckUser.setUpdated(now);
        dynamoDBMapper.save(foodtruckUser, DynamoDBUtils.getFoodtrucksMapperConfig());
        return foodtruckUser;
    }
}
