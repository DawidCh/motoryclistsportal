/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package facades;

import entities.Motorcycle;
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
public class MotorcycleFacade implements MotorcycleFacadeLocal {
    @PersistenceContext
    private EntityManager em;

    public void create(Motorcycle motorcycle) {
        em.persist(motorcycle);
    }

    public void edit(Motorcycle motorcycle) {
        em.merge(motorcycle);
    }

    public void remove(Motorcycle motorcycle) {
        em.remove(em.merge(motorcycle));
    }

    public Motorcycle find(Object id) {
        return em.find(entities.Motorcycle.class, id);
    }
    
    public List<Motorcycle> findByLogin(String login){
        return em.createQuery("select object(o) from Motorcycle as o where o.login.login='"+login+"'").getResultList();
    }

    public List<Motorcycle> findAll() {
        return em.createQuery("select object(o) from Motorcycle as o").getResultList();
    }

}
