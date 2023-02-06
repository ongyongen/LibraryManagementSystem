/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package lmsapplicationclient;

import ejb.session.stateless.MemberSessionBeanRemote;
import ejb.session.stateless.StaffSessionBeanRemote;
import entity.Member;
import exception.InputDataValidationException;
import exception.InvalidLoginException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;

/**
 *
 * @author ongyongen
 */
public class Main {

    @EJB
    private static MemberSessionBeanRemote memberSessionBeanRemote;

    @EJB
    private static StaffSessionBeanRemote staffSessionBeanRemote;
    

    public static void main(String[] args) {
        /*
        Test Function 1 : Login
        staffSessionBeanRemote.retrieveStaffByUsernameAndPassword("eric", "some");
        staffSessionBeanRemote.retrieveStaffByUsernameAndPassword("Eric", "Some");
        System.out.println(staffSessionBeanRemote.retrieveStaffByUsernameAndPassword("eric", "password"));
        */
        
        /* 
        Test Function 2 : Register Member
        Member member = new Member("Megan", "Tan", 'F', 20, "T1234560C", "90098672", "Blk 408 Tampines St 41");
        Member member = new Member("Megan", "Tan", 'F', 20, "T0046980C", "90098672", "Blk 408 Tampines St 41");
        Member member = new Member("Megan", "Tan", 'F', 20, "T0046980C", null, null);
        Member member = new Member("", " ", 'F', -10, "T0046980C", "", null);
        System.out.println(memberSessionBeanRemote.createNewMember(member));
        */
      
        
        
       
        
    }
    
}
