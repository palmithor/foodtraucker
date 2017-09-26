'use strict';

//const uuid = require('uuid');
//const dynamodb = require('./dynamodb');

module.exports.signup = (event, context, callback) => {
    console.log('hello world');
    callback(null, {message: "OK"})
};