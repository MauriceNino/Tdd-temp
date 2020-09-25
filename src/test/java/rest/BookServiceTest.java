package rest;

import daos.BookDao;
import daos.EmProducer;
import entities.Book;
import helpers.ApplicationBinder;
import helpers.ServiceTest;
import helpers.DBTestHelper;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.jboss.weld.junit5.auto.AddPackages;
import org.jboss.weld.junit5.auto.EnableAutoWeld;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;
import static org.junit.Assert.assertEquals;

@EnableAutoWeld
@AddPackages(EmProducer.class)
public class BookServiceTest extends ServiceTest {
    @Inject
    private DBTestHelper db;

    @Override
    protected Application configure() {
        return new ResourceConfig()
                .register(new ApplicationBinder(BookDao.class))
                .register(BookService.class);
    }

    @Test
    public void get() {
        db.setup();
        db.using(this.getClass(), "bookServiceTest.xml");

        Response response = target("books/1").request().get();
        Book book = response.readEntity(Book.class);

        assertEquals(200, response.getStatus());
        assertEquals("Maurice", book.getAuthor());

        response.close();
        db.teardown();
    }
}