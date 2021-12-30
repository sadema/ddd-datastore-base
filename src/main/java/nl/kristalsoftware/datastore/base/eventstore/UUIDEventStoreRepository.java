package nl.kristalsoftware.datastore.base.eventstore;

import nl.kristalsoftware.datastore.base.eventstore.event.entity.UUIDBaseEventEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface UUIDEventStoreRepository extends CrudRepository<UUIDBaseEventEntity, Long> {

    Iterable<UUIDBaseEventEntity> findAllByReference(UUID value);

}
