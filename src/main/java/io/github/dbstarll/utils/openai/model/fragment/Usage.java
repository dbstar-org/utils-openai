package io.github.dbstarll.utils.openai.model.fragment;

import java.io.Serializable;
import java.util.StringJoiner;

public final class Usage implements Serializable {
    private int promptTokens;
    private int completionTokens;
    private int totalTokens;

    /**
     * get promptTokens.
     *
     * @return promptTokens
     */
    public int getPromptTokens() {
        return promptTokens;
    }

    /**
     * set promptTokens.
     *
     * @param promptTokens promptTokens
     */
    public void setPromptTokens(final int promptTokens) {
        this.promptTokens = promptTokens;
    }

    /**
     * get completionTokens.
     *
     * @return completionTokens
     */
    public int getCompletionTokens() {
        return completionTokens;
    }

    /**
     * set completionTokens.
     *
     * @param completionTokens completionTokens
     */
    public void setCompletionTokens(final int completionTokens) {
        this.completionTokens = completionTokens;
    }

    /**
     * get totalTokens.
     *
     * @return totalTokens
     */
    public int getTotalTokens() {
        return totalTokens;
    }

    /**
     * set totalTokens.
     *
     * @param totalTokens totalTokens
     */
    public void setTotalTokens(final int totalTokens) {
        this.totalTokens = totalTokens;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Usage.class.getSimpleName() + "[", "]")
                .add("promptTokens=" + getPromptTokens())
                .add("completionTokens=" + getCompletionTokens())
                .add("totalTokens=" + getTotalTokens())
                .toString();
    }
}
