/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package facades;

import entities.Privileges;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Dawid Chojnacki
 */
@Stateless
public class PrivilegesFacade implements PrivilegesFacadeLocal {
    @PersistenceContext
    private EntityManager em;

    public void create(Privileges privileges) {
        em.persist(privileges);
    }

    public void edit(Privileges privileges) {
        em.merge(privileges);
    }

    public void remove(Privileges privileges) {
        em.remove(em.merge(privileges));
    }

    public Privileges find(Object id) {
        return em.find(entities.Privileges.class, id);
    }
    
    public Privileges findByDesc(String desc)
    {
        return (Privileges) em.createQuery("select object(o) from Privileges as o where o.description='"+desc+"'").
                getSingleResult();
    }

    public List<Privileges> findAll() {
        return em.createQuery("select object(o) from Privileges as o").getResultList();
    }

}
