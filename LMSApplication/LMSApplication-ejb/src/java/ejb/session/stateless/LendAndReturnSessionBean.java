
package ejb.session.stateless;

import entity.Book;
import entity.LendAndReturn;
import entity.Member;
import exception.BookNotAvailableException;
import exception.BookNotFoundException;
import exception.FineNotPaidException;
import exception.InputDataValidationException;
import exception.LendingNotFoundException;
import exception.MemberNotFoundException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Set;
import javax.ejb.EJB;
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
public class LendAndReturnSessionBean implements LendAndReturnSessionBeanRemote, LendAndReturnSessionBeanLocal {

    @EJB
    private MemberSessionBeanLocal memberSessionBeanLocal;

    @EJB
    private BookSessionBeanLocal bookSessionBeanLocal;

    @PersistenceContext(unitName = "LMSApplication-ejbPU")
    private EntityManager em;
    
    private final ValidatorFactory validatorFactory;
    private final Validator validator;

    public LendAndReturnSessionBean() {
        this.validatorFactory = Validation.buildDefaultValidatorFactory();
        this.validator = validatorFactory.getValidator();
    }
    
    private String prepareInputDataValidationErrorMsg(Set<ConstraintViolation<LendAndReturn>> violations) {
        String msg = "Input data validation error :";

        for (ConstraintViolation violation : violations) {
            msg += "\n" + violation.getPropertyPath() + " - " + violation.getMessage();
        }

        return msg;
    }
           
    @Override
    public Long createLendingRecord(String memberIdentityNo, String bookTitle, Date currentDate) throws MemberNotFoundException, BookNotFoundException, BookNotAvailableException, InputDataValidationException {
        Member member = memberSessionBeanLocal.retrieveMemberByIdentityNo(memberIdentityNo);
        Book book = bookSessionBeanLocal.retrieveBookByTitle(bookTitle);
                
        Long bookId = book.getBookId();
        List<LendAndReturn> bookRecords = this.retrieveAllLendingRecordsByBookId(bookId);
        boolean isAvailable = true;
        if (!bookRecords.isEmpty()) {
            for (LendAndReturn bookRecord : bookRecords) {
                Date lendDate = bookRecord.getLendDate();
                Date returnDate = bookRecord.getReturnDate();
                if (currentDate.after(lendDate) && returnDate == null) {
                    isAvailable = false;
                    break;
                }
            }
        }
        
        if (isAvailable == true) {
            LendAndReturn record = new LendAndReturn(currentDate);
            record.setMember(member);
            record.setBook(book);
                        
            Set<ConstraintViolation<LendAndReturn>> constraintViolations = validator.validate(record);
            if (constraintViolations.isEmpty()) {
                em.persist(record);
                em.flush();
                return record.getLendId();
            } else {
                throw new InputDataValidationException(prepareInputDataValidationErrorMsg(constraintViolations));
            }
            
        } else {
            throw new BookNotAvailableException();
        }

    }
    
    @Override
    public List<LendAndReturn> retrieveAllLendingRecords() {
        String queryString = "SELECT record FROM LendAndReturn record";
        Query query = em.createQuery(queryString);
        return query.getResultList();   
    }
    
    public List<LendAndReturn> retrieveAllLendingRecordsByBookId(Long bookId) {
        Query query = em.createQuery("SELECT record FROM LendAndReturn record "
                + "WHERE record.book.bookId = :bookId");
        query.setParameter("bookId", bookId);
        return query.getResultList();
    }
    
    @Override
    public LendAndReturn retrieveLendingRecordById(Long recordId) throws LendingNotFoundException {
        if (em.find(LendAndReturn.class, recordId) == null) {
            throw new LendingNotFoundException();
        } else {
            LendAndReturn record = em.find(LendAndReturn.class, recordId);
            return record;
        }
    }
    
    @Override
    public LendAndReturn retrieveLendingRecordByIdNoAndTitle(String idNo, String title) throws LendingNotFoundException, BookNotFoundException, MemberNotFoundException {
        Long bookId = bookSessionBeanLocal.retrieveBookByTitle(title).getBookId();
        Long memberId = memberSessionBeanLocal.retrieveMemberByIdentityNo(idNo).getMemberId();
           
        Query query = em.createQuery("SELECT record FROM LendAndReturn record "
                + "WHERE record.book.bookId = :bookId "
                + "AND record.member.memberId = :memberId");
 
        query.setParameter("bookId", bookId);
        query.setParameter("memberId", memberId);

        if (query.getResultList().isEmpty()) {
            throw new LendingNotFoundException();
        } else {
            return (LendAndReturn)query.getSingleResult();
        }
    }

    @Override
    public BigDecimal calculateFineAmount(Date currentDate, Date lendDate) {
        long diff = Math.abs(currentDate.getTime() - lendDate.getTime());
        long days = diff / (1000*60*60*24);
        BigDecimal fineAmount = new BigDecimal((days-14) * 0.50);        
        return fineAmount;
    }
    
    @Override
    public BigDecimal retrieveFineAmountForRecord(Long recordId, Date currentDate) throws LendingNotFoundException, BookNotFoundException, MemberNotFoundException {
        LendAndReturn record = this.retrieveLendingRecordById(recordId);
        Date lendDate = record.getLendDate();    
        return calculateFineAmount(currentDate, lendDate);
    }
    
    @Override
    public void returnBookNotLate(Long recordId, Date returnDate) throws LendingNotFoundException, BookNotFoundException, MemberNotFoundException {
        LendAndReturn record = this.retrieveLendingRecordById(recordId);
        record.setReturnDate(returnDate);
    }
    
    @Override
    public void returnBookLate(Long recordId, Date returnDate, BigDecimal finePayment) throws LendingNotFoundException, BookNotFoundException, MemberNotFoundException, FineNotPaidException {
        LendAndReturn record = this.retrieveLendingRecordById(recordId);
        Date lendDate = record.getLendDate(); 
        BigDecimal fineAmount = calculateFineAmount(returnDate, lendDate);
        
        if (finePayment.compareTo(fineAmount) == -1) {
            throw new FineNotPaidException();
        } else {
            record.setReturnDate(returnDate);
            record.setFineAmount(fineAmount);
            
        }
    }

    
}