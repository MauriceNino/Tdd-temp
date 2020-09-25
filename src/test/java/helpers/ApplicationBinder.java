package helpers;

import daos.BookDao;
import daos.EmProducer;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

import javax.persistence.EntityManager;
import java.util.Arrays;

public class ApplicationBinder extends AbstractBinder {
    public ApplicationBinder(Class<?>... classes) {
        Arrays.stream(classes).forEach(cl -> bind(cl).to(cl).ranked(1));
    }

    @Override
    protected void configure() {
        bindFactory(EmProducer.class).to(EntityManager.class).ranked(1);
    }
}
