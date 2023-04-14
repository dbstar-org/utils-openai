package io.github.dbstarll.utils.openai.model.api;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.github.dbstarll.utils.openai.model.DateConverter;

import java.util.Date;
import java.util.StringJoiner;

public final class FineTuneEvent extends Base {
    private String level;
    private String message;

    /**
     * 设置创建时间.
     *
     * @param createdAt 创建时间
     */
    @JsonDeserialize(converter = DateConverter.class)
    public void setCreatedAt(final Date createdAt) {
        setCreated(createdAt);
    }

    /**
     * get level.
     *
     * @return level
     */
    public String getLevel() {
        return level;
    }

    /**
     * set level.
     *
     * @param level level
     */
    public void setLevel(final String level) {
        this.level = level;
    }

    /**
     * get message.
     *
     * @return message
     */
    public String getMessage() {
        return message;
    }

    /**
     * set message.
     *
     * @param message message
     */
    public void setMessage(final String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", super.toString() + "[", "]")
                .add("level='" + getLevel() + "'")
                .add("message='" + getMessage() + "'")
                .toString();
    }
}
