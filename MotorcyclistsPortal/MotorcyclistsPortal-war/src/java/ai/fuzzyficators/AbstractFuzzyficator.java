/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ai.fuzzyficators;

import ai.fuzzycomputers.FuzzyComputerInterface;
import ai.fuzzycomputers.TrapeziumComputer;
import fuzzyelements.FuzzyValue;
import fuzzyelements.Fuzzyficable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Dawid
 */
public abstract class AbstractFuzzyficator {

    /**
     * Object for computing Usage for specified
     * percentage usage of element.
     */
    protected FuzzyComputerInterface fuzzyComputer;
    /**
     * List of Double objects representing values of membership functions.
     */

    public AbstractFuzzyficator() {
        this.fuzzyComputer = new TrapeziumComputer();
    }

    /**
     * Method using for computing fuzzy value for given object.
     * @param object given for computing fuzzy value
     * @return fuzzy value represented by String
     * @throws java.lang.Exception
     */
    public abstract FuzzyValue processElement(Fuzzyficable object)
            throws Exception;

    /**
     * Method using for computing fuzzy value for collection.
     * @param objects arguments for computing
     * @return fuzzy values (FuzzyValue) for given attributes
     * @throws java.lang.Exception
     */
    public List < FuzzyValue > processCollection(
            final List < Fuzzyficable  > objects)
            throws Exception {
        List < FuzzyValue > result = new ArrayList < FuzzyValue >();
        for (int i = 0; i < objects.size(); i++) {
            Fuzzyficable object = objects.get(i);
            result.add(this.processElement(object));
        }
        return result;
    }
}
