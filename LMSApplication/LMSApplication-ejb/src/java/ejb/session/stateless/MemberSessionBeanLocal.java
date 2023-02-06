/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/SessionLocal.java to edit this template
 */
package ejb.session.stateless;

import entity.Member;
import exception.InputDataValidationException;
import exception.MemberNotFoundException;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author ongyongen
 */
@Local
public interface MemberSessionBeanLocal {

    public Long createNewMember(Member member) throws InputDataValidationException;

    public List<Member> retrieveAllMembers();

    public Member retrieveMemberByIdentityNo(String identityNo) throws MemberNotFoundException;
    
}