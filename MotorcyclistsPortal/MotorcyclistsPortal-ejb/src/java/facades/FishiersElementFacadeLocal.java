/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package facades;

import entities.FishiersElement;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Dawid
 */
@Local
public interface FishiersElementFacadeLocal {

    void create(FishiersElement fishiesrElement);

    void edit(FishiersElement fishiesrElement);

    void remove(FishiersElement fishiesrElement);

    FishiersElement find(Object id);

    List<FishiersElement> findAll();

}
