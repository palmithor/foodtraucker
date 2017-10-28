package com.foodtraucker.serverless.trucks;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.model.*;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.foodtraucker.serverless.ApiGatewayRequest;
import com.foodtraucker.serverless.DynamoDBIntegrationTest;
import com.foodtraucker.serverless.LocalDynamoDBCreationRule;
import com.foodtraucker.serverless.dynamo.DynamoDBUtils;
import com.foodtraucker.serverless.dynamo.TableEnvConstant;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author palmithor
 * @since 26.10.2017.
 */
public class CheckInHandlerTest extends DynamoDBIntegrationTest{

    private CheckinHandler checkinHandler; // class under test

    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();
        checkinHandler = new CheckinHandler(getDynamoDB());
    }

    @Test
    public void test() {
        final ApiGatewayRequest request = new ApiGatewayRequest();
        checkinHandler.handleRequest(request, getMockedContext());
    }

    private Context getMockedContext() {
        final Context contextMock = mock(Context.class);
        final LambdaLogger loggerMock = mock(LambdaLogger.class);
        when(contextMock.getLogger()).thenReturn(loggerMock);
        doAnswer((Answer<Void>) invocationOnMock -> {
            System.out.println(Arrays.toString(invocationOnMock.getArguments()));
            return null;
        }).when(loggerMock).log(anyString());

        return contextMock;
    }
}