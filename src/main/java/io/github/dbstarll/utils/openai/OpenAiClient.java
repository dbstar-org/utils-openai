package io.github.dbstarll.utils.openai;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.github.dbstarll.utils.http.client.request.RelativeUriResolver;
import io.github.dbstarll.utils.json.jackson.JsonApiClient;
import io.github.dbstarll.utils.net.api.ApiException;
import io.github.dbstarll.utils.openai.model.api.Model;
import io.github.dbstarll.utils.openai.model.api.TextCompletion;
import io.github.dbstarll.utils.openai.model.request.CompletionRequest;
import io.github.dbstarll.utils.openai.model.response.ApiError;
import io.github.dbstarll.utils.openai.model.response.Models;
import org.apache.http.HttpEntity;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.EntityBuilder;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.entity.ContentType;

import java.io.IOException;

import static org.apache.commons.lang3.Validate.notBlank;
import static org.apache.commons.lang3.Validate.notNull;

public final class OpenAiClient extends JsonApiClient {
    private final String apiKey;

    /**
     * 构造OpenAiClient实例.
     *
     * @param httpClient HttpClient
     * @param mapper     ObjectMapper
     * @param apiKey     API key
     */
    public OpenAiClient(final HttpClient httpClient, final ObjectMapper mapper, final String apiKey) {
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
    protected RequestBuilder preProcessing(final RequestBuilder builder) throws ApiException {
        return super.preProcessing(builder).setHeader("Authorization", "Bearer " + apiKey);
    }

    @Override
    protected <T> T postProcessing(final HttpUriRequest request, final T executeResult) throws ApiException {
        final T result = super.postProcessing(request, executeResult);
        if (result instanceof ObjectNode) {
            final JsonNode error = ((ObjectNode) result).get("error");
            if (error != null) {
                throw new ApiErrorException(mapper.convertValue(error, ApiError.class));
            }
        }
        return result;
    }

    /**
     * Lists the currently available models, and provides basic information
     * about each one such as the owner and availability.
     *
     * @return Models
     * @throws ApiException exception on api call
     * @throws IOException  exception on io
     * @see <a href="https://platform.openai.com/docs/api-reference/models/list">List models</a>
     */
    public Models models() throws ApiException, IOException {
        return executeObject(get("/models").build(), Models.class);
    }

    /**
     * Retrieves a model instance, providing basic information about the model such as the owner and permissioning.
     *
     * @param model The ID of the model to use for this request
     * @return Model
     * @throws ApiException exception on api call
     * @throws IOException  exception on io
     * @see <a href="https://platform.openai.com/docs/api-reference/models/retrieve">Retrieve model</a>
     */
    public Model model(final String model) throws ApiException, IOException {
        return executeObject(get("/models/" + notNull(model, "model is Required")).build(), Model.class);
    }

    /**
     * Creates a completion for the provided prompt and parameters.
     *
     * @param request CompletionRequest
     * @return TextCompletion
     * @throws ApiException exception on api call
     * @throws IOException  exception on io
     * @see <a href="https://platform.openai.com/docs/api-reference/completions/create">Create completion</a>
     */
    public TextCompletion completions(final CompletionRequest request) throws ApiException, IOException {
        final String json = mapper.writeValueAsString(request);
        final HttpEntity entity = EntityBuilder.create().setText(json)
                .setContentType(ContentType.APPLICATION_JSON).setContentEncoding("UTF-8").build();
        final HttpUriRequest httpUriRequest = post("/completions").setEntity(entity).build();
        logger.trace("json: [{}]@{} with {}", httpUriRequest, httpUriRequest.hashCode(), json);
        return executeObject(httpUriRequest, TextCompletion.class);
    }
}
