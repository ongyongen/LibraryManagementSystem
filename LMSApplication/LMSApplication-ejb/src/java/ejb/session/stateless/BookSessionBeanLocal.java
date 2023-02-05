
package ejb.session.stateless;

import entity.Book;
import exception.BookNotFoundException;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author ongyongen
 */
@Local
public interface BookSessionBeanLocal {
    
    public Long createNewBook (Book book);
    
    public List<Book> retrieveAllBooks();

    public Book retrieveBookByTitle(String title) throws BookNotFoundException;
    
}