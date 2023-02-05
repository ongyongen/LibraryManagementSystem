/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatelessEjbClass.java to edit this template
 */
package ejb.session.stateless;

import entity.Staff;
import exception.InvalidLoginException;
import exception.StaffNotFoundException;
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
public class StaffSessionBean implements StaffSessionBeanRemote, StaffSessionBeanLocal {

    @PersistenceContext(unitName = "LMSApplication-ejbPU")
    private EntityManager em;

    @Override
    public Long createNewStaff(Staff staff) {
        em.persist(staff);
        em.flush();
        return staff.getStaffId();
    }
    
    @Override
    public List<Staff> retrieveAllStaff() {
        String queryString = "SELECT s FROM Staff s";
        Query query = em.createQuery(queryString);
        return query.getResultList();
    }
    
    @Override
    public Staff retrieveStaffById(Long staffId) throws StaffNotFoundException {
        if (em.find(Staff.class, staffId) == null) {
            throw new StaffNotFoundException();
        } else {
            Staff staff = em.find(Staff.class, staffId);
            return staff;
        }
    }
    
    @Override
    public Staff retrieveStaffByUsernameAndPassword(String username, String password) throws InvalidLoginException {
        
        Query query = em.createQuery("SELECT s FROM Staff s "
                + "WHERE s.userName = :username "
                + "AND s.password = :password");
        query.setParameter("username", username);
        query.setParameter("password", password);
        
        if (query.getResultList().isEmpty()) {
            throw new InvalidLoginException();
        } else {
            return (Staff)query.getSingleResult();
        }
    }
    
    @Override
    public void updateStaff(Long staffId, String newFirstName, String newLastName, String newUserName, String newPassword) throws StaffNotFoundException {
        Staff staff = this.retrieveStaffById(staffId);
        staff.setFirstName(newFirstName);
        staff.setLastName(newLastName);
        staff.setUserName(newUserName);
        staff.setPassword(newPassword);    
    }
    
    @Override
    public void deleteStaff(Long staffId) throws StaffNotFoundException {
        Staff staff = this.retrieveStaffById(staffId);
        em.remove(staff);
    }
    
}