package com.foodtraucker.serverless.dynamo;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.foodtraucker.serverless.trucks.Checkin;
import com.foodtraucker.serverless.trucks.FoodtruckUser;
import com.foodtraucker.serverless.trucks.Foodtruck;

import java.util.Arrays;
import java.util.Optional;

/**
 * @author palmithor
 * @since 2017-10-28
 */
public class DynamoDBTestUtils {

    private final AmazonDynamoDB dynamoDB;
    private final DynamoDBMapper dynamoDBMapper;


    public DynamoDBTestUtils(final AmazonDynamoDB dynamoDB) {
        this.dynamoDB = dynamoDB;
        this.dynamoDBMapper = new DynamoDBMapper(dynamoDB);
    }

    public void createSchema() {
        final CreateTableRequest checkinCreateTableRequest = dynamoDBMapper.generateCreateTableRequest(Checkin.class, DynamoDBUtils.getCheckinsMapperConfig());
        final CreateTableRequest foodtruckCreateTableRequest = dynamoDBMapper.generateCreateTableRequest(Foodtruck.class, DynamoDBUtils.getCheckinsMapperConfig());
        final CreateTableRequest foodtruckUserCreateTableRequest = dynamoDBMapper.generateCreateTableRequest(FoodtruckUser.class, DynamoDBUtils.getFoodtruckUsersMapperConfig());
        setProvisionedThroughput(checkinCreateTableRequest, foodtruckCreateTableRequest, foodtruckUserCreateTableRequest);
        dynamoDB.createTable(foodtruckCreateTableRequest);
        dynamoDB.createTable(checkinCreateTableRequest);
        dynamoDB.createTable(foodtruckUserCreateTableRequest);
    }

    public AmazonDynamoDB getDynamoDB() {
        return dynamoDB;
    }

    public DynamoDBMapper getDynamoDBMapper() {
        return dynamoDBMapper;
    }

    private void setProvisionedThroughput(final CreateTableRequest... requests) {
        final ProvisionedThroughput provisionedThroughput = new ProvisionedThroughput(5L, 5L);

        Arrays.asList(requests).forEach(request -> {
            request.setProvisionedThroughput(provisionedThroughput);
            Optional.ofNullable(request.getGlobalSecondaryIndexes())
                    .ifPresent(globalSecondaryIndices -> globalSecondaryIndices
                            .forEach(v -> v.setProvisionedThroughput(provisionedThroughput)));
        });
    }

}
