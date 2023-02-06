/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package manager;

import ejb.session.stateless.StaffSessionBeanLocal;
import entity.Staff;
import exception.InvalidLoginException;
import exception.StaffNotFoundException;

/**
 *
 * @author ongyongen
 */
public class StaffManager {
    private StaffSessionBeanLocal staffSessionBeanLocal;
    
    public StaffManager() {
        
    }
    
    public StaffManager(StaffSessionBeanLocal staffSessionBeanLocal) {
        this.staffSessionBeanLocal = staffSessionBeanLocal;
    }
    
    public Staff getStaff(Long cId) throws StaffNotFoundException {
        return staffSessionBeanLocal.retrieveStaffById(cId);
    }
    
    public Staff loginStaff(String username, String password) throws InvalidLoginException {
        return staffSessionBeanLocal.retrieveStaffByUsernameAndPassword(username, password);
    }
    
    
}
