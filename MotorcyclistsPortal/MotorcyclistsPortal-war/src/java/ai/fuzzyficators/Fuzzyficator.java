/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ai.fuzzyficators;

import ai.fuzzycomputers.FuzzyComputerInterface;
import ai.fuzzycomputers.TrapeziumComputer;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Dawid
 */
public abstract class Fuzzyficator {

    /**
     * Object for computing Usage for specified
     * percentage usage of element.
     */
    protected FuzzyComputerInterface fuzzyComputer;
    /**
     * List of Double objects representing values of membership functions
     */
    //protected List <Double> results;

    public Fuzzyficator() {
        this.fuzzyComputer = new TrapeziumComputer();
    }
    
    /**
     * Method using for computing fuzzy value for given object.
     * @param object given for computing fuzzy value
     * @return fuzzy value represented by String
     * @throws java.lang.Exception
     */
    public abstract String processElement(Object object) throws Exception;

    /**
     * Method using for computing fuzzy value for collection.
     * @param objects arguments for computing
     * @return fuzzy values for given attributes
     * @throws java.lang.Exception
     */
    public List < String > processCollection(final List objects)
            throws Exception {
        List < String > result = new ArrayList < String >();
        //this.results = new ArrayList < Double >();
        for (int i = 0; i < objects.size(); i++) {
            Object object = objects.get(i);
            result.add(this.processElement(object));
        }
        return result;
    }
}
