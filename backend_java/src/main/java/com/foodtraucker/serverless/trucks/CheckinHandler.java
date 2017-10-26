package com.foodtraucker.serverless.trucks;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.foodtraucker.serverless.ApiGatewayResponse;

import java.util.Map;

/**
 * @author palmithor
 * @since 26.10.2017.
 */
public class CheckinHandler implements RequestHandler<CheckinRequest, ApiGatewayResponse> {

    @Override
    public ApiGatewayResponse handleRequest(final CheckinRequest request, final Context context) {
        return null;
    }

}
