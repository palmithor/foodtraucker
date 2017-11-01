package com.foodtraucker.serverless.trucks;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.foodtraucker.serverless.DynamoDBIntegrationTest;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

import static org.junit.Assert.*;

/**
 * @author palmithor
 * @since 2017-10-31
 */
public class CheckinDaoTest extends DynamoDBIntegrationTest {

    private CheckinDao dao; // class under test


    @Before
    public void setUp()  {
        dao = new CheckinDao(new DynamoDBMapper(getDynamoDB()));
    }


    @Test
    public void shouldCreate() {
        final String foodtruckId = UUID.randomUUID().toString();
        final CheckinRequest checkinRequest = new CheckinRequest();
        checkinRequest.setCheckin(Instant.now().toEpochMilli());
        checkinRequest.setCheckout(Instant.now().toEpochMilli());
        checkinRequest.setLat(50f);
        checkinRequest.setLon(10f);
        dao.create(foodtruckId, checkinRequest);
    }
}