import { Context, DynamoDBStreamEvent } from 'aws-lambda';

export type StreamEventCallbackHandler<Event, Result extends DynamoDBStreamEvent> = (
    event: Event,
    context: Context,
    callback: (error: Error | null, result?: Result) => void,
) => void;

export type StreamEventPromiseHandler<Event, Result extends DynamoDBStreamEvent> = (
    event: Event,
    context: Context,
) => Result | PromiseLike<Result>;

// Convert a promise-returning handler to one that takes a callback
export function createStreamEventCallbackHandler<Event, Result extends DynamoDBStreamEvent>(
    promiseHandler: StreamEventPromiseHandler<Event, Result>,
): StreamEventCallbackHandler<Event, Result> {
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
export function createStreamEventPromiseHandler<Event, Result extends DynamoDBStreamEvent>(
    callbackHandler: StreamEventCallbackHandler<Event, Result>,
): StreamEventPromiseHandler<Event, Result> {
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
