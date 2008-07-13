/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package facades;

import entities.FishierElementBridge;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Dawid Chojnacki
 */
@Stateless
public class FishierElementBridgeFacade implements FishierElementBridgeFacadeLocal {
    @PersistenceContext
    private EntityManager em;

    public void create(FishierElementBridge fishierElementBridge) {
        em.persist(fishierElementBridge);
    }

    public void edit(FishierElementBridge fishierElementBridge) {
        em.merge(fishierElementBridge);
    }

    public void remove(FishierElementBridge fishierElementBridge) {
        em.remove(em.merge(fishierElementBridge));
    }

    public FishierElementBridge find(Object id) {
        return em.find(entities.FishierElementBridge.class, id);
    }
    
    public List<FishierElementBridge> findAllByFishier(String fishierId) {
        return em.createQuery("select object(o) from FishierElementBridge as o where " +
                "o.fishier.id='"+fishierId+"'").getResultList();
    }

    public List<FishierElementBridge> findAll() {
        return em.createQuery("select object(o) from FishierElementBridge as o").getResultList();
    }

}
