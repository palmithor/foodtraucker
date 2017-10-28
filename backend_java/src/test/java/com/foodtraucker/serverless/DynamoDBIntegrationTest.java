package com.foodtraucker.serverless;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.foodtraucker.serverless.dynamo.DynamoDBTestUtils;
import com.foodtraucker.serverless.dynamo.TableEnvConstant;
import com.foodtraucker.serverless.trucks.CheckinHandler;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * @author palmithor
 * @since 2017-10-28
 */
@RunWith(JUnit4.class)
public abstract class DynamoDBIntegrationTest {

    @ClassRule public static final LocalDynamoDBCreationRule DYNAMO_DB = new LocalDynamoDBCreationRule();

    private DynamoDBTestUtils dynamoDBTestUtils;

    static {
        System.setProperty(TableEnvConstant.TRUCKS_TABLE, "TRUCKS");
        System.setProperty(TableEnvConstant.CHECKINS_TABLE, "CHECKINS");
    }

    private AmazonDynamoDB dynamoDB;

    @Before
    public void setUp() throws Exception {
        dynamoDB = DYNAMO_DB.getAmazonDynamoDB();
        dynamoDBTestUtils = new DynamoDBTestUtils(dynamoDB);
        dynamoDBTestUtils.createSchema();
    }

    @After
    public void tearDown() throws Exception {
        dynamoDB.shutdown();
    }

    public AmazonDynamoDB getDynamoDB() {
        return dynamoDB;
    }
}
