package rest;

import entities.Book;
import helpers.ApplicationBinder;
import helpers.BaseTest;
import helpers.DBSetup;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;
import static org.junit.Assert.assertEquals;

public class BookServiceTest extends BaseTest {

    @Override
    protected Application configure() {
        ResourceConfig resource = new ResourceConfig(BookService.class);
        resource.register(new ApplicationBinder());
        resource.register(JacksonFeature.class);
        return resource;
    }

    @Test
    @DBSetup("bookServiceTest.xml")
    public void get() {
        Response response = target("books/1").request().get();

        Book book = response.readEntity(Book.class);
        try {
            assertEquals(200, response.getStatus());
        } finally {
            response.close();
        }

        assertEquals("Maurice", book.getAuthor());
    }
}