# Cognito
Cognito User Pool and Cognito Federated Identity need to be created before deploying to a new environment.
The reason for it is that I am not certain right now, how to reference the arn for a newly created user pool 
when creating the API Gateway Authorizer.

In order to allow users to upload directly from the frontend to a S3 bucket, they need to have a federated identity.