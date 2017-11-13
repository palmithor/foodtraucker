package com.foodtraucker.serverless.menus;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.foodtraucker.serverless.dynamo.DynamoDBUtils;

import java.util.List;

/**
 * @author armannds
 * @since 2017-11-12
 */
public class FoodtruckMenuDao {

    private final DynamoDBMapper dynamoDBMapper;

    public FoodtruckMenuDao(final DynamoDBMapper dynamoDBMapper) {
        this.dynamoDBMapper = dynamoDBMapper;
    }

    public void create(final String foodtruckId, final List<AddMenuRequest> addMenuRequests) {
        addMenuRequests.forEach(addMenuRequest -> create(foodtruckId, addMenuRequest));

    }

    public void create(final String foodtruckId, final AddMenuRequest addMenuRequest) {
        final FoodtruckMenu foodtruckMenu = new FoodtruckMenu();
        foodtruckMenu.setFoodtruckId(foodtruckId);
        foodtruckMenu.setName(addMenuRequest.getName());
        foodtruckMenu.setDescription(addMenuRequest.getDescription());
        dynamoDBMapper.save(foodtruckMenu, DynamoDBUtils.getFoodtruckMenusMapperConfig());
    }
}
