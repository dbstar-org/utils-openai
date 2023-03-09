package io.github.dbstarll.utils.openai.model.fragment;

import java.util.StringJoiner;

public final class ChatChoice extends Choice {
    private Message message;

    /**
     * get message.
     *
     * @return message
     */
    public Message getMessage() {
        return message;
    }

    /**
     * set message.
     *
     * @param message message
     */
    public void setMessage(final Message message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", super.toString() + "[", "]")
                .add("message='" + getMessage() + "'")
                .toString();
    }
}
