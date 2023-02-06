package ejb.session.stateless;

import entity.Book;
import entity.Staff;
import exception.BookNotFoundException;
import exception.InputDataValidationException;
import java.util.List;
import java.util.Set;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;
import javax.validation.Validator;
/**
 *
 * @author ongyongen
 */
@Stateless
public class BookSessionBean implements BookSessionBeanRemote, BookSessionBeanLocal {

    @PersistenceContext(unitName = "LMSApplication-ejbPU")
    private EntityManager em;
    
    private final ValidatorFactory validatorFactory;
    private final javax.validation.Validator validator;

    public BookSessionBean() {
        this.validatorFactory = Validation.buildDefaultValidatorFactory();
        this.validator = validatorFactory.getValidator();
    }

        
    @Override
    public Long createNewBook (Book book) throws InputDataValidationException {
        Set<ConstraintViolation<Book>> constraintViolations = validator.validate(book);
        if (constraintViolations.isEmpty()) {
            em.persist(book);
            em.flush();
            return book.getBookId();
        } else {
            throw new InputDataValidationException(prepareInputDataValidationErrorMsg(constraintViolations));
        }
    }
    
    private String prepareInputDataValidationErrorMsg(Set<ConstraintViolation<Book>> violations) {
        String msg = "Input data validation error :";

        for (ConstraintViolation violation : violations) {
            msg += "\n" + violation.getPropertyPath() + " - " + violation.getMessage();
        }

        return msg;
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