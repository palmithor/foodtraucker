'use strict';

//const uuid = require('uuid');
//const dynamodb = require('./dynamodb');

//const userTable = process.env.USERS_TABLE;
//const traucksTable = process.env.TRAUCKS_TABLE;


module.exports.signup = (event, context, callback) => {
    console.log('hello world');
    callback(null, {message: "OK"})
};
