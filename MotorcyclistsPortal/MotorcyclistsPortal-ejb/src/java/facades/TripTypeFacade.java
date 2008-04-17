/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package facades;

import entities.TripType;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author kalosh
 */
@Stateless
public class TripTypeFacade implements TripTypeFacadeLocal {
    @PersistenceContext
    private EntityManager em;

    public void create(TripType tripType) {
        em.persist(tripType);
    }

    public void edit(TripType tripType) {
        em.merge(tripType);
    }

    public void remove(TripType tripType) {
        em.remove(em.merge(tripType));
    }

    public TripType find(Object id) {
        return em.find(entities.TripType.class, id);
    }

    public List<TripType> findAll() {
        return em.createQuery("select object(o) from TripType as o").getResultList();
    }

}
