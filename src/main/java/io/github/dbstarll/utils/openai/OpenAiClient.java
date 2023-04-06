package io.github.dbstarll.utils.openai;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.github.dbstarll.utils.http.client.request.RelativeUriResolver;
import io.github.dbstarll.utils.json.jackson.JsonApiClient;
import io.github.dbstarll.utils.net.api.ApiException;
import io.github.dbstarll.utils.openai.model.api.ChatCompletion;
import io.github.dbstarll.utils.openai.model.api.File;
import io.github.dbstarll.utils.openai.model.api.Model;
import io.github.dbstarll.utils.openai.model.api.TextCompletion;
import io.github.dbstarll.utils.openai.model.request.ChatRequest;
import io.github.dbstarll.utils.openai.model.request.CompletionRequest;
import io.github.dbstarll.utils.openai.model.request.UploadFileRequest;
import io.github.dbstarll.utils.openai.model.response.ApiError;
import io.github.dbstarll.utils.openai.model.response.Files;
import io.github.dbstarll.utils.openai.model.response.Models;
import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.entity.EntityBuilder;
import org.apache.hc.core5.http.ClassicHttpRequest;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.io.support.ClassicRequestBuilder;

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
    protected ClassicRequestBuilder preProcessing(final ClassicRequestBuilder builder) throws ApiException {
        return super.preProcessing(builder).setHeader("Authorization", "Bearer " + apiKey);
    }

    @Override
    protected <T> T postProcessing(final ClassicHttpRequest request, final T executeResult) throws ApiException {
        final T result = super.postProcessing(request, executeResult);
        final JsonNode error = ((ObjectNode) result).get("error");
        if (error != null) {
            throw new ApiErrorException(mapper.convertValue(error, ApiError.class));
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
        return execute(get("/models").build(), Models.class);
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
        return execute(get("/models/" + notNull(model, "model is Required")).build(), Model.class);
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
    public TextCompletion completion(final CompletionRequest request) throws ApiException, IOException {
        request.setStream(false);
        return execute(post("/completions").setEntity(jsonEntity(request)).build(), TextCompletion.class);
    }

    /**
     * Creates a completion for the chat message.
     *
     * @param request ChatRequest
     * @return ChatCompletion
     * @throws ApiException exception on api call
     * @throws IOException  exception on io
     * @see <a href="https://platform.openai.com/docs/api-reference/chat/create">Create chat completion</a>
     */
    public ChatCompletion chat(final ChatRequest request) throws ApiException, IOException {
        request.setStream(false);
        return execute(post("/chat/completions").setEntity(jsonEntity(request)).build(), ChatCompletion.class);
    }

    /**
     * Returns a list of files that belong to the user's organization.
     *
     * @return Files
     * @throws ApiException exception on api call
     * @throws IOException  exception on io
     * @see <a href="https://platform.openai.com/docs/api-reference/files/list">List files</a>
     */
    public Files files() throws ApiException, IOException {
        return execute(get("/files").build(), Files.class);
    }

    /**
     * Upload a file that contains document(s) to be used across various endpoints/features.
     * Currently, the size of all the files uploaded by one organization can be up to 1 GB.
     *
     * @param request UploadFileRequest
     * @return File
     * @throws ApiException exception on api call
     * @throws IOException  exception on io
     * @see <a href="https://platform.openai.com/docs/api-reference/files/upload">Upload file</a>
     */
    public File uploadFile(final UploadFileRequest request) throws ApiException, IOException {
        return execute(post("/files").setEntity(request.buildEntity()).build(), File.class);
    }

    /**
     * Delete a file.
     *
     * @param fileId The ID of the file to use for this request
     * @return File
     * @throws ApiException exception on api call
     * @throws IOException  exception on io
     * @see <a href="https://platform.openai.com/docs/api-reference/files/delete">Delete file</a>
     */
    public File deleteFile(final String fileId) throws ApiException, IOException {
        return execute(delete("/files/" + fileId).build(), File.class);
    }

    /**
     * Returns information about a specific file.
     *
     * @param fileId The ID of the file to use for this request
     * @return File
     * @throws ApiException exception on api call
     * @throws IOException  exception on io
     * @see <a href="https://platform.openai.com/docs/api-reference/files/retrieve">Retrieve file</a>
     */
    public File getFile(final String fileId) throws ApiException, IOException {
        return execute(get("/files/" + fileId).build(), File.class);
    }

    /**
     * Returns the contents of the specified file.
     * To help mitigate abuse, downloading of fine-tune training files is disabled for free accounts.
     *
     * @param fileId The ID of the file to use for this request
     * @return String
     * @throws ApiException exception on api call
     * @throws IOException  exception on io
     * @see <a href="https://platform.openai.com/docs/api-reference/files/retrieve-content">Retrieve file content</a>
     */
    public JsonNode getFileContent(final String fileId) throws ApiException, IOException {
        return execute(get("/files/" + fileId + "/content").build(), JsonNode.class);
    }

    private <T> HttpEntity jsonEntity(final T request) throws JsonProcessingException {
        return EntityBuilder.create().setText(mapper.writeValueAsString(request))
                .setContentType(ContentType.APPLICATION_JSON).setContentEncoding("UTF-8").build();
    }
}
