package rest;

import daos.BookDao;
import entities.Book;
import helpers.BaseJerseyTest;
import org.glassfish.jersey.server.ResourceConfig;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;

import static org.junit.jupiter.api.Assertions.*;

class BookServiceTest extends BaseJerseyTest {
    @InjectMocks
    BookDao bookDao;

    @Override
    protected Application configure() {
        return new ResourceConfig(BookService.class);
    }

    @Test
    void get() {
        Response response = target("books/1").request().get();

        try {
            assertEquals(200, response.getStatus());
            Book book = response.readEntity(Book.class);
            assertEquals(book.getId(), Long.valueOf(1L));
        } finally {
            response.close();
        }
    }

    @Test
    void create() {
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }
}