package com.foodtraucker.serverless.menus;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.foodtraucker.serverless.DynamoDBIntegrationTest;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

/**
 * @author armannds
 * @since 2017-11-13
 */
public class FoodtruckMenuDaoTest extends DynamoDBIntegrationTest {

    private FoodtruckMenuDao dao;

    @Before
    public void setUp() {
        dao = new FoodtruckMenuDao(new DynamoDBMapper(getDynamoDB()));
    }

    @Test
    public void shouldCreateOne() {
        final String foodtruckId = UUID.randomUUID().toString();
        final AddMenuRequest addMenuRequest = new AddMenuRequest();
        addMenuRequest.setName("Awesome burger");
        addMenuRequest.setDescription("Bacon bacon and bacon");
        dao.create(foodtruckId, addMenuRequest);
    }
}
