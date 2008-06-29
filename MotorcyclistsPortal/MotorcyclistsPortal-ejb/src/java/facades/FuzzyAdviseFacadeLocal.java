/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package facades;

import entities.FuzzyAdvise;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Dawid
 */
@Local
public interface FuzzyAdviseFacadeLocal {

    FuzzyAdvise find(Object id);

    List<FuzzyAdvise> findAll();

}
