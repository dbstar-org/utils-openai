package io.github.dbstarll.utils.openai;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.dbstarll.utils.http.client.HttpClientFactory;
import io.github.dbstarll.utils.net.api.StreamFutureCallback;
import io.github.dbstarll.utils.openai.model.request.CompletionRequest;
import org.apache.commons.lang3.StringUtils;
import org.apache.hc.client5.http.impl.async.CloseableHttpAsyncClient;
import org.apache.hc.client5.http.impl.async.HttpAsyncClientBuilder;
import org.apache.hc.core5.concurrent.FutureCallback;
import org.apache.hc.core5.http.HttpHost;
import org.apache.hc.core5.http.URIScheme;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.ThrowingConsumer;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

class OpenAiAsyncClientTest extends AbstractOpenAiClientTest {
    private void useClient(final ThrowingConsumer<OpenAiAsyncClient> consumer) throws Throwable {
        try (CloseableHttpAsyncClient client = new HttpClientFactory().setSocketTimeout(5000)
                .setConnectTimeout(5000).setAutomaticRetries(false).buildAsync(this::proxy)) {
            client.start();
            consumer.accept(new OpenAiAsyncClient(client, new ObjectMapper(), getOpenAiKey()));
        }
    }

    private void proxy(final HttpAsyncClientBuilder builder) {
        final String httpProxyHost = System.getProperty("http.proxyHost");
        final String httpProxyPort = System.getProperty("http.proxyPort");
        if (StringUtils.isNoneBlank(httpProxyHost, httpProxyPort)) {
            builder.setProxy(new HttpHost(URIScheme.HTTP.id, httpProxyHost, Integer.parseInt(httpProxyPort)));
        }
    }

    @Test
    void completion() throws Throwable {
        useClient(c -> {
            final CompletionRequest request = new CompletionRequest();
            request.setModel("text-ada-001");
            request.setPrompt("Say this is a test");

            final MyStreamFutureCallback<EventStream> callback = new MyStreamFutureCallback<>();
            final List<EventStream> completions = c.completion(request, callback).get();
//            assertEquals("text_completion", completion.getObject());
//            assertEquals("text-ada-001", completion.getModel());
//            assertEquals(1, completion.getChoices().size());
//            final TextChoice choice = completion.getChoices().get(0);
//            assertEquals(0, choice.getIndex());
//            assertNotNull(choice.getText());
//            assertNotNull(choice.getLogprobs());
        });
    }

    private static class MyFutureCallback<T> implements FutureCallback<T> {
        private final Object lock = new Object();

        private volatile boolean called;

        private volatile T result;
        private volatile Exception ex;
        private volatile boolean cancelled;

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
            this.cancelled = true;
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

    private static class MyStreamFutureCallback<T> extends MyFutureCallback<List<T>>
            implements StreamFutureCallback<T> {
        private final List<T> results = new ArrayList<>();

        @Override
        public void stream(T result) {
            results.add(result);
        }
    }
}