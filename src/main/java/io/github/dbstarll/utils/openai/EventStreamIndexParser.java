package io.github.dbstarll.utils.openai;

import io.github.dbstarll.utils.json.JsonParser;

class EventStreamIndexParser implements JsonParser<EventStreamIndex> {
    @Override
    public EventStreamIndex parse(final String str) {
        final int index = str.indexOf("\n\n");
        if (index < 0) {
            return new EventStreamIndex(null, index);
        } else {
            return new EventStreamIndex(new EventStream(str.substring(0, index)), index + 2);
        }
    }
}
