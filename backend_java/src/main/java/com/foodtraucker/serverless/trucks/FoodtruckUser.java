package com.foodtraucker.serverless.trucks;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import com.foodtraucker.serverless.dynamo.TableEnvConstant;

/**
 * @author palmithor
 * @since 26.10.2017.
 */
@SuppressWarnings("WeakerAccess")
@DynamoDBTable(tableName = TableEnvConstant.FOODTRUCK_USERS_TABLE)
public class FoodtruckUser {

    private String cognitoId;
    private String foodtruckId;
    private String role;
    private Long created;
    private Long updated;

    @DynamoDBHashKey(attributeName = "cognito_id")
    public String getCognitoId() {
        return cognitoId;
    }


    @DynamoDBRangeKey(attributeName = "foodtruck_id")
    public String getFoodtruckId() {
        return foodtruckId;
    }

    @DynamoDBAttribute(attributeName = "role")
    public String getRole() {
        return role;
    }

    @DynamoDBAttribute(attributeName = "created")
    public Long getCreated() {
        return created;
    }
    @DynamoDBAttribute(attributeName = "updated")
    public Long getUpdated() {
        return updated;
    }

    public void setCognitoId(final String cognitoId) {
        this.cognitoId = cognitoId;
    }

    public void setFoodtruckId(final String foodtruckId) {
        this.foodtruckId = foodtruckId;
    }

    public void setRole(final String role) {
        this.role = role;
    }

    public void setCreated(final Long created) {
        this.created = created;
    }

    public void setUpdated(final Long updated) {
        this.updated = updated;
    }
}
