package com.foodtraucker.serverless.users;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.foodtraucker.serverless.ApiGatewayProxyResponse;
import com.foodtraucker.serverless.ApiGatewayRequest;
import org.apache.log4j.Logger;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class UpdateUserHandler implements RequestHandler<ApiGatewayRequest, ApiGatewayProxyResponse> {

	private static final Logger LOG = Logger.getLogger(UpdateUserHandler.class);

	@Override
	public ApiGatewayProxyResponse handleRequest(final ApiGatewayRequest request, final Context context) {
		LOG.info("received: " + request.toString());
		return new ApiGatewayProxyResponse(200,  new HashMap<>(), "{}");
	}
}
