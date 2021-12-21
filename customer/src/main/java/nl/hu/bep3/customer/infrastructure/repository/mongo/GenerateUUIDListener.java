package nl.hu.bep3.customer.infrastructure.repository.mongo;

import nl.hu.bep3.customer.infrastructure.repository.Persistable;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class GenerateUUIDListener extends AbstractMongoEventListener<Persistable<UUID>> {
    @Override
    public void onBeforeConvert(final BeforeConvertEvent<Persistable<UUID>> event) {
        final Persistable<UUID> persistable = event.getSource();
        if (persistable.isNew()) {
            persistable.setId(UUID.randomUUID());
        }
    }
}
