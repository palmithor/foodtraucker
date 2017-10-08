'use strict';

var aws = require('aws-sdk');

//const uuid = require('uuid');
//const dynamodb = require('./dynamodb');

//const userTable = process.env.USERS_TABLE;
//const traucksTable = process.env.TRAUCKS_TABLE;


module.exports.signup = function(event, context) {
    console.log('Event: ' + JSON.stringify(event));
    context.done(null, event);
};