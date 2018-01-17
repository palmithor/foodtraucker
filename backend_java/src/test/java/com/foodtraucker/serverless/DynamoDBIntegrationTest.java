package com.foodtraucker.serverless;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.lambda.runtime.Context;
import com.foodtraucker.serverless.common.PathParamConstant;
import com.foodtraucker.serverless.dynamo.DynamoDBTestUtils;
import com.foodtraucker.serverless.dynamo.TableEnvConstant;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.mock;

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

    public DynamoDBMapper getDynamoDBMapper() {
        return DynamoDBIntegrationTest.dynamoDBTestUtils.getDynamoDBMapper();
    }

    public Context getMockedContext() {
        return mock(Context.class);
    }

    public RequestContext createRequestContext(final String cognitoId) {
        final RequestContext requestContext = new RequestContext();
        final Authorizer authorizer = new Authorizer();
        final Claims claims = new Claims();
        claims.setSub(cognitoId);
        authorizer.setClaims(claims);
        requestContext.setAuthorizer(authorizer);
        return requestContext;
    }

    public Map<String, String> getPathParams(final String foodtruckId) {
        Map<String, String> pathParams = new HashMap<>();
        pathParams.put(PathParamConstant.ID, foodtruckId);
        return pathParams;
    }
}
