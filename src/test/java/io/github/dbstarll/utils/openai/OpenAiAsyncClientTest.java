package io.github.dbstarll.utils.openai;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.dbstarll.utils.http.client.HttpClientFactory;
import io.github.dbstarll.utils.net.api.StreamFutureCallback;
import io.github.dbstarll.utils.openai.model.api.ChatCompletionChunk;
import io.github.dbstarll.utils.openai.model.api.TextCompletion;
import io.github.dbstarll.utils.openai.model.fragment.ChatChunkChoice;
import io.github.dbstarll.utils.openai.model.fragment.Message;
import io.github.dbstarll.utils.openai.model.fragment.TextChoice;
import io.github.dbstarll.utils.openai.model.request.ChatRequest;
import io.github.dbstarll.utils.openai.model.request.CompletionRequest;
import io.github.dbstarll.utils.openai.model.response.ApiError;
import org.apache.hc.client5.http.impl.async.CloseableHttpAsyncClient;
import org.apache.hc.core5.concurrent.FutureCallback;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.ThrowingConsumer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

class OpenAiAsyncClientTest extends AbstractOpenAiClientTest {
    private void useClient(final ThrowingConsumer<OpenAiAsyncClient> consumer) throws Throwable {
        try (CloseableHttpAsyncClient client = new HttpClientFactory().setSocketTimeout(5000)
                .setConnectTimeout(5000).setAutomaticRetries(false).buildAsync()) {
            client.start();
            consumer.accept(new OpenAiAsyncClient(client, new ObjectMapper(), getOpenAiKey()));
        }
    }

    @Test
    void completionException() throws Throwable {
        useClient(c -> {
            final CompletionRequest request = new CompletionRequest();
            final MyStreamFutureCallback<TextCompletion> callback = new MyStreamFutureCallback<>();
            final ExecutionException e = assertThrowsExactly(ExecutionException.class, () -> c.completion(request, callback).get());
            assertNotNull(e.getCause());
            assertSame(IOException.class, e.getCause().getClass());
            assertNotNull(e.getCause().getCause());
            assertSame(ApiErrorException.class, e.getCause().getCause().getClass());
            final ApiError apiError = ((ApiErrorException) e.getCause().getCause()).getApiError();
            assertEquals("you must provide a model parameter", apiError.getMessage());
            assertEquals("invalid_request_error", apiError.getType());
            assertNull(apiError.getParam());
            assertNull(apiError.getCode());
            callback.assertException(e.getCause());
        });
    }

    @Test
    void completion() throws Throwable {
        useClient(c -> {
            final CompletionRequest request = new CompletionRequest();
            request.setModel("text-ada-001");
            request.setPrompt("你好");

            final MyStreamFutureCallback<TextCompletion> callback = new MyStreamFutureCallback<>();
            assertNull(c.completion(request, callback).get());
            final StringBuilder builder = new StringBuilder();
            callback.results.forEach(completion -> {
                assertEquals("text_completion", completion.getObject());
                assertEquals("text-ada-001", completion.getModel());
                assertEquals(1, completion.getChoices().size());
                final TextChoice choice = completion.getChoices().get(0);
                assertEquals(0, choice.getIndex());
                assertNotNull(choice.getText());
                builder.append(choice.getText());
            });
            System.out.println(builder.toString());
        });
    }

    @Test
    void chat() throws Throwable {
        useClient(c -> {
            final ChatRequest request = new ChatRequest();
            request.setModel("gpt-3.5-turbo");
            request.setMaxTokens(32);
            request.setMessages(Arrays.asList(
                    Message.system("You are a helpful assistant."),
                    Message.user("Hello!"),
                    Message.assistant("Nice to meet you!"),
                    Message.user("你好")));
            final MyStreamFutureCallback<ChatCompletionChunk> callback = new MyStreamFutureCallback<>();
            assertNull(c.chat(request, callback).get());
            final StringBuilder builder = new StringBuilder();
            callback.results.forEach(completion -> {
                assertEquals("chat.completion.chunk", completion.getObject());
                assertEquals("gpt-3.5-turbo-0301", completion.getModel());
                assertEquals(1, completion.getChoices().size());
                final ChatChunkChoice choice = completion.getChoices().get(0);
                assertEquals(0, choice.getIndex());
                final Message delta = choice.getDelta();
                assertNotNull(delta);
                if (delta.getRole() != null) {
                    builder.append('[').append(delta.getRole()).append(']');
                }
                if (delta.getContent() != null) {
                    builder.append(delta.getContent());
                }
            });
            System.out.println(builder.toString());
        });
    }

    private static class MyFutureCallback<T> implements FutureCallback<T> {
        private final Object lock = new Object();

        private volatile boolean called;

        private volatile T result;
        private volatile Exception ex;

        @Override
        public void completed(T result) {
            this.result = result;
            synchronized (lock) {
                this.called = true;
                lock.notify();
            }
        }

        @Override
        public void failed(Exception ex) {
            this.ex = ex;
            synchronized (lock) {
                this.called = true;
                lock.notify();
            }
        }

        @Override
        public void cancelled() {
            synchronized (lock) {
                this.called = true;
                lock.notify();
            }
        }

        public void assertResult(T result) {
            waitCall();
            assertEquals(result, this.result);
        }

        public void assertException(Throwable ex) {
            waitCall();
            assertSame(ex, this.ex);
        }

        private void waitCall() {
            while (!called) {
                synchronized (lock) {
                    try {
                        lock.wait(100);
                    } catch (InterruptedException e) {
                        // ignore
                    }
                }
            }
        }
    }

    private static class MyStreamFutureCallback<T> extends MyFutureCallback<Void> implements StreamFutureCallback<T> {
        private final List<T> results = new ArrayList<>();

        @Override
        public void stream(T result) {
            results.add(result);
        }
    }
}