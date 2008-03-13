/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package facades;

import entities.AvailableLangs;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author kalosh
 */
@Stateless
public class AvailableLangsFacade implements AvailableLangsFacadeLocal {
    @PersistenceContext
    private EntityManager em;

    public void create(AvailableLangs availableLangs) {
        em.persist(availableLangs);
    }

    public void edit(AvailableLangs availableLangs) {
        em.merge(availableLangs);
    }

    public void remove(AvailableLangs availableLangs) {
        em.remove(em.merge(availableLangs));
    }

    public AvailableLangs find(Object id) {
        return em.find(entities.AvailableLangs.class, id);
    }

    public List<AvailableLangs> findAll() {
        return em.createQuery("select object(o) from AvailableLangs as o").getResultList();
    }

}
