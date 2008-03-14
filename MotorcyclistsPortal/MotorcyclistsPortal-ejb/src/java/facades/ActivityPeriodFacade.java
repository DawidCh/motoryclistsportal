/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package facades;

import entities.ActivityPeriod;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author kalosh
 */
@Stateless
public class ActivityPeriodFacade implements ActivityPeriodFacadeLocal {
    @PersistenceContext
    private EntityManager em;

    public void create(ActivityPeriod activityPeriod) {
        em.persist(activityPeriod);
    }

    public void edit(ActivityPeriod activityPeriod) {
        em.merge(activityPeriod);
    }

    public void remove(ActivityPeriod activityPeriod) {
        em.remove(em.merge(activityPeriod));
    }

    public ActivityPeriod find(Object id) {
        return em.find(entities.ActivityPeriod.class, id);
    }

    public List<ActivityPeriod> findAll() {
        return em.createQuery("select object(o) from ActivityPeriod as o").getResultList();
    }

}
