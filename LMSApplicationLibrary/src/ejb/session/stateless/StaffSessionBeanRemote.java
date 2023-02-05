
package ejb.session.stateless;

import entity.Staff;
import exception.InvalidLoginException;
import exception.StaffNotFoundException;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author ongyongen
 */
@Remote
public interface StaffSessionBeanRemote {
     
    public Long createNewStaff(Staff staff);

    public List<Staff> retrieveAllStaff();

    public Staff retrieveStaffByUsernameAndPassword(String username, String password) throws InvalidLoginException;

    public void updateStaff(Long staffId, String newFirstName, String newLastName, String newUserName, String newPassword) throws StaffNotFoundException;

    public void deleteStaff(Long staffId) throws StaffNotFoundException;

    public Staff retrieveStaffById(Long staffId) throws StaffNotFoundException;
}