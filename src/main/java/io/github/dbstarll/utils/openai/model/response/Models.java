package io.github.dbstarll.utils.openai.model.response;

import io.github.dbstarll.utils.openai.model.api.Model;

import java.util.List;
import java.util.StringJoiner;

public final class Models {
    private String object;
    private List<Model> data;

    public String getObject() {
        return object;
    }

    public void setObject(final String object) {
        this.object = object;
    }

    public List<Model> getData() {
        return data;
    }

    public void setData(final List<Model> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Models.class.getSimpleName() + "[", "]")
                .add("object='" + object + "'")
                .add("data=" + data)
                .toString();
    }
}
