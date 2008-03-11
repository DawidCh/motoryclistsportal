/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package facades;

import entities.LoginData;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Dawid
 */
@Local
public interface LoginDataFacadeLocal {

    void create(LoginData loginData);

    void edit(LoginData loginData);

    void remove(LoginData loginData);

    LoginData find(Object id);

    List<LoginData> findAll();

}
