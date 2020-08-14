package rest;

import entities.Book;
import org.apache.http.HttpStatus;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Calendar;
import java.util.Date;

@Path("test")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TestService {
    @GET
    public Response get() {
        Book b = new Book((long) 1, "test", "test", new Date(1998, Calendar.AUGUST, 7));

        return Response
                .status(HttpStatus.SC_OK)
                .entity(b)
                .build();
    }
}
