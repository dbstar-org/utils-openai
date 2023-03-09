package io.github.dbstarll.utils.openai.model.fragment;

import java.io.Serializable;
import java.util.StringJoiner;

public final class Message implements Serializable {
    private String role;
    private String content;

    /**
     * 默认的构造函数.
     */
    public Message() {
        // default
    }

    private Message(final String role, final String content) {
        this.role = role;
        this.content = content;
    }

    /**
     * get role.
     *
     * @return role
     */
    public String getRole() {
        return role;
    }

    /**
     * set role.
     *
     * @param role role
     */
    public void setRole(final String role) {
        this.role = role;
    }

    /**
     * get content.
     *
     * @return content
     */
    public String getContent() {
        return content;
    }

    /**
     * set content.
     *
     * @param content content
     */
    public void setContent(final String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Message.class.getSimpleName() + "[", "]")
                .add("role='" + role + "'")
                .add("content='" + content + "'")
                .toString();
    }

    /**
     * build a system Message.
     *
     * @param content content
     * @return a system Message
     */
    public static Message system(final String content) {
        return new Message("system", content);
    }

    /**
     * build a user Message.
     *
     * @param content content
     * @return a user Message
     */
    public static Message user(final String content) {
        return new Message("user", content);
    }

    /**
     * build an assistant Message.
     *
     * @param content content
     * @return an assistant Message
     */
    public static Message assistant(final String content) {
        return new Message("assistant", content);
    }
}
