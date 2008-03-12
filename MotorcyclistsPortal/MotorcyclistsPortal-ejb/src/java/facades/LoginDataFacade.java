/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package facades;

import entities.LoginData;
import java.util.Calendar;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import utils.MPException;

/**
 *
 * @author kalosh
 */
@Stateless
public class LoginDataFacade implements LoginDataFacadeLocal {
    @PersistenceContext
    private EntityManager em;

    public void create(LoginData loginData) throws MPException {
        LoginData ld = this.find(loginData.getLogin());
        if(null == ld){
            em.persist(loginData);
            return;
        }
        throw new MPException(MPException.LOGIN_EXISTS);
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
