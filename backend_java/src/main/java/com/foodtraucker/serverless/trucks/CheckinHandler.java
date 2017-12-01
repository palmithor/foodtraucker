package com.foodtraucker.serverless.trucks;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.foodtraucker.serverless.ApiGatewayProxyResponse;
import com.foodtraucker.serverless.ApiGatewayRequest;
import com.foodtraucker.serverless.common.AbstractHandler;
import com.foodtraucker.serverless.common.ErrorResponse;

import com.foodtraucker.serverless.common.PathParamConstant;
import com.foodtraucker.serverless.utils.JsonUtils;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

import javax.validation.*;
import java.io.IOException;
import java.util.*;

/**
 * @author palmithor
 * @since 26.10.2017.
 */
public class CheckinHandler extends AbstractHandler implements RequestHandler<ApiGatewayRequest, ApiGatewayProxyResponse> {

    private static final Logger logger = Logger.getLogger(CheckinHandler.class);
    ;

    private AmazonDynamoDB dynamoDB;

    public CheckinHandler() {
        super();
        this.dynamoDB = AmazonDynamoDBClientBuilder.defaultClient();
    }

    public CheckinHandler(final AmazonDynamoDB dynamoDB) {
        super();
        this.dynamoDB = dynamoDB;
    }

    @Override
    public ApiGatewayProxyResponse handleRequest(final ApiGatewayRequest request, final Context context) {
        final CheckinRequest checkinRequest = JsonUtils.fromJson(request.getBody(), CheckinRequest.class).orElse(new CheckinRequest());
        final Optional<Set<ConstraintViolation<CheckinRequest>>> validationErrors = validateRequest(checkinRequest);
        // Request is invalid
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
        final CheckinDao checkinDao = new CheckinDao(dynamoDBMapper);
        final Checkin checkin = checkinDao.create(foodtruckId, checkinRequest);

        return createOkResponse(checkin);
    }
}
