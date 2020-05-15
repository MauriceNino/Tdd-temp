package daos;

import entities.Book;
import helpers.BaseTest;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

class BookDaoTest extends BaseTest {
    @Inject
    private BookDao bookDao;


    // Exercise 1.
    // When the Book gets added, the date can not be bigger than todays date minus 10 years
    // If that happens, throw an error
    @Test
    void add() {
        Book b = Book.builder()
                .author("Test")
                .name("Test Name")
                .releaseDate(new Date())
                .build();

        bookDao.create(b);

        // Id needs to be generated
        assertNotNull(b.getId());

        Book added = bookDao.get(b.getId());
        assertEquals("Test", added.getAuthor());
        assertEquals("Test Name", added.getName());
    }

    // Exercise 2.
    // When removing a book, it should be added to the collection "removed_books"
    @Test
    void remove() {
        Book b = Book.builder()
                .author("Test")
                .name("Test Name")
                .releaseDate(new Date())
                .build();

        bookDao.create(b);
        assertNotNull(b.getId());

        bookDao.delete(b.getId());
        assertNull(bookDao.get(b.getId()));
    }

    // Exercise 3.
    // Use DBUnit to get 3 books with random data (Using XML Data)
    // Hint: Use the DBSetup annotation
    @Test
    void getAll() {
        List<Book> books = bookDao.getAll();
        assertEquals(0, books.size());
    }
}