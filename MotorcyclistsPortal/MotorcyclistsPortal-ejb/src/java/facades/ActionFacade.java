/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package facades;

import entities.Action;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Dawid Chojnacki
 */
@Stateless
public class ActionFacade implements ActionFacadeLocal {
    @PersistenceContext
    private EntityManager em;

    public void create(Action action) {
        em.persist(action);
    }

    public void edit(Action action) {
        em.merge(action);
    }

    public void remove(Action action) {
        em.remove(em.merge(action));
    }

    public Action find(Object id) {
        return em.find(entities.Action.class, id);
    }

    public List<Action> findAll() {
        return em.createQuery("select object(o) from Action as o").getResultList();
    }

}
