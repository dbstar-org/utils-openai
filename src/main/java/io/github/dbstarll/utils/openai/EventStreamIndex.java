package io.github.dbstarll.utils.openai;

import io.github.dbstarll.utils.net.api.index.AbstractIndex;

class EventStreamIndex extends AbstractIndex<EventStream> {
    EventStreamIndex(final EventStream data, final int index) {
        super(data, index);
    }
}
