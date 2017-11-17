package com.foodtraucker.serverless.trucks;

import com.foodtraucker.serverless.ApiGatewayProxyResponse;
import com.foodtraucker.serverless.ApiGatewayRequest;
import com.foodtraucker.serverless.DynamoDBIntegrationTest;
import com.foodtraucker.serverless.utils.JsonUtils;
import org.junit.Before;
import org.junit.Test;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

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
        new FoodtruckUserDao(getDynamoDBMapper()).create(cognitoId, foodtruckId, "owner");
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

    public CheckinRequest getValidCheckinRequest() {
        return CheckinRequest.createBuilder()
                .checkin(Instant.now().minus(2, ChronoUnit.HOURS).toEpochMilli())
                .checkout(Instant.now().plus(2, ChronoUnit.HOURS).toEpochMilli())
                .lat(50f)
                .lon(10f)
                .build();
    }
}