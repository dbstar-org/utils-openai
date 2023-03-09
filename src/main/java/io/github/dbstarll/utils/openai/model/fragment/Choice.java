package io.github.dbstarll.utils.openai.model.fragment;

import java.io.Serializable;
import java.util.StringJoiner;

public abstract class Choice implements Serializable {
    private int index;
    private String finishReason;

    /**
     * get index.
     *
     * @return index
     */
    public final int getIndex() {
        return index;
    }

    /**
     * set index.
     *
     * @param index index
     */
    public final void setIndex(final int index) {
        this.index = index;
    }

    /**
     * get finishReason.
     *
     * @return finishReason
     */
    public final String getFinishReason() {
        return finishReason;
    }

    /**
     * set finishReason.
     *
     * @param finishReason finishReason
     */
    public final void setFinishReason(final String finishReason) {
        this.finishReason = finishReason;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", getClass().getSimpleName() + "[", "]")
                .add("index=" + getIndex())
                .add("finishReason='" + getFinishReason() + "'")
                .toString();
    }
}
