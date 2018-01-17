package com.foodtraucker.serverless.common;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.foodtraucker.serverless.ApiGatewayProxyResponse;
import com.foodtraucker.serverless.ApiGatewayRequest;
import com.foodtraucker.serverless.trucks.FoodtruckUser;
import com.foodtraucker.serverless.trucks.FoodtruckUserDao;
import com.foodtraucker.serverless.utils.JsonUtils;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * @author armannds
 * @since 2017-11-14
 */
public abstract class AbstractHandler {

    private final Validator validator;

    public AbstractHandler() {
        this.validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    protected <T> Optional<Set<ConstraintViolation<T>>> validateRequest(final T request) {
        Set<ConstraintViolation<T>> violations = validator.validate(request);
        if (violations.size() > 0) {
            return Optional.of(violations);
        }
        return Optional.empty();
    }

    protected <T> ApiGatewayProxyResponse createBadRequestResponse(Set<ConstraintViolation<T>> validationErrors) {
        final ErrorResponse errorResponse = ErrorResponse.badRequest().addFieldViolation(validationErrors).build();
        return new ApiGatewayProxyResponse(errorResponse.getStatusCode(), JsonUtils.toJson(errorResponse));
    }

    protected Optional<FoodtruckUser> getFoodtruckUser(ApiGatewayRequest request, DynamoDBMapper dynamoDBMapper) {
        final FoodtruckUserDao foodtruckMenuDao = new FoodtruckUserDao(dynamoDBMapper);
        final String foodtruckId = request.getPathParameters().get(PathParamConstant.ID);
        final List<FoodtruckUser> foodtrucksByUser = foodtruckMenuDao.findByCognitoId(request.getRequestContext().getAuthorizer().getClaims().getSub());
        return foodtrucksByUser.stream().filter(row -> row.getFoodtruckId().equals(foodtruckId)).findFirst();
    }

    protected ApiGatewayProxyResponse createForbiddenResponse() {
        final ErrorResponse errorResponse = ErrorResponse.forbidden().build();
        return new ApiGatewayProxyResponse(errorResponse.getStatusCode(), JsonUtils.toJson(errorResponse));
    }

    protected  <T> ApiGatewayProxyResponse createOkResponse(T objectToReturn) {
        return new ApiGatewayProxyResponse(200, JsonUtils.toJson(objectToReturn));
    }
}
