package com.foodtraucker.serverless.trucks;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.foodtraucker.serverless.ApiGatewayProxyResponse;
import com.foodtraucker.serverless.ApiGatewayRequest;
import com.foodtraucker.serverless.users.UpdateUserHandler;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 * @author palmithor
 * @since 26.10.2017.
 */
public class CheckinHandler implements RequestHandler<ApiGatewayRequest, ApiGatewayProxyResponse> {

    private static final Logger LOG = Logger.getLogger(UpdateUserHandler.class);

    @Override
    public ApiGatewayProxyResponse handleRequest(final ApiGatewayRequest request, final Context context) {
        LOG.info(request.getBody());
        LOG.warn(request.getBody());
        System.out.println(request.getBody());

        return new ApiGatewayProxyResponse(200, new HashMap<>(), "{}");
    }

}
