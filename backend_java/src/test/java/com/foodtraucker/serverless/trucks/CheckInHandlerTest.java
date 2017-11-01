package com.foodtraucker.serverless.trucks;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.model.*;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.foodtraucker.serverless.*;
import com.foodtraucker.serverless.common.PathParamConstant;
import com.foodtraucker.serverless.dynamo.DynamoDBTestUtils;
import com.foodtraucker.serverless.dynamo.DynamoDBUtils;
import com.foodtraucker.serverless.dynamo.TableEnvConstant;
import com.foodtraucker.serverless.utils.JsonUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.logging.Logger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author palmithor
 * @since 26.10.2017.
 */
public class CheckInHandlerTest extends DynamoDBIntegrationTest {

    private CheckinHandler checkinHandler; // class under test
    private String foodtruckId;
    private String cognitoId;


    @Before
    public void setUp() throws Exception {
        checkinHandler = new CheckinHandler(getDynamoDB());

        foodtruckId = UUID.randomUUID().toString();
        cognitoId = UUID.randomUUID().toString();
        new FoodtruckUserDao(DynamoDBIntegrationTest.dynamoDBTestUtils.getDynamoDBMapper())
                .create(cognitoId, foodtruckId, "owner");
    }

    @Test
    public void shouldCheckin() {
        final CheckinRequest checkinRequest = getValidCheckinRequest();
        final ApiGatewayRequest request = new ApiGatewayRequest();
        request.setPathParameters(getPathParams(foodtruckId));
        request.setRequestContext(createRequestContext(cognitoId));
        request.setBody(JsonUtils.toJson(checkinRequest));
        final ApiGatewayProxyResponse apiGatewayResponse = checkinHandler.handleRequest(request, getMockedContext());
        assertThat(apiGatewayResponse.getStatusCode()).isEqualTo(200);
        assertThat(apiGatewayResponse.getBody()).isNotNull();
    }

    @Test
    public void shouldFailCheckinFoodtruckNotFound() {
        final CheckinRequest checkinRequest = getValidCheckinRequest();
        final ApiGatewayRequest request = new ApiGatewayRequest();
        request.setPathParameters(getPathParams(cognitoId));
        request.setRequestContext(createRequestContext(UUID.randomUUID().toString()));
        request.setBody(JsonUtils.toJson(checkinRequest));
        final ApiGatewayProxyResponse apiGatewayResponse = checkinHandler.handleRequest(request, getMockedContext());
        assertThat(apiGatewayResponse.getStatusCode()).isEqualTo(403);
        assertThat(apiGatewayResponse.getBody()).isNotNull();
        assertThat(apiGatewayResponse.getBody()).contains("Forbidden");
    }

    @Test
    public void shouldFailCheckinNoPermission() {
        final CheckinRequest checkinRequest = getValidCheckinRequest();
        final ApiGatewayRequest request = new ApiGatewayRequest();
        request.setPathParameters(getPathParams(UUID.randomUUID().toString()));
        request.setRequestContext(createRequestContext(cognitoId));
        request.setBody(JsonUtils.toJson(checkinRequest));
        final ApiGatewayProxyResponse apiGatewayResponse = checkinHandler.handleRequest(request, getMockedContext());
        assertThat(apiGatewayResponse.getStatusCode()).isEqualTo(403);
        assertThat(apiGatewayResponse.getBody()).isNotNull();
        assertThat(apiGatewayResponse.getBody()).contains("Forbidden");
    }

    private RequestContext createRequestContext(final String cognitoId) {
        final RequestContext requestContext = new RequestContext();
        final Authorizer authorizer = new Authorizer();
        final Claims claims = new Claims();
        claims.setSub(cognitoId);
        authorizer.setClaims(claims);
        requestContext.setAuthorizer(authorizer);
        return requestContext;
    }

    private Map<String, String> getPathParams(final String foodtruckId) {
        Map<String, String> pathParams = new HashMap<>();
        pathParams.put(PathParamConstant.ID, foodtruckId);
        return pathParams;
    }

    private Context getMockedContext() {
        return mock(Context.class);
    }

    public CheckinRequest getValidCheckinRequest() {
        return CheckinRequest.createBuilder()
                .checkin(Instant.now().minus(2, ChronoUnit.HOURS).toEpochMilli())
                .checkout(Instant.now().plus(2, ChronoUnit.HOURS).toEpochMilli())
                .lat(50f)
                .lon(10f)
                .build();
    }
}