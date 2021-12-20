package nl.hu.bep3.management.infrastracture.repository.mongo;

import java.util.UUID;
import nl.hu.bep3.management.infrastracture.repository.Persistable;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;

@Component
public class GenerateUuidListener extends AbstractMongoEventListener<Persistable<UUID>> {
  @Override
  public void onBeforeConvert(final BeforeConvertEvent<Persistable<UUID>> event) {
    final Persistable<UUID> persistable = event.getSource();
    if (persistable.isNew()) {
      persistable.setId(UUID.randomUUID());
    }
  }
}
