'use stict'
const aws = require('aws-sdk');
const dynamodb = new aws.DynamoDB();
const uuidV1 = require('uuid/v1');
const responder = require('responder.js');


const traucksTableName = process.env.TRUCKS_TABLE;

function create(event, context, callback) {
	console.log(`Event: ${JSON.stringify(event)}`);
	console.log(`Context: ${JSON.stringify(context)}`);
	const data = JSON.parse(event.body);

	console.log(`DATA : ${JSON.stringify(data)}`)
	const authorizer = event.requestContext.authorizer;

	if (typeof data === 'undefined' || typeof data.name === 'undefined') {
		console.log(`Please provide a truck name: { name: truckname }`);
		callback(null, responder.badRequest('Please provide a truck name: {name: truckname }'));
	}	else if (authorizer && authorizer.claims.sub) {
		const trauckId = uuidV1();
		const truckOwner = authorizer.claims.sub;
		dynamodb.listTables().promise()
			.then(result => {
				return dynamodb.putItem({
					"TableName": traucksTableName,
					"Item": {
							"id": {
									"S": trauckId
							},
							"owner": {
									"S": truckOwner
							},
							"trauckName": {
								"S": data.name
							}
					}
				})
				.promise();
			})
			.then(result => {
				console.log(`Truck with name ${data.name} created owner ${authorizer.claims.sub}!`)
				callback(null, responder.success(`Truck with name ${data.name} created owner ${authorizer.claims.sub}!`))
			})
			.catch(error => {
				console.log(error);
				callback(null, responder.internalServerError(error));
			});
	} else {
		console.log(`No authorized user found... ${JSON.stringify(authorizer)}`);
		callback(null, responder.notFound('No authorized user found...'));
	}
}

module.exports.create = create;
