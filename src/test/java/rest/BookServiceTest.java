package rest;

import helpers.ApplicationBinder;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;

import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;

import static org.junit.Assert.assertEquals;

public class BookServiceTest extends JerseyTest {
    @Override
    protected Application configure() {
        ResourceConfig resource = new ResourceConfig(BookService.class);
        resource.register(new ApplicationBinder());
        resource.register(JacksonFeature.class);
        return resource;
    }

    @Test
    public void get() {
        Response response = target("books/1").request().get();

        String books = response.readEntity(String.class);
        try {
            assertEquals(200, response.getStatus());
        } finally {
            response.close();
        }
    }
}