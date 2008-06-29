/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package facades;

import entities.FuzzyAdvise;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author kalosh
 */
@Stateless
public class FuzzyAdviseFacade implements FuzzyAdviseFacadeLocal {
    @PersistenceContext
    private EntityManager em;

    public void create(FuzzyAdvise fuzzyAdvise) {
        em.persist(fuzzyAdvise);
    }

    public void edit(FuzzyAdvise fuzzyAdvise) {
        em.merge(fuzzyAdvise);
    }

    public void remove(FuzzyAdvise fuzzyAdvise) {
        em.remove(em.merge(fuzzyAdvise));
    }

    public FuzzyAdvise find(Object id) {
        return em.find(entities.FuzzyAdvise.class, id);
    }

    public List<FuzzyAdvise> findAll() {
        return em.createQuery("select object(o) from FuzzyAdvise as o").getResultList();
    }

}
