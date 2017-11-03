import { CognitoUserPool } from 'amazon-cognito-identity-js'
import * as AWS from 'aws-sdk/global'

const REGION = process.env.AWS_REGION
// const IDENTITY_POOL_ID = process.env.IDENTITY_POOL_ID

AWS.config.update({
  region: REGION
})

const userPool = new CognitoUserPool({
  UserPoolId: process.env.USER_POOL_ID,
  ClientId: process.env.COGNITO_CLIENT_ID
})

export default {
  userPool
}
