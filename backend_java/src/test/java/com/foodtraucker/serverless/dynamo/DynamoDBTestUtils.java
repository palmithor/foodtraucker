package com.foodtraucker.serverless.dynamo;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.foodtraucker.serverless.trucks.Checkin;
import com.foodtraucker.serverless.trucks.Truck;

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
        final CreateTableRequest truckCreateTableRequest = dynamoDBMapper.generateCreateTableRequest(Truck.class, DynamoDBUtils.getCheckinsMapperConfig());
        setProvisionedThroughput(checkinCreateTableRequest, truckCreateTableRequest);
        dynamoDB.createTable(truckCreateTableRequest);
        dynamoDB.createTable(checkinCreateTableRequest);
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
