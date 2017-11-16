package com.foodtraucker.serverless.dynamo;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.foodtraucker.serverless.menus.FoodtruckMenu;
import com.foodtraucker.serverless.trucks.Checkin;
import com.foodtraucker.serverless.trucks.Foodtruck;
import com.foodtraucker.serverless.trucks.FoodtruckUser;

/**
 * @author armannds
 * @since 2017-11-16
 */
public enum DynamoDBTable {

    CHECKINS(Checkin.class, DynamoDBUtils.getCheckinsMapperConfig()),
    FOODTRUCKS(Foodtruck.class, DynamoDBUtils.getFoodtrucksMapperConfig()),
    FOODTRUCK_USERS(FoodtruckUser.class, DynamoDBUtils.getFoodtruckUsersMapperConfig()),
    FOODTRUCK_MENUS(FoodtruckMenu.class, DynamoDBUtils.getFoodtruckMenusMapperConfig());

    private final Class clazz;
    private final DynamoDBMapperConfig mapperConfig;

    <T> DynamoDBTable(Class<T> clazz, DynamoDBMapperConfig mapperConfig) {
        this.clazz = clazz;
        this.mapperConfig = mapperConfig;
    }

    @SuppressWarnings("unchecked")
    public <T> Class<T> getClazz() {
        return clazz;
    }

    public DynamoDBMapperConfig getMapperConfig() {
        return mapperConfig;
    }
}
