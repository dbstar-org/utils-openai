package io.github.dbstarll.utils.openai;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.util.ByteBufferBackedInputStream;
import io.github.dbstarll.utils.http.client.request.RelativeUriResolver;
import io.github.dbstarll.utils.json.jackson.JsonApiAsyncClient;
import io.github.dbstarll.utils.net.api.ApiException;
import io.github.dbstarll.utils.net.api.StreamFutureCallback;
import io.github.dbstarll.utils.openai.model.api.ChatCompletionChunk;
import io.github.dbstarll.utils.openai.model.api.TextCompletion;
import io.github.dbstarll.utils.openai.model.request.ChatRequest;
import io.github.dbstarll.utils.openai.model.request.CompletionRequest;
import io.github.dbstarll.utils.openai.model.response.ApiError;
import org.apache.hc.client5.http.async.HttpAsyncClient;
import org.apache.hc.client5.http.entity.EntityBuilder;
import org.apache.hc.core5.http.ClassicHttpRequest;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.EntityDetails;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.io.support.ClassicRequestBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.concurrent.Future;

import static org.apache.commons.lang3.Validate.notBlank;
import static org.apache.commons.lang3.Validate.notNull;

public final class OpenAiAsyncClient extends JsonApiAsyncClient {
    private final String apiKey;

    /**
     * 构造OpenAiAsyncClient实例.
     *
     * @param httpClient HttpAsyncClient
     * @param mapper     ObjectMapper
     * @param apiKey     API key
     */
    public OpenAiAsyncClient(final HttpAsyncClient httpClient, final ObjectMapper mapper, final String apiKey) {
        super(httpClient, true, optimize(mapper.copy()));
        this.apiKey = notBlank(apiKey, "apiKey is blank");
        setUriResolver(new RelativeUriResolver("https://api.openai.com/v1", "/v1"));
    }

    private static ObjectMapper optimize(final ObjectMapper mapper) {
        return mapper
                .setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE)
                .setSerializationInclusion(Include.NON_NULL);
    }

    @Override
    protected ClassicRequestBuilder preProcessing(final ClassicRequestBuilder builder) throws ApiException {
        return super.preProcessing(builder).setHeader("Authorization", "Bearer " + apiKey);
    }

    @Override
    protected void consume(final ClassicHttpRequest request, final EntityDetails entityDetails, final ByteBuffer src)
            throws IOException {
        final ContentType contentType = ContentType.parseLenient(entityDetails.getContentType());
        if (ContentType.APPLICATION_JSON.isSameMimeType(contentType)) {
            try (InputStream in = new ByteBufferBackedInputStream(src)) {
                final JsonNode error = mapper.readTree(in).get("error");
                throw new IOException(new ApiErrorException(mapper.convertValue(error, ApiError.class)));
            }
        }
        super.consume(request, entityDetails, src);
    }

    /**
     * Creates a completion for the provided prompt and parameters.
     *
     * @param request  CompletionRequest
     * @param callback StreamFutureCallback
     * @return Void
     * @throws ApiException exception on api call
     * @throws IOException  exception on io
     * @see <a href="https://platform.openai.com/docs/api-reference/completions/create">Create completion</a>
     */
    public Future<Void> completion(final CompletionRequest request, final StreamFutureCallback<TextCompletion> callback)
            throws ApiException, IOException {
        notNull(callback, "callback is null");
        request.setStream(true);
        return execute(post("/completions").setEntity(jsonEntity(request)).build(), TextCompletion.class,
                new CompletionsCallback<>(callback));
    }

    /**
     * Creates a completion for the chat message.
     *
     * @param request  ChatRequest
     * @param callback StreamFutureCallback
     * @return Void
     * @throws ApiException exception on api call
     * @throws IOException  exception on io
     * @see <a href="https://platform.openai.com/docs/api-reference/chat/create">Create chat completion</a>
     */
    public Future<Void> chat(final ChatRequest request, final StreamFutureCallback<ChatCompletionChunk> callback)
            throws ApiException, IOException {
        notNull(callback, "callback is null");
        request.setStream(true);
        return execute(post("/chat/completions").setEntity(jsonEntity(request)).build(),
                ChatCompletionChunk.class, new CompletionsCallback<>(callback));
    }

    private <T> HttpEntity jsonEntity(final T request) throws JsonProcessingException {
        return EntityBuilder.create().setText(mapper.writeValueAsString(request))
                .setContentType(ContentType.APPLICATION_JSON).setContentEncoding("UTF-8").build();
    }
}
