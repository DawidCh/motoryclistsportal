/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package facades;

import entities.Motorcycle;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Dawid
 */
@Local
public interface MotorcycleFacadeLocal {

    void create(Motorcycle motorcycle);

    void edit(Motorcycle motorcycle);

    void remove(Motorcycle motorcycle);

    Motorcycle find(Object id);

    List<Motorcycle> findAll();

    public java.util.List<entities.Motorcycle> findByLogin(String login);

    public java.util.List<entities.Motorcycle> findByFishier(entities.Fishier fishier);

}
