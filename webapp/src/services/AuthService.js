import {CognitoUserAttribute, CognitoUser, AuthenticationDetails} from 'amazon-cognito-identity-js'
import AwsProfile from './AwsProfile'

function login (email, password) {
  const user = new CognitoUser({
    Username: email,
    Pool: AwsProfile.userPool
  })

  const authenticationData = {
    Username: email,
    Password: password
  }

  const authenticationDetails = new AuthenticationDetails(authenticationData)

  return new Promise((resolve, reject) =>
    user.authenticateUser(authenticationDetails, {
      onSuccess: result => resolve(result),
      onFailure: err => reject(err)
    })
  )
}

function signUp (email, password) {
  const attributeList = [
    new CognitoUserAttribute({
      Name: 'email',
      Value: email
    })
  ]

  return new Promise((resolve, reject) =>
    AwsProfile.userPool.signUp(email, password, attributeList, null, {
      onSuccess: result => resolve(result),
      onFailure: err => reject(err)
    })
  )
}

function forgotPassword (email) {
  const cognitoUser = new CognitoUser({
    Pool: AwsProfile.userPool,
    Username: email
  })

  return new Promise((resolve, reject) => cognitoUser.forgotPassword({
    onSuccess: result => resolve(result),
    onFailure: err => reject(err)
  }))
}

function confirmPassword (email, code, newPassword) {
  const cognitoUser = new CognitoUser({
    Pool: AwsProfile.userPool,
    Username: email
  })

  return new Promise((resolve, reject) => {
    cognitoUser.confirmPassword(code, newPassword, {
      onSuccess: result => resolve(result),
      onFailure: err => reject(err)
    })
  })
}

export default {
  signUp,
  login,
  forgotPassword,
  confirmPassword
}
