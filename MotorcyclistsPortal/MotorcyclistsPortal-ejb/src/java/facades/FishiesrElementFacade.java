/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package facades;

import entities.FishiesrElement;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author kalosh
 */
@Stateless
public class FishiesrElementFacade implements FishiesrElementFacadeLocal {
    @PersistenceContext
    private EntityManager em;

    public void create(FishiesrElement fishiesrElement) {
        em.persist(fishiesrElement);
    }

    public void edit(FishiesrElement fishiesrElement) {
        em.merge(fishiesrElement);
    }

    public void remove(FishiesrElement fishiesrElement) {
        em.remove(em.merge(fishiesrElement));
    }

    public FishiesrElement find(Object id) {
        return em.find(entities.FishiesrElement.class, id);
    }

    public List<FishiesrElement> findAll() {
        return em.createQuery("select object(o) from FishiesrElement as o").getResultList();
    }

}
