import { CognitoUserPoolEvent, Context } from 'aws-lambda';

export type CallbackHandler<Event, Result extends CognitoUserPoolEvent> = (
    event: Event,
    context: Context,
    callback: (error: Error | null, result?: Result) => void,
) => void;

export type PromiseHandler<Event, Result extends CognitoUserPoolEvent> = (
    event: Event,
    context: Context,
) => Result | PromiseLike<Result>;

// Convert a promise-returning handler to one that takes a callback
export function createCallbackHandler<Event, Result extends CognitoUserPoolEvent>(
    promiseHandler: PromiseHandler<Event, Result>,
): CallbackHandler<Event, Result> {
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
export function createPromiseHandler<Event, Result extends CognitoUserPoolEvent>(
    callbackHandler: CallbackHandler<Event, Result>,
): PromiseHandler<Event, Result> {
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
