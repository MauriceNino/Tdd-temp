package rest;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;

import javax.ws.rs.core.Application;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static org.junit.Assert.assertEquals;

public class SimpleJerseyTest extends JerseyTest {

    @Override
    protected Application configure() {
        return new ResourceConfig(BookService.class);
    }

    @Test
    public void test() {
        Response response = target("books").request().get();

        assertEquals("Http Response should be 200: ", Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals("Http Content-Type should be: ", MediaType.TEXT_HTML, response.getHeaderString(HttpHeaders.CONTENT_TYPE));

        String content = response.readEntity(String.class);
        System.out.println("Gotten response: " + content);
        assertEquals("Content of ressponse is: ", "Hello World!", content);
    }
}