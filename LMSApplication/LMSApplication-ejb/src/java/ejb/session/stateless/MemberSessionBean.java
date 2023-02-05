
package ejb.session.stateless;

import entity.Member;
import exception.MemberNotFoundException;
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
public class MemberSessionBean implements MemberSessionBeanRemote, MemberSessionBeanLocal {

    @PersistenceContext(unitName = "LMSApplication-ejbPU")
    private EntityManager em;
    
    @Override
    public Long createNewMember(Member member) {
        em.persist(member);
        em.flush();
        return member.getMemberId();
    }
    
    @Override
    public List<Member> retrieveAllMembers() {
        String queryString = "SELECT m FROM Member m";
        Query query = em.createQuery(queryString);
        return query.getResultList();
    }
    
    @Override
    public Member retrieveMemberByIdentityNo(String identityNo) throws MemberNotFoundException {
           
        Query query = em.createQuery("SELECT m FROM Member m "
                + "WHERE m.identityNo = :identityNo");
              
        query.setParameter("identityNo", identityNo);
        
        if (query.getResultList().isEmpty()) {
            throw new MemberNotFoundException();
        } else {
            return (Member)query.getSingleResult();
        }
        
    }
}