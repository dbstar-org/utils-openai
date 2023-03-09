package io.github.dbstarll.utils.openai.model.fragment;

import java.io.Serializable;
import java.util.StringJoiner;

public final class Choice implements Serializable {
    private int index;
    private String text;
    private String finishReason;
    private Logprobs logprobs;

    /**
     * get index.
     *
     * @return index
     */
    public int getIndex() {
        return index;
    }

    /**
     * set index.
     *
     * @param index index
     */
    public void setIndex(final int index) {
        this.index = index;
    }

    /**
     * get text.
     *
     * @return text
     */
    public String getText() {
        return text;
    }

    /**
     * set text.
     *
     * @param text text
     */
    public void setText(final String text) {
        this.text = text;
    }

    /**
     * get finishReason.
     *
     * @return finishReason
     */
    public String getFinishReason() {
        return finishReason;
    }

    /**
     * set finishReason.
     *
     * @param finishReason finishReason
     */
    public void setFinishReason(final String finishReason) {
        this.finishReason = finishReason;
    }

    /**
     * get logprobs.
     *
     * @return logprobs
     */
    public Logprobs getLogprobs() {
        return logprobs;
    }

    /**
     * set logprobs.
     *
     * @param logprobs logprobs
     */
    public void setLogprobs(final Logprobs logprobs) {
        this.logprobs = logprobs;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Choice.class.getSimpleName() + "[", "]")
                .add("index=" + getIndex())
                .add("text='" + getText() + "'")
                .add("finishReason='" + getFinishReason() + "'")
                .add("logprobs='" + getLogprobs() + "'")
                .toString();
    }
}
