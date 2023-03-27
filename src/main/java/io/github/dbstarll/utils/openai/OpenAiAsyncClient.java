package io.github.dbstarll.utils.openai;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import io.github.dbstarll.utils.http.client.request.RelativeUriResolver;
import io.github.dbstarll.utils.json.jackson.JsonApiAsyncClient;
import io.github.dbstarll.utils.net.api.ApiException;
import io.github.dbstarll.utils.net.api.StreamFutureCallback;
import io.github.dbstarll.utils.openai.model.request.ChatRequest;
import io.github.dbstarll.utils.openai.model.request.CompletionRequest;
import org.apache.hc.client5.http.async.HttpAsyncClient;
import org.apache.hc.client5.http.entity.EntityBuilder;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.io.support.ClassicRequestBuilder;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Future;

import static org.apache.commons.lang3.Validate.notBlank;

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
        setResponseHandlerFactory(new EventStreamResponseHandlerFactory(mapper, true));
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

//    @Override
//    protected <T> T postProcessing(final ClassicHttpRequest request, final T executeResult) throws ApiException {
//        final T result = super.postProcessing(request, executeResult);
//        if (result instanceof ObjectNode) {
//            final JsonNode error = ((ObjectNode) result).get("error");
//            if (error != null) {
//                throw new ApiErrorException(mapper.convertValue(error, ApiError.class));
//            }
//        }
//        return result;
//    }

    /**
     * Creates a completion for the provided prompt and parameters.
     *
     * @param request  CompletionRequest
     * @param callback StreamFutureCallback
     * @return List<EventStream>
     * @throws ApiException exception on api call
     * @throws IOException  exception on io
     * @see <a href="https://platform.openai.com/docs/api-reference/completions/create">Create completion</a>
     */
    public Future<List<EventStream>> completion(final CompletionRequest request,
                                                final StreamFutureCallback<EventStream> callback)
            throws ApiException, IOException {
        request.setStream(true);
        return execute(post("/completions").setEntity(jsonEntity(request)).build(), EventStream.class, callback);
    }

    /**
     * Creates a completion for the chat message.
     *
     * @param request  ChatRequest
     * @param callback StreamFutureCallback
     * @return ChatCompletion
     * @throws ApiException exception on api call
     * @throws IOException  exception on io
     * @see <a href="https://platform.openai.com/docs/api-reference/chat/create">Create chat completion</a>
     */
    public Future<List<EventStream>> chat(final ChatRequest request,
                                          final StreamFutureCallback<EventStream> callback)
            throws ApiException, IOException {
        request.setStream(true);
        return executeObject(post("/chat/completions").setEntity(jsonEntity(request)).build(),
                EventStream.class, callback);
    }

    private <T> HttpEntity jsonEntity(final T request) throws JsonProcessingException {
        return EntityBuilder.create().setText(mapper.writeValueAsString(request))
                .setContentType(ContentType.APPLICATION_JSON).setContentEncoding("UTF-8").build();
    }
}
