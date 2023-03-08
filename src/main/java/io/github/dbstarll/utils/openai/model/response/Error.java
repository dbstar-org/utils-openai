package io.github.dbstarll.utils.openai.model.response;

import java.io.Serializable;
import java.util.StringJoiner;

public class Error implements Serializable {
    private String message;
    private String type;
    private String param;
    private String code;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
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
