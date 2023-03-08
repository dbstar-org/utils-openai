package io.github.dbstarll.utils.openai.model.response;

import io.github.dbstarll.utils.openai.model.api.Model;

import java.util.List;

public final class Models {
    private String object;
    private List<Model> data;

    /**
     * 获得object.
     *
     * @return object
     */
    public String getObject() {
        return object;
    }

    /**
     * 设置object.
     *
     * @param object object
     */
    public void setObject(final String object) {
        this.object = object;
    }

    /**
     * 获得data.
     *
     * @return data
     */
    public List<Model> getData() {
        return data;
    }

    /**
     * 设置data.
     *
     * @param data data
     */
    public void setData(final List<Model> data) {
        this.data = data;
    }
}
