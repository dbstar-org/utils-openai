package io.github.dbstarll.utils.openai.model.api;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.github.dbstarll.utils.openai.model.DateConverter;

import java.io.Serializable;
import java.util.Date;
import java.util.StringJoiner;

public abstract class Base implements Serializable {
    private String id;
    private String object;
    @JsonDeserialize(converter = DateConverter.class)
    private Date created;

    /**
     * 获得id.
     *
     * @return id
     */
    public final String getId() {
        return id;
    }

    /**
     * 设置id.
     *
     * @param id id
     */
    public final void setId(final String id) {
        this.id = id;
    }

    /**
     * 获得object.
     *
     * @return object
     */
    public final String getObject() {
        return object;
    }

    /**
     * 设置object.
     *
     * @param object object
     */
    public final void setObject(final String object) {
        this.object = object;
    }

    /**
     * 获得创建时间.
     *
     * @return 创建时间
     */
    public final Date getCreated() {
        return created;
    }

    /**
     * 设置创建时间.
     *
     * @param created 创建时间
     */
    public final void setCreated(final Date created) {
        this.created = created;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", getClass().getSimpleName() + "[", "]")
                .add("id='" + getId() + "'")
                .add("object='" + getObject() + "'")
                .add("created=" + getCreated())
                .toString();
    }
}
