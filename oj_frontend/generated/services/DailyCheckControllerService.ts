/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { BaseResponse_List_DailyCheckVO_ } from '../models/BaseResponse_List_DailyCheckVO_';
import type { BaseResponse_long_ } from '../models/BaseResponse_long_';
import type { RecordAddRequest } from '../models/RecordAddRequest';

import type { CancelablePromise } from '../core/CancelablePromise';
import { OpenAPI } from '../core/OpenAPI';
import { request as __request } from '../core/request';

export class DailyCheckControllerService {

    /**
     * addRecord
     * @param recordAddRequest recordAddRequest
     * @returns BaseResponse_long_ OK
     * @returns any Created
     * @throws ApiError
     */
    public static addRecordUsingPost(
recordAddRequest: RecordAddRequest,
): CancelablePromise<BaseResponse_long_ | any> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/api/DailyCheck/add',
            body: recordAddRequest,
            errors: {
                401: `Unauthorized`,
                403: `Forbidden`,
                404: `Not Found`,
            },
        });
    }

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
