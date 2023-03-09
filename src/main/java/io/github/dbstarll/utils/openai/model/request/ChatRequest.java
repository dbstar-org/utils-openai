package io.github.dbstarll.utils.openai.model.request;

import io.github.dbstarll.utils.openai.model.fragment.Message;

import java.util.List;

public final class ChatRequest extends AbstractCompletionRequest {
    private List<Message> messages;

    /**
     * The messages to generate chat completions for, in the chat format.
     *
     * @return messages
     */
    public List<Message> getMessages() {
        return messages;
    }

    /**
     * set messages.
     *
     * @param messages messages
     */
    public void setMessages(final List<Message> messages) {
        this.messages = messages;
    }
}
