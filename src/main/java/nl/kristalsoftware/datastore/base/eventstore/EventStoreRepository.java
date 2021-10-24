package nl.kristalsoftware.datastore.base.eventstore;

import nl.kristalsoftware.datastore.base.eventstore.event.BaseEventEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface EventStoreRepository extends CrudRepository<BaseEventEntity, Long> {

    Iterable<BaseEventEntity> findAllByReference(UUID value);

}
