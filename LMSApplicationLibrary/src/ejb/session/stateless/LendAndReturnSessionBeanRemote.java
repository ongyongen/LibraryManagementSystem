
package ejb.session.stateless;

import entity.LendAndReturn;
import exception.BookNotAvailableException;
import exception.BookNotFoundException;
import exception.FineNotPaidException;
import exception.LendingNotFoundException;
import exception.MemberNotFoundException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author ongyongen
 */
@Remote
public interface LendAndReturnSessionBeanRemote {
    
    public Long createLendingRecord(String memberIdentityNo, String bookTitle) throws MemberNotFoundException, BookNotFoundException, BookNotAvailableException;    

    public LendAndReturn retrieveLendingRecordByIdNoAndTitle(String idNo, String title) throws LendingNotFoundException, BookNotFoundException, MemberNotFoundException;

    public List<LendAndReturn> retrieveAllLendingRecords();

    public LendAndReturn retrieveLendingRecordById(Long recordId) throws LendingNotFoundException;

    public BigDecimal calculateFineAmount(Date currentDate, Date lendDate);

    public BigDecimal retrieveFineAmountForRecord(Long recordId) throws LendingNotFoundException, BookNotFoundException, MemberNotFoundException;

    public void returnBookNotLate(Long recordId, Date returnDate) throws LendingNotFoundException, BookNotFoundException, MemberNotFoundException;

    public void returnBookLate(Long recordId, Date returnDate, BigDecimal finePayment) throws LendingNotFoundException, BookNotFoundException, MemberNotFoundException, FineNotPaidException;

}