package io.github.dbstarll.utils.openai;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.dbstarll.utils.http.client.HttpClientFactory;
import io.github.dbstarll.utils.openai.model.api.ChatCompletion;
import io.github.dbstarll.utils.openai.model.api.File;
import io.github.dbstarll.utils.openai.model.api.Model;
import io.github.dbstarll.utils.openai.model.api.TextCompletion;
import io.github.dbstarll.utils.openai.model.fragment.ChatChoice;
import io.github.dbstarll.utils.openai.model.fragment.Message;
import io.github.dbstarll.utils.openai.model.fragment.TextChoice;
import io.github.dbstarll.utils.openai.model.request.ChatRequest;
import io.github.dbstarll.utils.openai.model.request.CompletionRequest;
import io.github.dbstarll.utils.openai.model.request.UploadFileRequest;
import io.github.dbstarll.utils.openai.model.response.Files;
import io.github.dbstarll.utils.openai.model.response.Models;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.ThrowingConsumer;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.junit.jupiter.api.Assertions.assertTrue;

class OpenAiClientTest extends AbstractOpenAiClientTest {
    private void useClient(final ThrowingConsumer<OpenAiClient> consumer) throws Throwable {
        try (CloseableHttpClient client = new HttpClientFactory().setSocketTimeout(5000)
                .setAutomaticRetries(false).build()) {
            consumer.accept(new OpenAiClient(client, new ObjectMapper(), getOpenAiKey()));
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
            assertEquals("ApiError[message='The model 'unknown' does not exist', type='invalid_request_error', param='model', code='model_not_found']", e.getApiError().toString());
            assertEquals("The model 'unknown' does not exist", e.getApiError().getMessage());
            assertEquals("invalid_request_error", e.getApiError().getType());
            assertEquals("model", e.getApiError().getParam());
            assertEquals("model_not_found", e.getApiError().getCode());
        });
    }

    @Test
    void completions() throws Throwable {
        useClient(c -> {
            final CompletionRequest request = new CompletionRequest();
            request.setModel("text-ada-001");
            request.setPrompt("你好");
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
            final TextCompletion completion = c.completion(request);
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
            final ApiErrorException e = assertThrowsExactly(ApiErrorException.class, () -> c.completion(request));
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
                    Message.user("你好")));
            final ChatCompletion completion = c.chat(request);
            assertEquals("chat.completion", completion.getObject());
            assertEquals("gpt-3.5-turbo-0301", completion.getModel());
            assertEquals(1, completion.getChoices().size());
            final ChatChoice choice = completion.getChoices().get(0);
            assertEquals(0, choice.getIndex());
            assertNotNull(choice.getMessage());
        });
    }

    @Test
    void files() throws Throwable {
        useClient(c -> {
            final Files files = c.files();
            assertEquals("list", files.getObject());
            assertNotNull(files.getData());

            // remove old test file
            for (File file : files.getData()) {
                if ("processed".equals(file.getStatus()) && "test".equals(file.getFilename()) && 152 == file.getBytes()) {
                    System.out.println("delete: " + c.deleteFile(file.getId()));
                }
            }

            final UploadFileRequest request = new UploadFileRequest("test", OpenAiClientTest.class.getResourceAsStream("/test.jsonl"));
            final File file = c.uploadFile(request);
            assertNotNull(file.getId());
            assertNotNull(file.getCreated());
            assertEquals("file", file.getObject());
            assertEquals(UploadFileRequest.PURPOSE_FINE_TUNE, file.getPurpose());
            assertEquals("test", file.getFilename());
            assertEquals(152, file.getBytes());
            assertEquals("uploaded", file.getStatus());
            assertNull(file.getStatusDetails());

            while (true) {
                final File load = c.getFile(file.getId());
                assertEquals(file.getCreated(), load.getCreated());
                if (!"uploaded".equals(load.getStatus())) {
                    break;
                }
                TimeUnit.SECONDS.sleep(1);
            }

            final ApiErrorException e = assertThrowsExactly(ApiErrorException.class, () -> c.getFileContent(file.getId()));
            assertEquals("To help mitigate abuse, downloading of fine-tune training files is disabled for free accounts.", e.getApiError().getMessage());
            assertEquals("invalid_request_error", e.getApiError().getType());

            final File del = c.deleteFile(file.getId());
            assertEquals("file", del.getObject());
            assertEquals(file.getId(), del.getId());
            assertTrue(del.isDeleted());
        });
    }
}
