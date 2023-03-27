package io.github.dbstarll.utils.openai;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.dbstarll.utils.json.jackson.JsonResponseHandlerFactory;

final class EventStreamResponseHandlerFactory extends JsonResponseHandlerFactory {
    EventStreamResponseHandlerFactory(final ObjectMapper mapper, final boolean alwaysProcessEntity) {
        super(mapper, alwaysProcessEntity);
        addResponseHandler(EventStreamIndex.class, new EventStreamIndexParser(), alwaysProcessEntity);
    }
}
