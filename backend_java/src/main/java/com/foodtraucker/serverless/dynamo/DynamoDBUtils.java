package com.foodtraucker.serverless.dynamo;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.foodtraucker.serverless.trucks.Checkin;
import com.foodtraucker.serverless.trucks.Truck;

/**
 * @author palmithor
 * @since 2017-10-28
 */
public class DynamoDBUtils {

    private DynamoDBUtils() {

    }


    public static DynamoDBMapperConfig getTrucksMapperConfig() {
        return getMapperConfig(TableEnvConstant.TRUCKS_TABLE);
    }

    public static DynamoDBMapperConfig getCheckinsMapperConfig() {
        return getMapperConfig(TableEnvConstant.CHECKINS_TABLE);
    }

    private static DynamoDBMapperConfig getMapperConfig(final String envTableKey) {
        return DynamoDBMapperConfig.builder()
                .withTableNameOverride(DynamoDBMapperConfig.TableNameOverride.withTableNameReplacement(System.getenv(envTableKey)))
                .build();
    }

}
