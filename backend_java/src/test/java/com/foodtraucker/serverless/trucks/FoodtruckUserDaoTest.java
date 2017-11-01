package com.foodtraucker.serverless.trucks;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMappingException;
import com.foodtraucker.serverless.DynamoDBIntegrationTest;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

/**
 * @author palmithor
 * @since 2017-10-31
 */
public class FoodtruckUserDaoTest extends DynamoDBIntegrationTest {

    private FoodtruckUserDao dao; // class under test


    @Before
    public void setUp()  {
        dao = new FoodtruckUserDao(new DynamoDBMapper(getDynamoDB()));
    }


    @Test
    public void shouldCreateUser() {
        final String cognitoId = UUID.randomUUID().toString();
        final FoodtruckUser foodtruckUser = dao.create(cognitoId, UUID.randomUUID().toString(), "owner");

        final List<FoodtruckUser> users = dao.findByCognitoId(cognitoId);
        assertThat(users).hasSize(1);
        assertThat(users.get(0)).isEqualToComparingFieldByFieldRecursively(foodtruckUser);
    }

    @Test
    public void shouldCreateUserWithTwoTrucks() {
        final String cognitoId = UUID.randomUUID().toString();
        dao.create(cognitoId, UUID.randomUUID().toString(), "owner");
        dao.create(cognitoId, UUID.randomUUID().toString(), "manager");

        final List<FoodtruckUser> users = dao.findByCognitoId(cognitoId);
        assertThat(users).hasSize(2);
    }

    @Test
    public void shouldFailCreateUserMissingHashKey() {
        assertThatExceptionOfType(DynamoDBMappingException.class).isThrownBy(() -> dao.create(null, UUID.randomUUID().toString(), "owner"))
                .withMessage("FoodtruckUser[cognito_id]; null or empty value for primary key");
    }

    @Test
    public void shouldFailCreateUserMissingRangeKey() {
        assertThatExceptionOfType(DynamoDBMappingException.class).isThrownBy(() -> dao.create(UUID.randomUUID().toString(), null, "owner"))
                .withMessage("FoodtruckUser[foodtruck_id]; null or empty value for primary key");
    }
}