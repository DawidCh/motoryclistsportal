/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package facades;

import entities.FishiersElement;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author kalosh
 */
@Stateless
public class FishiersElementFacade implements FishiersElementFacadeLocal {
    @PersistenceContext
    private EntityManager em;

    public void create(FishiersElement fishiesrElement) {
        em.persist(fishiesrElement);
    }

    public void edit(FishiersElement fishiesrElement) {
        em.merge(fishiesrElement);
    }

    public void remove(FishiersElement fishiesrElement) {
        em.remove(em.merge(fishiesrElement));
    }

    public FishiersElement find(Object id) {
        return em.find(entities.FishiersElement.class, id);
    }

    public List<FishiersElement> findAll() {
        return em.createQuery("select object(o) from FishiersElement as o").getResultList();
    }

}
