/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package facades;

import entities.AvailableLangs;
import java.util.List;
import java.util.Locale;
import javax.ejb.Local;

/**
 *
 * @author Dawid
 */
@Local
public interface AvailableLangsFacadeLocal {

    void create(AvailableLangs availableLangs);

    void edit(AvailableLangs availableLangs);

    void remove(AvailableLangs availableLangs);

    AvailableLangs find(Object id);

    List<Locale> findAll();

}
