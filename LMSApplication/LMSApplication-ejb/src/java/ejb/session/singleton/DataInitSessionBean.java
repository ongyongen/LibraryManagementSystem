
package ejb.session.singleton;

import ejb.session.stateless.BookSessionBeanLocal;
import ejb.session.stateless.LendAndReturnSessionBeanLocal;
import ejb.session.stateless.MemberSessionBeanLocal;
import ejb.session.stateless.StaffSessionBeanLocal;
import entity.Book;
import entity.LendAndReturn;
import entity.Member;
import entity.Staff;
import exception.BookNotAvailableException;
import exception.BookNotFoundException;
import exception.MemberNotFoundException;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.LocalBean;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author ongyongen
 */
@Singleton
@LocalBean
@Startup
public class DataInitSessionBean {

    @EJB
    private LendAndReturnSessionBeanLocal lendAndReturnSessionBeanLocal;

    @EJB
    private MemberSessionBeanLocal memberSessionBeanLocal;

    @EJB
    private BookSessionBeanLocal bookSessionBeanLocal;

    @EJB
    private StaffSessionBeanLocal staffSessionBeanLocal;
    
    
    

    @PersistenceContext(unitName = "LMSApplication-ejbPU")
    private EntityManager em;
    
    @PostConstruct
    public void postConstruct() {
        if (em.find(Staff.class, 1l) == null) {
            try {
                Staff staff1 = new Staff("Eric", "Some", "eric", "password");
                Staff staff2 = new Staff("Sarah", "Brightman", "sarah", "password");
                staffSessionBeanLocal.createNewStaff(staff1);
                staffSessionBeanLocal.createNewStaff(staff2);
                
                Book book1 = new Book("Anna Karenina", "0451528611", "Leo Tolstoy");
                Book book2 = new Book("Madame Bovary", "979-8649042031", "Gustave Flaubert");
                Book book3 = new Book("Hamlet", "1980625026", "William Shakespeare");
                Book book4 = new Book("The Hobbit", "9780007458424", "J R R Tolkien");
                Book book5 = new Book("Great Expectations", "1521853592", "Charles Dickens");
                Book book6 = new Book("Pride and Prejudice", "979-8653642272", "Jane Austen");
                Book book7 = new Book("Wuthering Heights", "3961300224", "Emily Brontë");
                bookSessionBeanLocal.createNewBook(book1);
                bookSessionBeanLocal.createNewBook(book2);
                bookSessionBeanLocal.createNewBook(book3);
                bookSessionBeanLocal.createNewBook(book4);
                bookSessionBeanLocal.createNewBook(book5);
                bookSessionBeanLocal.createNewBook(book6);
                bookSessionBeanLocal.createNewBook(book7);
                
                Member member1 = new Member("Tony", "Shade", 'M', 31, "S8900678A", "83722773", "13 Jurong East, Ave 3");
                Member member2 = new Member("Dewi", "Tan", 'F', 35, "S8581028X", "94602711", "15 Computing Dr");
                memberSessionBeanLocal.createNewMember(member1);
                memberSessionBeanLocal.createNewMember(member2);
                
                
                Format formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                String dateString = formatter.format(new Date());
                Date date =  new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(dateString);  


                lendAndReturnSessionBeanLocal.createLendingRecord("S8900678A", "Anna Karenina");
                lendAndReturnSessionBeanLocal.createLendingRecord("S8900678A", "Madame Bovary");
                lendAndReturnSessionBeanLocal.createLendingRecord("S8900678A", "Hamlet");
                lendAndReturnSessionBeanLocal.createLendingRecord("S8581028X", "The Hobbit");
                lendAndReturnSessionBeanLocal.createLendingRecord("S8581028X", "Great Expectations");

            } catch (MemberNotFoundException | BookNotFoundException | BookNotAvailableException | ParseException ex) {
                System.out.println("Error");
            }

            
        }
        
    }

    

    
}