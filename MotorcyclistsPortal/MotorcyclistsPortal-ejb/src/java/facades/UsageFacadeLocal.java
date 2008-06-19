/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package facades;

import entities.Usage;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Dawid
 */
@Local
public interface UsageFacadeLocal {

    public Usage findHardest();
    
    public Usage findLowest();

    Usage find(Object id);

    List<Usage> findAll();

}
