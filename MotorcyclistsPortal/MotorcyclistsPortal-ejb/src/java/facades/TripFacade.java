/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package facades;

import entities.Trip;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author kalosh
 */
@Stateless
public class TripFacade implements TripFacadeLocal {
    @PersistenceContext
    private EntityManager em;

    public void create(Trip trip) {
        em.persist(trip);
    }

    public void edit(Trip trip) {
        em.merge(trip);
    }

    public void remove(Trip trip) {
        em.remove(em.merge(trip));
    }

    public Trip find(Object id) {
        return em.find(entities.Trip.class, id);
    }

    public List<Trip> findAll() {
        return em.createQuery("select object(o) from Trip as o").getResultList();
    }

}
