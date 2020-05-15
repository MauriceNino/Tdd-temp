package daos;

import entities.Book;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class BookDao extends BaseDao<Book> {
    public BookDao() {
        super(Book.class);
    }
}
