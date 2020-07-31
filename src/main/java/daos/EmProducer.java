package daos;

import org.glassfish.hk2.api.Factory;

import javax.enterprise.inject.Default;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;

public class EmProducer implements Factory<EntityManager> {

    @Produces
    @Default
    public EntityManager create() {
        return Persistence
                .createEntityManagerFactory("com.gepardec")
                .createEntityManager();
    }

    @Override
    public EntityManager provide() {
        return create();
    }

    public void dispose(@Disposes @Default EntityManager entityManager) {
        if (entityManager.isOpen()) {
            entityManager.close();
        }
    }
}
