package com.foodtraucker.serverless.users;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.foodtraucker.serverless.ApiGatewayResponse;
import org.apache.log4j.Logger;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class UpdateUserHandler implements RequestHandler<UserRequest, ApiGatewayResponse> {

	private static final Logger LOG = Logger.getLogger(UpdateUserHandler.class);

	@Override
	public ApiGatewayResponse handleRequest(final UserRequest request, final Context context) {
		LOG.info("received: " + request.toString());
		return new ApiGatewayResponse(200, "", new HashMap<>(), false);
	}
}
