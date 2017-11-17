package com.foodtraucker.serverless.menus;

import com.foodtraucker.serverless.ApiGatewayProxyResponse;
import com.foodtraucker.serverless.ApiGatewayRequest;
import com.foodtraucker.serverless.DynamoDBIntegrationTest;
import com.foodtraucker.serverless.trucks.FoodtruckUserDao;
import com.foodtraucker.serverless.utils.JsonUtils;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author armannds
 * @since 2017-11-16
 */

public class AddFoodtruckMenuHandlerTest extends DynamoDBIntegrationTest {

    private AddFoodtruckMenuHandler addFoodtruckMenuHandler;
    private AddMenuRequest addMenuRequest;
    private ApiGatewayRequest request;
    private String foodtruckId;
    private String cognitoId;

    @Before
    public void setUp() throws Exception {
        addFoodtruckMenuHandler = new AddFoodtruckMenuHandler(getDynamoDB());
        addMenuRequest = createAddMenuRequest();
        request = new ApiGatewayRequest();
        foodtruckId = UUID.randomUUID().toString();
        cognitoId = UUID.randomUUID().toString();
        new FoodtruckUserDao(getDynamoDBMapper()).create(cognitoId, foodtruckId, "owner");
    }

    private AddMenuRequest createAddMenuRequest() {
        return AddMenuRequest.createBuilder()
                .name("Fish and Chips")
                .description("Fish and Chips, daahhh!")
                .build();
    }

    @Test
    public void shouldAddFoodtruckMenu() {
        request.setPathParameters(getPathParams(foodtruckId));
        request.setRequestContext(createRequestContext(cognitoId));
        request.setBody(JsonUtils.toJson(addMenuRequest));

        final ApiGatewayProxyResponse response = addFoodtruckMenuHandler.handleRequest(request, getMockedContext());
        assertThat(response.getStatusCode()).isEqualTo(200);
        assertThat(response.getBody()).isNotNull();
    }

    @Test
    public void shouldFailFoodtruckNotFound() {
        request.setPathParameters(getPathParams(cognitoId));
        request.setRequestContext(createRequestContext(UUID.randomUUID().toString()));
        request.setBody(JsonUtils.toJson(addMenuRequest));

        final ApiGatewayProxyResponse response = addFoodtruckMenuHandler.handleRequest(request, getMockedContext());
        assertThat(response.getStatusCode()).isEqualTo(403);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody()).contains("Forbidden");
    }

    @Test
    public void shouldFailNoPermission() {
        request.setPathParameters(getPathParams(UUID.randomUUID().toString()));
        request.setRequestContext(createRequestContext(cognitoId));
        request.setBody(JsonUtils.toJson(request));
        final ApiGatewayProxyResponse response = addFoodtruckMenuHandler.handleRequest(request, getMockedContext());
        assertThat(response.getStatusCode()).isEqualTo(403);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody()).contains("Forbidden");
    }
}
