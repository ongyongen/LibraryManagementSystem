/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/SessionLocal.java to edit this template
 */
package ejb.session.stateless;

import entity.LendAndReturn;
import exception.BookNotAvailableException;
import exception.BookNotFoundException;
import exception.FineNotPaidException;
import exception.InputDataValidationException;
import exception.LendingNotFoundException;
import exception.MemberNotFoundException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author ongyongen
 */
@Local
public interface LendAndReturnSessionBeanLocal {

    public Long createLendingRecord(String memberIdentityNo, String bookTitle, Date currentDate) throws MemberNotFoundException, BookNotFoundException, BookNotAvailableException, InputDataValidationException;    

    public LendAndReturn retrieveLendingRecordByIdNoAndTitle(String idNo, String title) throws LendingNotFoundException, BookNotFoundException, MemberNotFoundException;

    public List<LendAndReturn> retrieveAllLendingRecords();

    public LendAndReturn retrieveLendingRecordById(Long recordId) throws LendingNotFoundException;

    public BigDecimal calculateFineAmount(Date currentDate, Date lendDate);

    public BigDecimal retrieveFineAmountForRecord(Long recordId, Date currentDate) throws LendingNotFoundException, BookNotFoundException, MemberNotFoundException;

    public void returnBookNotLate(Long recordId, Date returnDate) throws LendingNotFoundException, BookNotFoundException, MemberNotFoundException;

    public void returnBookLate(Long recordId, Date returnDate, BigDecimal finePayment) throws LendingNotFoundException, BookNotFoundException, MemberNotFoundException, FineNotPaidException;
    
}