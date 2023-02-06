
package ejb.session.stateless;

import entity.Member;
import exception.InputDataValidationException;
import exception.MemberNotFoundException;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author ongyongen
 */
@Remote
public interface MemberSessionBeanRemote {
    
    public Long createNewMember(Member member) throws InputDataValidationException;

    public List<Member> retrieveAllMembers();
    
    public Member retrieveMemberByIdentityNo(String identityNo) throws MemberNotFoundException;

}