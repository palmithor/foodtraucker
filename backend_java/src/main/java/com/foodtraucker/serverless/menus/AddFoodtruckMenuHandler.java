package com.foodtraucker.serverless.menus;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.foodtraucker.serverless.ApiGatewayProxyResponse;
import com.foodtraucker.serverless.ApiGatewayRequest;
import com.foodtraucker.serverless.common.AbstractHandler;
import com.foodtraucker.serverless.trucks.FoodtruckUser;
import com.foodtraucker.serverless.utils.JsonUtils;
import org.apache.log4j.Logger;

import javax.validation.ConstraintViolation;
import java.util.Optional;
import java.util.Set;

/**
 * @author armannds
 * @since 2017-11-12
 */
public class AddFoodtruckMenuHandler extends AbstractHandler implements RequestHandler<ApiGatewayRequest, ApiGatewayProxyResponse> {

    private static final Logger LOG = Logger.getLogger(AddFoodtruckMenuHandler.class);

    private AmazonDynamoDB dynamoDB;

    public AddFoodtruckMenuHandler() {
        super();
        this.dynamoDB = AmazonDynamoDBClientBuilder.defaultClient();
    }

    public AddFoodtruckMenuHandler(final AmazonDynamoDB dynamoDB) {
        super();
        this.dynamoDB = dynamoDB;
    }

    @Override
    public ApiGatewayProxyResponse handleRequest(ApiGatewayRequest request, Context context) {
        final AddMenuRequest addMenuRequest = JsonUtils.fromJson(request.getBody(), AddMenuRequest.class).orElse(new AddMenuRequest());
        final Optional<Set<ConstraintViolation<AddMenuRequest>>> validationErrors = validateRequest(addMenuRequest);

        if (validationErrors.isPresent()) {
            return createBadRequestResponse(validationErrors.get());
        }

        final DynamoDBMapper dynamoDBMapper = new DynamoDBMapper(dynamoDB);
        final Optional<FoodtruckUser> foodtruckUser = getFoodtruckUser(request, dynamoDBMapper);

        // User does not have access to this foodtruck
        if (!foodtruckUser.isPresent()) {
            return createForbiddenResponse();
        }

        final String foodtruckId = foodtruckUser.get().getFoodtruckId();
        final FoodtruckMenuDao foodtruckMenuDao = new FoodtruckMenuDao(dynamoDBMapper);
        final FoodtruckMenu foodtruckMenu = foodtruckMenuDao.create(foodtruckId, addMenuRequest);

        return createOkResponse(foodtruckMenu);
    }
}
