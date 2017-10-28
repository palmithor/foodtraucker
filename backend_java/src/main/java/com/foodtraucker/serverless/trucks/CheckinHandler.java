package com.foodtraucker.serverless.trucks;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.foodtraucker.serverless.ApiGatewayProxyResponse;
import com.foodtraucker.serverless.ApiGatewayRequest;
import com.foodtraucker.serverless.dynamo.DynamoDBUtils;


import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

/**
 * @author palmithor
 * @since 26.10.2017.
 */
public class CheckinHandler implements RequestHandler<ApiGatewayRequest, ApiGatewayProxyResponse> {

    private AmazonDynamoDB dynamoDB;

    public CheckinHandler() {
        this.dynamoDB = AmazonDynamoDBClientBuilder.defaultClient();
    }

    public CheckinHandler(final AmazonDynamoDB dynamoDB) {
        this.dynamoDB = dynamoDB;
    }

    @Override
    public ApiGatewayProxyResponse handleRequest(final ApiGatewayRequest request, final Context context) {

        final LambdaLogger logger = context.getLogger();

        logger.log("");

        Checkin checkin = new Checkin();
        checkin.setFoodtruckId(UUID.randomUUID().toString());
        checkin.setCheckin(new Date().getTime());
        checkin.setCheckout(new Date().getTime());
        checkin.setLat(50.00);
        checkin.setLon(10.00);
        checkin.setCreated(new Date().getTime());


        DynamoDBMapper dynamoDBMapper = new DynamoDBMapper(dynamoDB);
        dynamoDBMapper.save(checkin, DynamoDBUtils.getCheckinsMapperConfig());


        return new ApiGatewayProxyResponse(200, new HashMap<>(), request.getBody());
    }

}
