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

public final class OpenAiClient extends JsonApiClient {
    private final String apiKey;
    private final ModelOperations modelOperations;
    private final FileOperations fileOperations;

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
        this.modelOperations = new ModelOperations();
        this.fileOperations = new FileOperations();
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

    private <T> HttpEntity jsonEntity(final T request) throws JsonProcessingException {
        return EntityBuilder.create().setText(mapper.writeValueAsString(request))
                .setContentType(ContentType.APPLICATION_JSON).setContentEncoding("UTF-8").build();
    }

    /**
     * List and describe the various models available in the API. You can refer to the Models documentation
     * to understand what models are available and the differences between them.
     *
     * @return ModelOperations
     */
    public ModelOperations models() {
        return modelOperations;
    }

    /**
     * Files are used to upload documents that can be used with features like Fine-tuning.
     *
     * @return FileOperations
     */
    public FileOperations files() {
        return fileOperations;
    }

    public final class ModelOperations {
        private static final String PREFIX = "/models";

        /**
         * Lists the currently available models, and provides basic information
         * about each one such as the owner and availability.
         *
         * @return Models
         * @throws ApiException exception on api call
         * @throws IOException  exception on io
         * @see <a href="https://platform.openai.com/docs/api-reference/models/list">List models</a>
         */
        public Models list() throws ApiException, IOException {
            return execute(OpenAiClient.this.get(PREFIX).build(), Models.class);
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
        public Model get(final String model) throws ApiException, IOException {
            notBlank(model, "model is Required");
            return execute(OpenAiClient.this.get(PREFIX + "/" + model).build(), Model.class);
        }
    }

    public final class FileOperations {
        private static final String PREFIX = "/files";

        /**
         * Returns a list of files that belong to the user's organization.
         *
         * @return Files
         * @throws ApiException exception on api call
         * @throws IOException  exception on io
         * @see <a href="https://platform.openai.com/docs/api-reference/files/list">List files</a>
         */
        public Files list() throws ApiException, IOException {
            return execute(OpenAiClient.this.get(PREFIX).build(), Files.class);
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
        public File upload(final UploadFileRequest request) throws ApiException, IOException {
            return execute(OpenAiClient.this.post(PREFIX).setEntity(request.buildEntity()).build(), File.class);
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
        public File delete(final String fileId) throws ApiException, IOException {
            return execute(OpenAiClient.this.delete(filePath(fileId)).build(), File.class);
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
        public File get(final String fileId) throws ApiException, IOException {
            return execute(OpenAiClient.this.get(filePath(fileId)).build(), File.class);
        }

        /**
         * Returns the contents of the specified file.
         * To help mitigate abuse, downloading of fine-tune training files is disabled for free accounts.
         *
         * @param fileId The ID of the file to use for this request
         * @return String
         * @throws ApiException exception on api call
         * @throws IOException  exception on io
         * @see <a href="https://platform.openai.com/docs/api-reference/files/retrieve-content">Retrieve file
         * content</a>
         */
        public JsonNode content(final String fileId) throws ApiException, IOException {
            return execute(OpenAiClient.this.get(filePath(fileId) + "/content").build(), JsonNode.class);
        }

        private String filePath(final String fileId) {
            return PREFIX + "/" + notBlank(fileId, "fileId is blank");
        }
    }
}
