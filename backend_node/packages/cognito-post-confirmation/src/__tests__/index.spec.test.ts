import { CognitoUserPoolEvent } from 'aws-lambda';
import { promiseHandler } from '../';

it('fails due to invalid trigger source', async () => {
    const triggerSource = 'PreSignUp_SignUp';
    await expect(
        promiseHandler({
            triggerSource,
        } as CognitoUserPoolEvent),
    ).rejects.toThrow(`Unexpected trigger source: ${triggerSource}`);
});
