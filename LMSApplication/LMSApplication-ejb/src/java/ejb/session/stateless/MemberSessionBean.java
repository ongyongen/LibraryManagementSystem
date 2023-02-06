
package ejb.session.stateless;

import entity.Member;
import exception.InputDataValidationException;
import exception.MemberNotFoundException;
import java.util.List;
import java.util.Set;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;
import javax.validation.Validator;

/**
 *
 * @author ongyongen
 */
@Stateless
public class MemberSessionBean implements MemberSessionBeanRemote, MemberSessionBeanLocal {

    @PersistenceContext(unitName = "LMSApplication-ejbPU")
    private EntityManager em;
    
    private final ValidatorFactory validatorFactory;
    private final Validator validator;

    public MemberSessionBean() {
        this.validatorFactory = Validation.buildDefaultValidatorFactory();
        this.validator = validatorFactory.getValidator();
    }
    
    @Override
    public Long createNewMember(Member member) throws InputDataValidationException {
        Set<ConstraintViolation<Member>> constraintViolations = validator.validate(member);
        if (constraintViolations.isEmpty()) {
            em.persist(member);
            em.flush();
            return member.getMemberId();
        } else {
            throw new InputDataValidationException(prepareInputDataValidationErrorMsg(constraintViolations));
        }
    }
    
    private String prepareInputDataValidationErrorMsg(Set<ConstraintViolation<Member>> violations) {
        String msg = "Input data validation error :";

        for (ConstraintViolation violation : violations) {
            msg += "\n" + violation.getPropertyPath() + " - " + violation.getMessage();
        }

        return msg;
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