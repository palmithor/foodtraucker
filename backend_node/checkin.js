'use strict';

var aws = require('aws-sdk');

var elasticsearch = require('elasticsearch');
var client = new elasticsearch.Client({
    host: 'https://search-foodtraucker-nscubf57faz4unxvtmsuthfphy.eu-central-1.es.amazonaws.com',
    log: 'warning' // todo trace
});
var indexName = 'foodtraucker-' + process.env.NODE_ENV + '-foodtrucks';

var setupIndex = function () {
    return client.indices.getMapping({
        index: indexName
    }).catch(function (err) {
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
};

module.exports.checkinStream = function (event, context) {
    return setupIndex()
        .then(function (res) {
            var records = event['Records'];
            records.forEach(function (record) {
                if (record) {
                    console.log(JSON.stringify(record));
                    if (record.eventName === 'INSERT') {

                    } else if (record.eventName === 'REMOVE') {

                    } else if (record.eventName === 'MODIFY') {

                    }
                }
            })
            context.done(null, event);
        }).catch(function (err) {
            console.log(err);
            context.done(err);
        });
};

/* INSERT record:
{
    "eventID": "2743870374dec7898c07bf055960aa31",
    "eventName": "INSERT",
    "eventVersion": "1.1",
    "eventSource": "aws:dynamodb",
    "awsRegion": "eu-central-1",
    "dynamodb": {
        "ApproximateCreationDateTime": 1510696440,
        "Keys": {
            "foodtruck_id": {
                "S": "28438e9a-ad30-431b-b505-3d9a017d04ac"
            },
            "id": {
                "S": "5e1e59c6-8265-41b7-9ae3-7bbd3c02d060"
            }
        },
        "NewImage": {
            "checkin": {
                "N": "1508267118238"
            },
            "created": {
                "N": "1510696474864"
            },
            "foodtruck_id": {
                "S": "28438e9a-ad30-431b-b505-3d9a017d04ac"
            },
            "lon": {
                "N": "10"
            },
            "id": {
                "S": "5e1e59c6-8265-41b7-9ae3-7bbd3c02d060"
            },
            "checkout": {
                "N": "1508295940189"
            },
            "updated": {
                "N": "1510696474864"
            },
            "lat": {
                "N": "50"
            }
        },
        "SequenceNumber": "29081500000000003982152126",
        "SizeBytes": 243,
        "StreamViewType": "NEW_IMAGE"
    },
    "eventSourceARN": "arn:aws:dynamodb:eu-central-1:174046704947:table/foodtraucker-test-foodtruck-checkins/stream/2017-11-08T15:59:17.027"
}
*/