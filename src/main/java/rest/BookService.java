package rest;

import daos.BookDao;
import entities.Book;
import org.apache.http.HttpStatus;

import javax.inject.Inject;
import javax.ws.rs.Path;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;

@Path("books")
public class BookService implements ApiBase<Book>  {
    @Inject
    BookDao bookDao;

    @Override
    public Response getAll() {
        return Response
                .status(HttpStatus.SC_OK)
                .entity(bookDao.getAll())
                .build();
    }

    @Override
    public Response get(Long id) {
        Book b = bookDao.get(id);

        if(b == null) {
            return Response
                    .status(HttpStatus.SC_NOT_FOUND)
                    .build();
        }

        try {
            return Response
                    .status(HttpStatus.SC_OK)
                    .entity(b)
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response
                    .status(HttpStatus.SC_METHOD_FAILURE)
                    .build();
        }
    }

    @Override
    public Response create(Book elem) {
        bookDao.create(elem);

        return Response
                .status(HttpStatus.SC_OK)
                .entity(elem)
                .build();
    }

    @Override
    public Response update(Long id, Book elem) {
        elem.setId(id);
        bookDao.update(elem);

        return Response
                .status(HttpStatus.SC_OK)
                .entity(elem)
                .build();
    }

    @Override
    public Response delete(Long id) {
        bookDao.delete(id);

        return Response
                .status(HttpStatus.SC_OK)
                .build();
    }
}
