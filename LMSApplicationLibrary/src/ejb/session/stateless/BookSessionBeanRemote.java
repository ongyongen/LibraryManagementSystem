
package ejb.session.stateless;

import entity.Book;
import exception.BookNotFoundException;
import exception.InputDataValidationException;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author ongyongen
 */
@Remote
public interface BookSessionBeanRemote {
    
    public Long createNewBook (Book book) throws InputDataValidationException;
    
    public List<Book> retrieveAllBooks();
    
    public Book retrieveBookByTitle(String title) throws BookNotFoundException;

    
}