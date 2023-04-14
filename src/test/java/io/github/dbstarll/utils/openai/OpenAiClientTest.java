package io.github.dbstarll.utils.openai;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.dbstarll.utils.http.client.HttpClientFactory;
import io.github.dbstarll.utils.net.api.ApiException;
import io.github.dbstarll.utils.openai.model.api.ChatCompletion;
import io.github.dbstarll.utils.openai.model.api.File;
import io.github.dbstarll.utils.openai.model.api.FineTune;
import io.github.dbstarll.utils.openai.model.api.FineTuneEvent;
import io.github.dbstarll.utils.openai.model.api.Model;
import io.github.dbstarll.utils.openai.model.api.TextCompletion;
import io.github.dbstarll.utils.openai.model.fragment.ChatChoice;
import io.github.dbstarll.utils.openai.model.fragment.Message;
import io.github.dbstarll.utils.openai.model.fragment.TextChoice;
import io.github.dbstarll.utils.openai.model.request.ChatRequest;
import io.github.dbstarll.utils.openai.model.request.CompletionRequest;
import io.github.dbstarll.utils.openai.model.request.CreateFineTuneRequest;
import io.github.dbstarll.utils.openai.model.request.UploadFileRequest;
import io.github.dbstarll.utils.openai.model.response.Files;
import io.github.dbstarll.utils.openai.model.response.FineTuneEvents;
import io.github.dbstarll.utils.openai.model.response.FineTunes;
import io.github.dbstarll.utils.openai.model.response.Models;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.ThrowingConsumer;

import java.io.IOException;
import java.time.Duration;
import java.util.Arrays;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.junit.jupiter.api.Assertions.assertTrue;

class OpenAiClientTest extends AbstractOpenAiClientTest {
    private void useClient(final ThrowingConsumer<OpenAiClient> consumer) throws Throwable {
        try (CloseableHttpClient client = new HttpClientFactory().setSocketTimeout(15000)
                .setAutomaticRetries(false).build()) {
            consumer.accept(new OpenAiClient(client, new ObjectMapper(), getOpenAiKey()));
        }
    }

    @Test
    void models() throws Throwable {
        useClient(c -> {
            final Models models = c.models().list();
            assertNotNull(models);
            assertEquals("list", models.getObject());
            models.getData().forEach(System.out::println);
        });
    }

    @Test
    void model() throws Throwable {
        useClient(c -> {
            final Model model = c.models().get("gpt-3.5-turbo");
            assertNotNull(model);
            assertEquals("gpt-3.5-turbo", model.getId());
            assertEquals("model", model.getObject());
        });
    }

    @Test
    void modelUnknown() throws Throwable {
        useClient(c -> {
            final ApiErrorException e = assertThrowsExactly(ApiErrorException.class, () -> c.models().get("unknown"));
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
            final Files files = c.files().list();
            assertEquals("list", files.getObject());
            assertNotNull(files.getData());

            // remove old test file
            for (File file : files.getData()) {
                if ("processed".equals(file.getStatus()) && "test".equals(file.getFilename()) && 152 == file.getBytes()) {
                    System.out.println("delete: " + c.files().delete(file.getId()));
                }
            }

            final UploadFileRequest request = new UploadFileRequest("test", OpenAiClientTest.class.getResourceAsStream("/test.jsonl"));
            final File file = c.files().upload(request);
            assertNotNull(file.getId());
            assertNotNull(file.getCreated());
            assertEquals("file", file.getObject());
            assertEquals(UploadFileRequest.PURPOSE_FINE_TUNE, file.getPurpose());
            assertEquals("test", file.getFilename());
            assertEquals(152, file.getBytes());
            assertEquals("uploaded", file.getStatus());
            assertNull(file.getStatusDetails());

            await().pollInterval(Duration.ofSeconds(1)).pollDelay(Duration.ofSeconds(1)).until(() -> !"uploaded".equals(c.files().get(file.getId()).getStatus()));

            final ApiErrorException e = assertThrowsExactly(ApiErrorException.class, () -> c.files().content(file.getId()));
            assertEquals("To help mitigate abuse, downloading of fine-tune training files is disabled for free accounts.", e.getApiError().getMessage());
            assertEquals("invalid_request_error", e.getApiError().getType());

            final File del = c.files().delete(file.getId());
            assertEquals("file", del.getObject());
            assertEquals(file.getId(), del.getId());
            assertTrue(del.isDeleted());
        });
    }

