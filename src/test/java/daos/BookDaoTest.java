package daos;

import entities.Book;
import helpers.DBTestHelper;
import org.jboss.weld.junit5.auto.AddPackages;
import org.jboss.weld.junit5.auto.EnableAutoWeld;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

@EnableAutoWeld
@AddPackages(EmProducer.class)
class BookDaoTest {
    @Inject
    DBTestHelper db;
    @Inject
    private BookDao bookDao;

    // Exercise 1.
    // When the Book gets added, the date can not be bigger than todays date minus 10 years
    // If that happens, throw an error
    @Test
    void add() {
        db.setup();

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

        db.teardown();
    }

    // Exercise 2.
    // When removing a book, it should be added to the collection "removed_books"
    @Test
    void remove() {
        db.setup();

        Book b = Book.builder()
                .author("Test")
                .name("Test Name")
                .releaseDate(new Date())
                .build();

        bookDao.create(b);
        assertNotNull(b.getId());

        bookDao.delete(b.getId());
        assertNull(bookDao.get(b.getId()));

        db.teardown();
    }

    // Exercise 3.
    // Use DBUnit to get 3 books with random data (Using XML Data)
    // Hint: Check out the DBTestHelper class to find a fitting method
    @Test
    void getAll() {
        db.setup();

        List<Book> books = bookDao.getAll();
        assertEquals(0, books.size());

        db.teardown();
    }
}