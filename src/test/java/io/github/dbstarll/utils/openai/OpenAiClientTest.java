package io.github.dbstarll.utils.openai;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.dbstarll.utils.http.client.HttpClientFactory;
import io.github.dbstarll.utils.openai.model.api.Model;
import io.github.dbstarll.utils.openai.model.response.Models;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.ThrowingConsumer;

import java.net.Proxy;
import java.net.Proxy.Type;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

class OpenAiClientTest {
    private static final String TOKEN_KEY = "OPEN_AI_KEY";

    private void useClient(final ThrowingConsumer<OpenAiClient> consumer) throws Throwable {
        try (CloseableHttpClient client = proxy(new HttpClientFactory()).setAutomaticRetries(false).build()) {
            consumer.accept(new OpenAiClient(client, new ObjectMapper(), getOpenAiKey()));
        }
    }

    private HttpClientFactory proxy(final HttpClientFactory factory) {
        final String socksProxyHost = System.getProperty("socksProxyHost");
        final String socksProxyPort = System.getProperty("socksProxyPort");
        if (StringUtils.isNoneBlank(socksProxyHost, socksProxyPort)) {
            final Proxy proxy = HttpClientFactory.proxy(Type.SOCKS, socksProxyHost, Integer.parseInt(socksProxyPort));
            return factory.setProxy(proxy).setResolveFromProxy(true);
        } else {
            return factory;
        }
    }

    private String getOpenAiKey() {
        final String keyFromProperty = System.getProperty(TOKEN_KEY);
        if (StringUtils.isNotBlank(keyFromProperty)) {
            return keyFromProperty;
        }

        final String opts = System.getenv("MAVEN_OPTS");
        if (StringUtils.isNotBlank(opts)) {
            for (String opt : StringUtils.split(opts)) {
                if (opt.startsWith("-D" + TOKEN_KEY + "=")) {
                    return opt.substring(3 + TOKEN_KEY.length());
                }
            }
        }

        return null;
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

    @Test
    void model() throws Throwable {
        useClient(c -> {
            final Model model = c.model("gpt-3.5-turbo");
            assertNotNull(model);
            assertEquals("gpt-3.5-turbo", model.getId());
            assertEquals("model", model.getObject());
        });
    }

    @Test
    void modelUnknown() throws Throwable {
        useClient(c -> {
            final ApiErrorException e = assertThrowsExactly(ApiErrorException.class, () -> c.model("unknown"));
            assertEquals("That model does not exist", e.getError().getMessage());
            assertEquals("invalid_request_error", e.getError().getType());
            assertEquals("model", e.getError().getParam());
            assertNull(e.getError().getCode());
        });
    }
}
