/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package facades;

import entities.Usage;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author kalosh
 */
@Stateless
public class UsageFacade implements UsageFacadeLocal {
    @PersistenceContext
    private EntityManager em;

    public void create(Usage usage) {
        em.persist(usage);
    }

    public void edit(Usage usage) {
        em.merge(usage);
    }

    public void remove(Usage usage) {
        em.remove(em.merge(usage));
    }

    public Usage find(Object id) {
        return em.find(entities.Usage.class, id);
    }

    public List<Usage> findAll() {
        return em.createQuery("select object(o) from Usage as o").getResultList();
    }

    public Usage findHardest() {
        return (Usage) em.createQuery("" +
                "select object(o) from Usage as o where o.id=5" +
                "").getSingleResult();
    }
    
    public Usage findLowest() {
        return (Usage) em.createQuery("" +
                "select object(o) from Usage as o where o.id=1" +
                "").getSingleResult();
    }

}
