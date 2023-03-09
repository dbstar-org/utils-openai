package io.github.dbstarll.utils.openai;

import io.github.dbstarll.utils.net.api.ApiProtocolException;
import io.github.dbstarll.utils.openai.model.response.ApiError;

public final class ApiErrorException extends ApiProtocolException {
    private final ApiError apiError;

    /**
     * 从ApiError来构造.
     *
     * @param apiError ApiError
     */
    public ApiErrorException(final ApiError apiError) {
        super(apiError.getMessage(), null);
        this.apiError = apiError;
    }

    /**
     * 获得ApiError信息.
     *
     * @return ApiError
     */
    public ApiError getApiError() {
        return apiError;
    }
}
