/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package facades;

import entities.LoginData;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author kalosh
 */
@Stateless
public class LoginDataFacade implements LoginDataFacadeLocal {
    @PersistenceContext
    private EntityManager em;

    public void create(LoginData loginData) {
        em.persist(loginData);
    }

    public void edit(LoginData loginData) {
        em.merge(loginData);
    }

    public void remove(LoginData loginData) {
        em.remove(em.merge(loginData));
    }

    public LoginData find(Object id) {
        return em.find(entities.LoginData.class, id);
    }

    public List<LoginData> findAll() {
        return em.createQuery("select object(o) from LoginData as o").getResultList();
    }

}
