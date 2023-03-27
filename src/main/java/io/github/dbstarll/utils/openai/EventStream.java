package io.github.dbstarll.utils.openai;

public class EventStream {
    private final String content;

    EventStream(final String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return content;
    }
}