    @Test
    void fineTunes() throws Throwable {
        useClient(c -> {
            final FineTunes fineTunes = c.fineTunes().list();
            assertEquals("list", fineTunes.getObject());
            assertNotNull(fineTunes.getData());

            for (FineTune fineTune : fineTunes.getData()) {
                System.out.println(fineTune.getId() + ": " + fineTune.getStatus() + ", " + fineTune.getFineTunedModel());
            }

            final File trainingFile = sampleFile(c);

            final CreateFineTuneRequest request = new CreateFineTuneRequest();
            request.setTrainingFile(trainingFile.getId());
            request.setValidationFile(null);
            request.setModel("ada");
            request.setNumEpochs(null);
            request.setBatchSize(null);
            request.setLearningRateMultiplier(null);
            request.setPromptLossWeight(null);
            request.setComputeClassificationMetrics(null);
            request.setClassificationNumClasses(null);
            request.setClassificationPositiveClass(null);
            request.setSuffix("test");
            final FineTune fineTune = c.fineTunes().create(request);
            assertEquals("fine-tune", fineTune.getObject());
            assertEquals("ada", fineTune.getModel());
            assertEquals(1, fineTune.getTrainingFiles().size());
            assertEquals(trainingFile.getId(), fineTune.getTrainingFiles().get(0).getId());
            assertEquals(0, fineTune.getValidationFiles().size());
            assertEquals(0, fineTune.getResultFiles().size());
            assertEquals("pending", fineTune.getStatus());
            assertEquals(1, fineTune.getEvents().size());
            final FineTuneEvent event = fineTune.getEvents().get(0);
            assertEquals("fine-tune-event", event.getObject());
            assertEquals("info", event.getLevel());
            assertEquals("Created fine-tune: " + fineTune.getId(), event.getMessage());

            final FineTune cancel = c.fineTunes().cancel(fineTune.getId());
            assertEquals("cancelled", cancel.getStatus());
            assertEquals(2, cancel.getEvents().size());
            final FineTuneEvent cancelEvent = cancel.getEvents().get(1);
            assertEquals("fine-tune-event", cancelEvent.getObject());
            assertEquals("info", cancelEvent.getLevel());
            assertEquals("Fine-tune cancelled", cancelEvent.getMessage());

            final FineTune get = c.fineTunes().get(fineTune.getId());
            assertEquals("cancelled", get.getStatus());
            assertEquals(2, get.getEvents().size());

            final FineTuneEvents events = c.fineTunes().events(fineTune.getId());
            assertEquals("list", events.getObject());
            assertEquals(2, events.getData().size());
        });
    }

    private File sampleFile(final OpenAiClient client) throws IOException, ApiException {
        final AtomicReference<File> fileRef = new AtomicReference<>();
        final Optional<File> match = client.files().list().getData().stream().filter(f -> "sample".equals(f.getFilename())).findFirst();
        if (match.isPresent()) {
            fileRef.set(match.get());
        } else {
            fileRef.set(client.files().upload(new UploadFileRequest("sample", OpenAiClientTest.class.getResourceAsStream("/test.jsonl"))));
        }

        final String fileId = fileRef.get().getId();
        await().pollInterval(Duration.ofSeconds(1)).until(() -> {
            if (!"uploaded".equals(fileRef.get().getStatus())) {
                return true;
            } else {
                fileRef.set(client.files().get(fileId));
                return false;
            }
        });

        return fileRef.get();
    }
}
