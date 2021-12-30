package nl.kristalsoftware.datastore.base.eventstore.event;

import java.util.List;

public interface BaseEventFinder<T,V> {

    List<V> getEvents(T aggregateRoot);

}
