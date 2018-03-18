import { CognitoUserPool, CognitoUserAttribute, CognitoUser, AuthenticationDetails } from 'amazon-cognito-identity-js';
// import { CognitoIdentityCredentials } from 'aws-sdk';

const cognitoUserPool = new CognitoUserPool({
  UserPoolId: process.env.USER_POOL_ID,
  ClientId: process.env.USER_POOL_CLIENT_ID,
});

function constructUser(cognitoUser, session) {
  return {
    username: cognitoUser.getUsername(),
    isConfirmed: true,
    tokens: {
      IdToken: session.getIdToken().getJwtToken(),
      AccessToken: session.getAccessToken().getJwtToken(),
      RefreshToken: session.getRefreshToken().getToken(),
    },
  };
}

export default {
  getCurrentUser() {
    return new Promise((resolve, reject) => {
      const cognitoUser = cognitoUserPool.getCurrentUser();
      if (!cognitoUser) {
        reject({ message: 'Can\'t retrieve the current user' });
      } else {
        cognitoUser.getSession((err, session) => {
          if (err) reject(err);
          if (session) resolve(constructUser(cognitoUser, session));
        });
      }
    });
  },
  authenticateUser(credentials) {
    // payload: { email: , password: }
    const authDetails = new AuthenticationDetails({
      Username: credentials.username,
      Password: credentials.password,
    });

    const cognitoUser = new CognitoUser({
      Pool: cognitoUserPool,
      Username: credentials.username,
    });

    return new Promise((resolve, reject) => cognitoUser.authenticateUser(authDetails, {
      onFailure: err => reject(err),
      onSuccess: session => resolve(constructUser(cognitoUser, session)),
    }));
  },
  signUp(credentials) {
    const attributeList = [
      new CognitoUserAttribute({
        Name: 'email',
        Value: credentials.username,
      }),
    ];

    return new Promise((resolve, reject) => {
      cognitoUserPool.signUp(credentials.username, credentials.password, attributeList, null,
        (err, result) => {
          if (err) reject(err);
          if (result) resolve(result);
        });
    });
  },
  signOut() {
    return this.getCurrentUser()
      .then((currentUser) => {
        const cognitoUser = new CognitoUser({
          Pool: cognitoUserPool,
          Username: currentUser.username,
        });
        cognitoUser.signOut();
      });
  },
};
