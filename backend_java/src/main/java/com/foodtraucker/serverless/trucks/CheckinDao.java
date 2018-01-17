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
class CheckinDao {

    private final DynamoDBMapper dynamoDBMapper;

    CheckinDao(final DynamoDBMapper dynamoDBMapper) {
        this.dynamoDBMapper = dynamoDBMapper;
    }


    Checkin create(final String foodtruckId, final CheckinRequest checkinRequest) {
        final long now = Instant.now().toEpochMilli();
        final Checkin checkin = new Checkin();
        checkin.setFoodtruckId(foodtruckId);
        checkin.setLon(checkinRequest.getLon().doubleValue());
        checkin.setLat(checkinRequest.getLat().doubleValue());
        checkin.setCheckin(checkinRequest.getCheckin());
        checkin.setCheckout(checkinRequest.getCheckout());
        checkin.setCreated(now);
        checkin.setUpdated(now);
        dynamoDBMapper.save(checkin, DynamoDBUtils.getCheckinsMapperConfig());
        return checkin;
    }
}
