import { CognitoUserPool } from 'amazon-cognito-identity-js'
import * as AWS from 'aws-sdk/global'

const region = process.env.AWS_REGION
const identityPoolId = process.env.IDENTITY_POOL_ID
const userPoolId = process.env.USER_POOL_ID

const userPoolUrl = `cognito-idp.${region}.amazonaws.com/${userPoolId}`

AWS.config.update({
  region: region
})

const userPool = new CognitoUserPool({
  UserPoolId: userPoolId,
  ClientId: process.env.COGNITO_CLIENT_ID
})

export default {
  userPool,
  identityPoolId,
  userPoolUrl
}
