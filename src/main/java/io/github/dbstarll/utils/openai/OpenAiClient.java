package io.github.dbstarll.utils.openai;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import io.github.dbstarll.utils.http.client.request.RelativeUriResolver;
import io.github.dbstarll.utils.json.jackson.JsonApiClient;
import io.github.dbstarll.utils.net.api.ApiException;
import io.github.dbstarll.utils.openai.model.response.Models;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;

import java.io.IOException;

import static org.apache.commons.lang3.Validate.notBlank;

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
        super(httpClient, true, mapper.copy()
                .setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE));
        this.apiKey = notBlank(apiKey, "apiKey is blank");
        setUriResolver(new RelativeUriResolver("https://api.openai.com/v1", "/v1"));
    }

    @Override
    protected RequestBuilder preProcessing(final RequestBuilder builder) throws ApiException {
        return super.preProcessing(builder).setHeader("Authorization", "Bearer " + apiKey);
    }

    @Override
    protected <T> T postProcessing(final HttpUriRequest request, final T executeResult) throws ApiException {
        return super.postProcessing(request, executeResult);
    }

    public Models models() throws ApiException, IOException {
        return executeObject(get("/models").build(), Models.class);
    }
}
