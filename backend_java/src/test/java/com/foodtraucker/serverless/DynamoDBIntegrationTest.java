package com.foodtraucker.serverless;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.foodtraucker.serverless.dynamo.DynamoDBTestUtils;
import com.foodtraucker.serverless.dynamo.TableEnvConstant;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * @author palmithor
 * @since 2017-10-28
 */
@RunWith(JUnit4.class)
public abstract class DynamoDBIntegrationTest {

    @ClassRule public static final LocalDynamoDBCreationRule DYNAMO_DB = new LocalDynamoDBCreationRule();

    public static DynamoDBTestUtils dynamoDBTestUtils;

    static {
        System.setProperty(TableEnvConstant.TRUCKS_TABLE, "FOODTRUCKS");
        System.setProperty(TableEnvConstant.CHECKINS_TABLE, "CHECKINS");
        System.setProperty(TableEnvConstant.FOODTRUCK_USERS_TABLE, "FOODTRUCK_USERS");
        System.setProperty(TableEnvConstant.FOODTRUCK_MENUS_TABLE, "FOODTRUCK_MENUS");
    }

    private static AmazonDynamoDB dynamoDB;

    @BeforeClass
    public static void beforeClass() throws Exception {
        dynamoDB = DYNAMO_DB.getAmazonDynamoDB();
        dynamoDBTestUtils = new DynamoDBTestUtils(dynamoDB);
        dynamoDBTestUtils.createSchema();
    }

    @AfterClass
    public static void afterClass() throws Exception {
        dynamoDB.shutdown();
    }

    public AmazonDynamoDB getDynamoDB() {
        return dynamoDB;
    }
}
