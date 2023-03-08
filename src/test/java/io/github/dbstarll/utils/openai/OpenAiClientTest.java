package io.github.dbstarll.utils.openai;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.dbstarll.utils.http.client.HttpClientFactory;
import io.github.dbstarll.utils.openai.model.response.Models;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.ThrowingConsumer;

import java.net.Proxy.Type;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class OpenAiClientTest {
    private void useClient(final ThrowingConsumer<OpenAiClient> consumer) throws Throwable {
        try (CloseableHttpClient client = proxy(new HttpClientFactory()).setAutomaticRetries(false).build()) {
            consumer.accept(new OpenAiClient(client, new ObjectMapper(), System.getProperty("OPEN-AI-KEY")));
        }
    }

    private HttpClientFactory proxy(final HttpClientFactory factory) {
        final String socksProxyHost = System.getProperty("socksProxyHost");
        final String socksProxyPort = System.getProperty("socksProxyPort");
        if (StringUtils.isNoneBlank(socksProxyHost, socksProxyPort)) {
            return factory.setProxy(HttpClientFactory.proxy(Type.SOCKS, socksProxyHost, Integer.parseInt(socksProxyPort)))
                    .setResolveFromProxy(true);
        } else {
            return factory;
        }
    }

    @Test
    void models() throws Throwable {
        useClient(c -> {
            final Models models = c.models();
            assertNotNull(models);
            assertEquals("list", models.getObject());
            models.getData().forEach(System.out::println);
        });
    }
}