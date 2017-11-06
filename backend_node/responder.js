module.exports = {
	success: (result) => {
			return {
					statusCode: 200,
					headers: {
							"Access-Control-Allow-Origin" : "*", // Required for CORS support to work
							"Access-Control-Allow-Credentials" : true // Required for cookies, authorization headers with HTTPS
					},
					body: JSON.stringify(result),
			}
	},
	badRequest: (msg) => {
		return {
			statusCode: 400,
			headers: {
				"Access-Control-Allow-Origin" : "*", // Required for CORS support to work
				"Access-Control-Allow-Credentials" : true // Required for cookies, authorization headers with HTTPS
			},
			body: JSON.stringify({
				statusCode: 400,
				error: 'Bad Request',
				message: JSON.stringify(msg),
			})
		}
	},
	notFound: (msg) => {
		return {
			statusCode: 403,
			headers: {
				"Access-Control-Allow-Origin" : "*", // Required for CORS support to work
				"Access-Control-Allow-Credentials" : true // Required for cookies, authorization headers with HTTPS
			},
			body: JSON.stringify({
				statusCode: 403,
				error: 'Not Found',
				message: JSON.stringify(msg),
			})
		}
	},
	internalServerError: (msg) => {
			return {
					statusCode: 500,
					headers: {
							"Access-Control-Allow-Origin" : "*", // Required for CORS support to work
							"Access-Control-Allow-Credentials" : true // Required for cookies, authorization headers with HTTPS
					},
					body: JSON.stringify({
							statusCode: 500,
							error: 'Internal Server Error',
							internalError: JSON.stringify(msg),
					}),
			}
	}
}
