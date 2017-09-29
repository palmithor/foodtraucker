package com.foodtraucker.serverless.users;

import java.util.Collections;
import java.util.Map;

import org.apache.log4j.Logger;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class UpdateUserHandler implements RequestHandler<Map<String, Object>, UserResponse> {

	private static final Logger LOG = Logger.getLogger(UpdateUserHandler.class);

	@Override
	public UserResponse handleRequest(final  Map<String, Object> input, final Context context) {
		LOG.info("received: " + input);
		return new UserResponse("palmithor@gmail.com");
	}
}
