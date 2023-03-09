package io.github.dbstarll.utils.openai.model.fragment;

import java.util.StringJoiner;

public final class TextChoice extends Choice {
    private String text;
    private Logprobs logprobs;

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
        return new StringJoiner(", ", super.toString() + "[", "]")
                .add("text='" + getText() + "'")
                .add("logprobs='" + getLogprobs() + "'")
                .toString();
    }
}
