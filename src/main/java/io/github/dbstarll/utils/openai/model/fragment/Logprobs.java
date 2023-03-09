package io.github.dbstarll.utils.openai.model.fragment;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

public final class Logprobs implements Serializable {
    private String[] tokens;
    private double[] tokenLogprobs;
    private List<Map<String, Double>> topLogprobs;
    private int[] textOffset;

    /**
     * get tokens.
     *
     * @return tokens
     */
    public String[] getTokens() {
        return tokens;
    }

    /**
     * set tokens.
     *
     * @param tokens tokens
     */
    public void setTokens(final String[] tokens) {
        this.tokens = tokens;
    }

    /**
     * get tokenLogprobs.
     *
     * @return tokenLogprobs
     */
    public double[] getTokenLogprobs() {
        return tokenLogprobs;
    }

    /**
     * set tokenLogprobs.
     *
     * @param tokenLogprobs tokenLogprobs
     */
    public void setTokenLogprobs(final double[] tokenLogprobs) {
        this.tokenLogprobs = tokenLogprobs;
    }

    /**
     * get topLogprobs.
     *
     * @return topLogprobs
     */
    public List<Map<String, Double>> getTopLogprobs() {
        return topLogprobs;
    }

    /**
     * set topLogprobs.
     *
     * @param topLogprobs topLogprobs
     */
    public void setTopLogprobs(final List<Map<String, Double>> topLogprobs) {
        this.topLogprobs = topLogprobs;
    }

    /**
     * get textOffset.
     *
     * @return textOffset
     */
    public int[] getTextOffset() {
        return textOffset;
    }

    /**
     * set textOffset.
     *
     * @param textOffset textOffset
     */
    public void setTextOffset(final int[] textOffset) {
        this.textOffset = textOffset;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Logprobs.class.getSimpleName() + "[", "]")
                .add("tokens=" + getTokens().length)
                .add("tokenLogprobs=" + getTokenLogprobs().length)
                .add("topLogprobs=" + getTopLogprobs().size())
                .add("textOffset=" + getTextOffset().length)
                .toString();
    }
}
