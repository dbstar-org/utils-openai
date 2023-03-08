package io.github.dbstarll.utils.openai;

import io.github.dbstarll.utils.net.api.ApiProtocolException;
import io.github.dbstarll.utils.openai.model.response.Error;

public final class ApiErrorException extends ApiProtocolException {
    private final Error error;

    /**
     * 从Error来构造.
     *
     * @param error Error
     */
    public ApiErrorException(final Error error) {
        super(error.getMessage(), null);
        this.error = error;
    }

    /**
     * 获得Error信息.
     *
     * @return Error
     */
    public Error getError() {
        return error;
    }
}
