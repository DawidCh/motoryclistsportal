/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package facades;

import entities.FuzzyDecision;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author kalosh
 */
@Stateless
public class FuzzyDecisionFacade implements FuzzyDecisionFacadeLocal {
    @PersistenceContext
    private EntityManager em;

    public void create(FuzzyDecision fuzzyDecision) {
        em.persist(fuzzyDecision);
    }

    public void edit(FuzzyDecision fuzzyDecision) {
        em.merge(fuzzyDecision);
    }

    public void remove(FuzzyDecision fuzzyDecision) {
        em.remove(em.merge(fuzzyDecision));
    }

    public FuzzyDecision find(Object id) {
        return em.find(entities.FuzzyDecision.class, id);
    }

    public List<FuzzyDecision> findAll() {
        return em.createQuery("select object(o) from FuzzyDecision as o").getResultList();
    }

}
