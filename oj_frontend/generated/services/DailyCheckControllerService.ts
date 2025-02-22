/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { BaseResponse_List_DailyCheckVO_ } from '../models/BaseResponse_List_DailyCheckVO_';

import type { CancelablePromise } from '../core/CancelablePromise';
import { OpenAPI } from '../core/OpenAPI';
import { request as __request } from '../core/request';

export class DailyCheckControllerService {

    /**
     * getUserRoutine
     * @returns BaseResponse_List_DailyCheckVO_ OK
     * @throws ApiError
     */
    public static getUserRoutineUsingGet(): CancelablePromise<BaseResponse_List_DailyCheckVO_> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/api/DailyCheck/get',
            errors: {
                401: `Unauthorized`,
                403: `Forbidden`,
                404: `Not Found`,
            },
        });
    }

}
