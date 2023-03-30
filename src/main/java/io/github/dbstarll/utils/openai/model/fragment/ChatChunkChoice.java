package io.github.dbstarll.utils.openai.model.fragment;

import java.util.StringJoiner;

public final class ChatChunkChoice extends Choice {
    private Message delta;

    /**
     * get delta.
     *
     * @return delta
     */
    public Message getDelta() {
        return delta;
    }

    /**
     * set delta.
     *
     * @param delta delta
     */
    public void setDelta(final Message delta) {
        this.delta = delta;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", super.toString() + "[", "]")
                .add("delta='" + getDelta() + "'")
                .toString();
    }
}
