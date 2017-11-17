package com.foodtraucker.serverless.trucks;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.foodtraucker.serverless.ApiGatewayProxyResponse;
import com.foodtraucker.serverless.ApiGatewayRequest;
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
public class CheckinHandler implements RequestHandler<ApiGatewayRequest, ApiGatewayProxyResponse> {

    private static final Logger logger = Logger.getLogger(CheckinHandler.class);
    ;
    private final Validator validator;

    private AmazonDynamoDB dynamoDB;
    private ObjectMapper objectMapper;

    public CheckinHandler() {
        this.dynamoDB = AmazonDynamoDBClientBuilder.defaultClient();
        this.objectMapper = new ObjectMapper();
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        this.validator = factory.getValidator();

    }

    public CheckinHandler(final AmazonDynamoDB dynamoDB) {
        this.dynamoDB = dynamoDB;
        this.objectMapper = new ObjectMapper();
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        this.validator = factory.getValidator();
    }

    @Override
    public ApiGatewayProxyResponse handleRequest(final ApiGatewayRequest request, final Context context) {
        final CheckinRequest checkinRequest = JsonUtils.fromJson(request.getBody(), CheckinRequest.class).orElse(new CheckinRequest());
        final Optional<Set<ConstraintViolation<CheckinRequest>>> validationErrorOptional = validateRequest(checkinRequest);
        // Request is invalid
        if (validationErrorOptional.isPresent()) {
            final ErrorResponse errorResponse = ErrorResponse.badRequest().addFieldViolation(validationErrorOptional.get()).build();
            return new ApiGatewayProxyResponse(errorResponse.getStatusCode(), JsonUtils.toJson(errorResponse));
        }
        final DynamoDBMapper dynamoDBMapper = new DynamoDBMapper(dynamoDB);
        final FoodtruckUserDao foodtruckUserDao = new FoodtruckUserDao(dynamoDBMapper);
        final String foodtruckId = request.getPathParameters().get(PathParamConstant.ID);
        final List<FoodtruckUser> foodtrucksByUser = foodtruckUserDao.findByCognitoId(request.getRequestContext().getAuthorizer().getClaims().getSub());
        final Optional<FoodtruckUser> foodtruckUser = foodtrucksByUser.stream()
                .filter(row -> row.getFoodtruckId().equals(foodtruckId)).findFirst();

        // User does not have access to this foodtruck
        if (!foodtruckUser.isPresent()) {
            final ErrorResponse errorResponse = ErrorResponse.forbidden().build();
            return new ApiGatewayProxyResponse(errorResponse.getStatusCode(), JsonUtils.toJson(errorResponse));
        }

        final CheckinDao checkinDao = new CheckinDao(dynamoDBMapper);
        final Checkin checkin = checkinDao.create(foodtruckId, checkinRequest);
        return new ApiGatewayProxyResponse(200, JsonUtils.toJson(checkin));
    }

    private Optional<Set<ConstraintViolation<CheckinRequest>>> validateRequest(final CheckinRequest checkinRequest) {
        Set<ConstraintViolation<CheckinRequest>> violations = validator.validate(checkinRequest);
        if (violations.size() > 0) {
            return Optional.of(violations);
        }
        return Optional.empty();
    }

}
