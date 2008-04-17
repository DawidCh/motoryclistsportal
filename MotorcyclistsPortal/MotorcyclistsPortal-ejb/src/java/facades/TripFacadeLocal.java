/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package facades;

import entities.Trip;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Dawid
 */
@Local
public interface TripFacadeLocal {

    void create(Trip trip);

    void edit(Trip trip);

    void remove(Trip trip);

    Trip find(Object id);

    List<Trip> findAll();

}
