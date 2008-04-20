/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package facades;

import entities.FishierElementBridge;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Dawid
 */
@Local
public interface FishierElementBridgeFacadeLocal {

    void create(FishierElementBridge fishierElementBridge);

    void edit(FishierElementBridge fishierElementBridge);

    void remove(FishierElementBridge fishierElementBridge);

    FishierElementBridge find(Object id);

    List<FishierElementBridge> findAll();

    public java.util.List<entities.FishierElementBridge> findAllByFishier(java.lang.String fishierId);

}
