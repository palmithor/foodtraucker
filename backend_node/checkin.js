'use strict';

var aws = require('aws-sdk');

var elasticsearch = require('elasticsearch');
var client = new elasticsearch.Client({
    host: 'https://search-foodtraucker-nscubf57faz4unxvtmsuthfphy.eu-central-1.es.amazonaws.com',
    log: 'warning' // todo trace
});
var indexName = 'foodtraucker-' + process.env.NODE_ENV + '-foodtrucks';

module.exports.streamCheckin = function (event, context) {
    return setupIndex()
        .then((result) => {
            var records = event['Records']
            records.forEach((record) => {
                if (record) {
                    console.log(record)
                    if (record.eventName === 'INSERT') {} else if (record.eventName === 'REMOVE') {

                    } else if (record.eventName === 'MODIFY') {

                    }
                }
            })
        }).catch((err) => {
            console.log(err);

        });
}

var setupIndex = function () {
    return client.indices.getMapping({
            index: indexName
        })
        .then((result) => result)
        .catch((err) => {
            return client.indices.create({
                index: indexName,
                body: {
                    "mappings": {
                        "checkins": {
                            "properties": {
                                "coordinate": {
                                    "type": "geo_point"
                                },
                                "checkin": {
                                    "type": "date",
                                    "format": "epoch_millis"
                                },
                                "checkout": {
                                    "type": "date",
                                    "format": "epoch_millis"
                                }
                            }
                        }
                    }
                }
            })
        });
}