/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package facades;

import entities.Fishier;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author kalosh
 */
@Stateless
public class FishierFacade implements FishierFacadeLocal {
    @PersistenceContext
    private EntityManager em;

    public void create(Fishier fishier) {
        em.persist(fishier);
    }

    public void edit(Fishier fishier) {
        em.merge(fishier);
    }

    public void remove(Fishier fishier) {
        em.remove(em.merge(fishier));
    }

    public Fishier find(Object id) {
        return em.find(entities.Fishier.class, id);
    }

    public List<Fishier> findAll() {
        return em.createQuery("select object(o) from Fishier as o").getResultList();
    }

}
