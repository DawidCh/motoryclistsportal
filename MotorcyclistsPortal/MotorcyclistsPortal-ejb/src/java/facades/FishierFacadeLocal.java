/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package facades;

import entities.Fishier;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Dawid
 */
@Local
public interface FishierFacadeLocal {

    void create(Fishier fishier);

    void edit(Fishier fishier);

    void remove(Fishier fishier);

    Fishier find(Object id);

    List<Fishier> findAll();

    public java.util.List<entities.Fishier> findByLogin(java.lang.String login);

}
