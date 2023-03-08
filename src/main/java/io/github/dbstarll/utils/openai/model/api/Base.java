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

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public String getObject() {
        return object;
    }

    public void setObject(final String object) {
        this.object = object;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(final Date created) {
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
