package nl.kristalsoftware.datastore.base.eventstore.event.message;

import nl.kristalsoftware.domain.base.BaseEvent;

public interface EventMessageHandler<T> {

    Class<? extends BaseEvent> appliesTo();

    void save(T message);

}
