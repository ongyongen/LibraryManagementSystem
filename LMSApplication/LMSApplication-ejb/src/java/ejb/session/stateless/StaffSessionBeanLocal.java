
package ejb.session.stateless;

import entity.Staff;
import exception.InputDataValidationException;
import exception.InvalidLoginException;
import exception.StaffNotFoundException;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author ongyongen
 */
@Local
public interface StaffSessionBeanLocal {

    public Long createNewStaff(Staff staff) throws InputDataValidationException;

    public List<Staff> retrieveAllStaff();

    public Staff retrieveStaffByUsernameAndPassword(String username, String password) throws InvalidLoginException;

    public void updateStaff(Long staffId, String newFirstName, String newLastName, String newUserName, String newPassword) throws StaffNotFoundException;

    public void deleteStaff(Long staffId) throws StaffNotFoundException;

    public Staff retrieveStaffById(Long staffId) throws StaffNotFoundException;
    
}