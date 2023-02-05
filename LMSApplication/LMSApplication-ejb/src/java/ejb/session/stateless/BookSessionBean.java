package ejb.session.stateless;

import entity.Book;
import exception.BookNotFoundException;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author ongyongen
 */
@Stateless
public class BookSessionBean implements BookSessionBeanRemote, BookSessionBeanLocal {

    @PersistenceContext(unitName = "LMSApplication-ejbPU")
    private EntityManager em;

    @Override
    public Long createNewBook (Book book) {
        em.persist(book);
        em.flush();
        return book.getBookId();
    }
    
    @Override
    public List<Book> retrieveAllBooks() {
        String queryString = "SELECT b FROM Book b";
        Query query = em.createQuery(queryString);
        return query.getResultList();
    }
    
    @Override
    public Book retrieveBookByTitle(String title) throws BookNotFoundException {        
        Query query = em.createQuery("SELECT b FROM Book b "
                + "WHERE b.title = :title");
              
        query.setParameter("title", title);
        
        if (query.getResultList().isEmpty()) {
            throw new BookNotFoundException();
        } else {
            return (Book)query.getSingleResult();
        }
        
    }

}