/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ai.fuzzycomputers;

import entities.Usage;
import java.util.ArrayList;
import java.util.List;
import utils.MPException;
import utils.MPLogger;

/**
 *
 * @author kalosh
 */
public class TrapeziumComputer implements FuzzyComputerInterface{

    
    /**
     * Method which computes fuzzy value for given value and trapezium membership
     * function.
     * @param argument is a list which contains: as first element value to fuzzyfication, 
     * rest elements are Usages
     * @throws Exception
     * @return entities.Usage which fits most to given argument
     */
    public Usage extractFuzzyValue(Object argument) throws Exception {
        if(!(argument instanceof List))
            throw new MPException("Object passed to TrapeziumComputer.processFuzzyfication is not properly");
        List parameters = (List) argument;
        Double percentageValue = (Double) parameters.get(0);
        List<Usage> usages = new ArrayList<Usage>();
        for(int i=1;i<parameters.size();i++){
            usages.add((Usage)parameters.get(i));
        }
        //todo:only for tests
        return usages.get(0);
    }
}
