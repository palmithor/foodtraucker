import {createCallbackHandler} from '@foodtraucker/utils';
import {CognitoUserPoolEvent} from 'aws-lambda';
import {DynamoDB} from 'aws-sdk';

const dynamodb = new DynamoDB();

export const promiseHandler = async (event: CognitoUserPoolEvent) => {
    const userTableName = process.env.USERS_TABLE;
    const datetime = new Date().getTime().toString();

    if (event.triggerSource === 'PostConfirmation_ConfirmSignUp') {
        console.log(`Event: ${JSON.stringify(event)}`);

        await dynamodb.putItem({
            "TableName": userTableName,
            "Item": {
                "cognito_id": {
                    "S": event.request.userAttributes.sub
                },
                "email": {
                    "S": event.request.userAttributes.email
                },
                "updated": {
                    "N": datetime
                },
                "created": {
                    "N": datetime
                }
            }
        }).promise();

        return event;
    } else if (event.triggerSource.startsWith('PostConfirmation_')) {
        return event;
    } else {
        throw new Error(`Unexpected trigger source: ${event.triggerSource}`);
    }
};

export const handler = createCallbackHandler(promiseHandler);