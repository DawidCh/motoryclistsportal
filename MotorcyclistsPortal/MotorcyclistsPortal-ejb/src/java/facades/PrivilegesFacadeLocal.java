/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package facades;

import entities.Privileges;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Dawid
 */
@Local
public interface PrivilegesFacadeLocal {

    void create(Privileges privileges);

    void edit(Privileges privileges);

    void remove(Privileges privileges);

    Privileges find(Object id);

    List<Privileges> findAll();

    public entities.Privileges findByDesc(java.lang.String desc);

}
