package helpers;

import daos.BookDao;
import daos.EmProducer;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

import javax.persistence.EntityManager;

public class ApplicationBinder extends AbstractBinder {
    @Override
    protected void configure() {
        bind(BookDao.class).to(BookDao.class).ranked(1);
        bindFactory(EmProducer.class).to(EntityManager.class).ranked(1);
    }
}
