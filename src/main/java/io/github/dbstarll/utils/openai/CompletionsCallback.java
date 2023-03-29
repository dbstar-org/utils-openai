package io.github.dbstarll.utils.openai;

import io.github.dbstarll.utils.json.jackson.EventStreamFutureCallback;
import io.github.dbstarll.utils.net.api.StreamFutureCallback;
import io.github.dbstarll.utils.net.api.index.EventStream;
import org.apache.hc.core5.concurrent.CallbackContribution;

final class CompletionsCallback<T> extends CallbackContribution<Void> implements EventStreamFutureCallback<T> {
    private final StreamFutureCallback<T> callback;

    CompletionsCallback(final StreamFutureCallback<T> callback) {
        super(callback);
        this.callback = callback;
    }

    @Override
    public void stream(final T result) {
        callback.stream(result);
    }

    @Override
    public void completed(final Void result) {
        callback.completed(result);
    }

    @Override
    public boolean ignore(final EventStream eventStream) {
        return "[DONE]".equals(eventStream.getData());
    }
}
