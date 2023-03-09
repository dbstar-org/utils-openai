package io.github.dbstarll.utils.openai.model.request;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import org.junit.jupiter.api.Test;

class CompletionRequestTest {

    @Test
    void setModel() throws JsonProcessingException {
        final ObjectMapper mapper = new ObjectMapper()
                .setSerializationInclusion(Include.NON_NULL)
                .setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
        final CompletionRequest request = new CompletionRequest();
        request.setModel("model");
        request.setMaxTokens(16);
        System.out.println(mapper.writeValueAsString(request));
    }
}