/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package facades;

import entities.FishiesrElement;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Dawid
 */
@Local
public interface FishiesrElementFacadeLocal {

    void create(FishiesrElement fishiesrElement);

    void edit(FishiesrElement fishiesrElement);

    void remove(FishiesrElement fishiesrElement);

    FishiesrElement find(Object id);

    List<FishiesrElement> findAll();

}
