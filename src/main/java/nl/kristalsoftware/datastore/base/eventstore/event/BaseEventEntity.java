package nl.kristalsoftware.datastore.base.eventstore.event;

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
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.UUID;

@NoArgsConstructor
@Data
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class BaseEventEntity {

    @Setter(AccessLevel.NONE)
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Type(type = "uuid-char")
    private UUID reference;

    private String domainEventName;

    public BaseEventEntity(UUID reference, String domainEventName) {
        this.reference = reference;
        this.domainEventName = domainEventName;
    }

    public LocalDate getLocalDateFromMillis(Long date) {
        Instant instant = Instant.ofEpochMilli(date);
        return LocalDate.ofInstant(instant, ZoneId.systemDefault());
    }

}
