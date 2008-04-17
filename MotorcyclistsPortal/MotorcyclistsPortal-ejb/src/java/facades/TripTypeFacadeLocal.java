/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package facades;

import entities.TripType;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Dawid
 */
@Local
public interface TripTypeFacadeLocal {

    void create(TripType tripType);

    void edit(TripType tripType);

    void remove(TripType tripType);

    TripType find(Object id);

    List<TripType> findAll();

}
