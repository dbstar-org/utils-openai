package io.github.dbstarll.utils.openai.model.response;

import java.io.Serializable;
import java.util.StringJoiner;

public final class Error implements Serializable {
    private String message;
    private String type;
    private String param;
    private String code;

    /**
     * 获得错误信息.
     *
     * @return 错误信息
     */
    public String getMessage() {
        return message;
    }

    /**
     * 设置错误信息.
     *
     * @param message 错误信息
     */
    public void setMessage(final String message) {
        this.message = message;
    }

    /**
     * 获得错误类型.
     *
     * @return 错误类型
     */
    public String getType() {
        return type;
    }

    /**
     * 设置错误类型.
     *
     * @param type 错误类型
     */
    public void setType(final String type) {
        this.type = type;
    }

    /**
     * 获得引起错误的参数.
     *
     * @return 引起错误的参数
     */
    public String getParam() {
        return param;
    }

    /**
     * 设置引起错误的参数.
     *
     * @param param 引起错误的参数
     */
    public void setParam(final String param) {
        this.param = param;
    }

    /**
     * 获得错误code.
     *
     * @return 错误code
     */
    public String getCode() {
        return code;
    }

    /**
     * 设置错误code.
     *
     * @param code 错误code
     */
    public void setCode(final String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Error.class.getSimpleName() + "[", "]")
                .add("message='" + message + "'")
                .add("type='" + type + "'")
                .add("param='" + param + "'")
                .add("code='" + code + "'")
                .toString();
    }
}
