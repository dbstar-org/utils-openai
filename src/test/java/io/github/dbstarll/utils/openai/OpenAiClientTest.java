package io.github.dbstarll.utils.openai;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.dbstarll.utils.http.client.HttpClientFactory;
import io.github.dbstarll.utils.openai.model.api.ChatCompletion;
import io.github.dbstarll.utils.openai.model.api.Model;
import io.github.dbstarll.utils.openai.model.api.TextCompletion;
import io.github.dbstarll.utils.openai.model.fragment.ChatChoice;
import io.github.dbstarll.utils.openai.model.fragment.Message;
import io.github.dbstarll.utils.openai.model.fragment.TextChoice;
import io.github.dbstarll.utils.openai.model.request.ChatRequest;
import io.github.dbstarll.utils.openai.model.request.CompletionRequest;
import io.github.dbstarll.utils.openai.model.response.Models;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.ThrowingConsumer;

import java.net.Proxy;
import java.net.Proxy.Type;
import java.util.Arrays;

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
            assertEquals("ApiError[message='That model does not exist', type='invalid_request_error', param='model', code='null']", e.getApiError().toString());
            assertEquals("That model does not exist", e.getApiError().getMessage());
            assertEquals("invalid_request_error", e.getApiError().getType());
            assertEquals("model", e.getApiError().getParam());
            assertNull(e.getApiError().getCode());
        });
    }

    @Test
    void completions() throws Throwable {
        useClient(c -> {
            final CompletionRequest request = new CompletionRequest();
            request.setModel("text-ada-001");
            request.setPrompt("Say this is a test");
            request.setLogprobs(2);
            request.setUser("dbstar");
            request.setTemperature(0.2f);
            request.setEcho(true);
            request.setSuffix(null);
            request.setTopP(null);
            request.setN(null);
            request.setStream(null);
            request.setStop(null);
            request.setPresencePenalty(null);
            request.setFrequencyPenalty(null);
            request.setBestOf(null);
            request.setLogitBias(null);
            final TextCompletion completion = c.completions(request);
            assertEquals("text_completion", completion.getObject());
            assertEquals("text-ada-001", completion.getModel());
            assertEquals(1, completion.getChoices().size());
            final TextChoice choice = completion.getChoices().get(0);
            assertEquals(0, choice.getIndex());
            assertNotNull(choice.getText());
            assertNotNull(choice.getLogprobs());
        });
    }

    @Test
    void completionsNoModel() throws Throwable {
        useClient(c -> {
            final CompletionRequest request = new CompletionRequest();
            final ApiErrorException e = assertThrowsExactly(ApiErrorException.class, () -> c.completions(request));
            assertEquals("ApiError[message='you must provide a model parameter', type='invalid_request_error', param='null', code='null']", e.getApiError().toString());
            assertEquals("you must provide a model parameter", e.getApiError().getMessage());
            assertEquals("invalid_request_error", e.getApiError().getType());
            assertNull(e.getApiError().getParam());
            assertNull(e.getApiError().getCode());
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
                    Message.user("Say this is a test")));
            final ChatCompletion completion = c.chat(request);
            assertEquals("chat.completion", completion.getObject());
            assertEquals("gpt-3.5-turbo-0301", completion.getModel());
            assertEquals(1, completion.getChoices().size());
            final ChatChoice choice = completion.getChoices().get(0);
            assertEquals(0, choice.getIndex());
            assertNotNull(choice.getMessage());
        });
    }
}
