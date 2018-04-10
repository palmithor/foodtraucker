'use strict';
const merge = require('webpack-merge');
const devEnv = require('./dev.env');

module.exports = merge(devEnv, {
  NODE_ENV: '"testing"',
  BASE_URL: '"https://gtbqp4gxz8.execute-api.eu-central-1.amazonaws.com/test"',
  AWS_REGION: '"eu-central-1"',
  USER_POOL_ID: '"eu-central-1_RxnIPPZui"',
  USER_POOL_CLIENT_ID: '"vqjsnpiojek70s79t4m1kt8li"',
  IDENTITY_POOL_ID: '"eu-central-1:a88c79e9-c177-4e7d-95ef-925fcdf027df"',
  ES_CHECKIN_INDEX: '"foodtraucker-test-foodtrucks"',
});
