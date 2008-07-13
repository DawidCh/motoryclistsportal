/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package facades;

import entities.Distance;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Dawid Chojnacki
 */
@Stateless
public class DistanceFacade implements DistanceFacadeLocal {
    @PersistenceContext
    private EntityManager em;

    public void create(Distance distance) {
        em.persist(distance);
    }

    public void edit(Distance distance) {
        em.merge(distance);
    }

    public void remove(Distance distance) {
        em.remove(em.merge(distance));
    }

    public Distance find(Object id) {
        return em.find(entities.Distance.class, id);
    }

    public List<Distance> findAll() {
        return em.createQuery("select object(o) from Distance as o").getResultList();
    }
    
    public Distance findHighest(){
        return (Distance) em.createQuery("select object(o) from Distance as o where o.id=5").getSingleResult();
    }

    public Distance findLowest(){
        return (Distance) em.createQuery("select object(o) from Distance as o where o.id=1").getSingleResult();
    }
}
