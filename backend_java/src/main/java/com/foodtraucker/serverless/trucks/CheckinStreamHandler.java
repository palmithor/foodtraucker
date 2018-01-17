package com.foodtraucker.serverless.trucks;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.DynamodbEvent;

/**
 * @author palmithor
 * @since 26.10.2017.
 */
public class CheckinStreamHandler implements RequestHandler<DynamodbEvent, Void> {

    @Override
    public Void handleRequest(final DynamodbEvent dynamodbEvent, final Context context) {

        for (DynamodbEvent.DynamodbStreamRecord record : dynamodbEvent.getRecords()) {

            if (record == null) {
                continue;
            }

            // Your code here
        }

        return null;
    }
}
