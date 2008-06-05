/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package facades;

import entities.TripType;
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

    public java.util.List<entities.TripType> findAll();

    public entities.TripType toTripType(java.lang.String trip) throws utils.MPException;

}
