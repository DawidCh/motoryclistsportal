/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package facades;

import entities.Distance;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Dawid
 */
@Local
public interface DistanceFacadeLocal {

    Distance find(Object id);

    List<Distance> findAll();
    
    Distance findHighest();

    Distance findLowest();
}
