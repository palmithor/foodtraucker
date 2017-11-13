package com.foodtraucker.serverless.menus;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.foodtraucker.serverless.dynamo.TableEnvConstant;

/**
 * @author armannds
 * @since 2017-11-12
 */
@DynamoDBTable(tableName = TableEnvConstant.FOODTRUCK_MENUS_TABLE)
public class FoodtruckMenu {

    private String foodtruckId;
    private String id;
    private String name;
    private String description;

    @DynamoDBHashKey(attributeName = "foodtruck_id")
    public String getFoodtruckId() {
        return foodtruckId;
    }

    @DynamoDBRangeKey(attributeName = "id")
    public String getId() {
        return id;
    }

    @DynamoDBAttribute(attributeName = "name")
    public String getName() {
        return name;
    }

    @DynamoDBAttribute(attributeName = "desc")
    public String getDescription() {
        return description;
    }

    public void setFoodtruckId(String foodtruckId) {
        this.foodtruckId = foodtruckId;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
