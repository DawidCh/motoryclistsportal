/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package facades;

import entities.User;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author kalosh
 */
@Stateless
public class UserFacade implements UserFacadeLocal {
    @PersistenceContext
    private EntityManager em;

    public void create(User user) {
        em.persist(user);
    }

    public void edit(User user) {
        em.merge(user);
    }

    public void remove(User user) {
        em.remove(em.merge(user));
    }

    public User find(Object id) {
        return em.find(entities.User.class, id);
    }

    public List<User> findAll() {
        return em.createQuery("select object(o) from User as o").getResultList();
    }

}
