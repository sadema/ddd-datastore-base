package nl.kristalsoftware.datastore.base.eventstore.event.entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import java.util.UUID;

@NoArgsConstructor
@Data
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class UUIDBaseEventEntity implements BaseEventEntityName {

    @Setter(AccessLevel.NONE)
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Type(type = "uuid-char")
    private UUID reference;

    private String domainEventName;

    public UUIDBaseEventEntity(UUID reference, String domainEventName) {
        this.reference = reference;
        this.domainEventName = domainEventName;
    }

}
