/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package facades;

import entities.FuzzyDecision;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Dawid
 */
@Local
public interface FuzzyDecisionFacadeLocal {

    FuzzyDecision find(Object id);

    List<FuzzyDecision> findAll();

}
