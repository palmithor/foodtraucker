import { Context } from 'aws-lambda';

export type APIGatewayEventCallbackHandler<Event, Result extends IAPIGatewayResponse> = (
    event: Event,
    context: Context,
    callback: (error: Error | null, result?: Result) => void,
) => void;

export type APIGatewayEventPromiseHandler<Event, Result extends IAPIGatewayResponse> = (
    event: Event,
    context: Context,
) => Result | PromiseLike<Result>;

export interface IAPIGatewayResponse {
    body: string,
    headers?: any,
    statusCode: number
}

// Convert a promise-returning handler to one that takes a callback
export function createAPIGatewayEventCallbackHandler<Event, Result extends IAPIGatewayResponse>(
    promiseHandler: APIGatewayEventPromiseHandler<Event, Result>,
): APIGatewayEventCallbackHandler<Event, Result> {
    return async (event, context, callback) => {
        try {
            const result = await promiseHandler(event, context);
            callback(null, result);
        } catch (error) {
            callback(error);
        }
    };
}

// Convert a callback function to one that returns a promise
export function createAPIGatewayEventPromiseHandler<Event, Result extends IAPIGatewayResponse>(
    callbackHandler: APIGatewayEventCallbackHandler<Event, Result>,
): APIGatewayEventPromiseHandler<Event, Result> {
    return (event, context) =>
        new Promise((resolve, reject) => {
            try {
                callbackHandler(event, context, (error, result) => {
                    if (error) {
                        reject(error);
                    } else {
                        resolve(result!);
                    }
                });
            } catch (error) {
                reject(error);
            }
        });
}
