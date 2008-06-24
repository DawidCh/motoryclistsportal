/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ai.fuzzyficators;

import entities.Trip;
import java.util.ArrayList;
import java.util.List;
import utils.BeanGetter;

/**
 *
 * @author kalosh
 */
public class TripsFuzzyficator extends Fuzzyficator {

    /**
     * Method using for computing fuzzy value for given Trip.
     * @param object given for computing fuzzy value
     * @return fuzzy value represented by String
     * @throws java.lang.Exception
     */
    @Override
    public final String processElement(Object object) throws Exception {
        //todo:zaimplementować metodę wziąć pod uwagę Fuzzyficator i jego
        //pola. Napisać fuzzycomputers i tripscomputers
        Double result = 0.0;
        this.results.add(result);
        return BeanGetter.lookupDistanceFacade().findLowest().getDescription();
    }

    /**
     * Method using for computing fuzzy value for collection.
     * @param objects arguments for computing
     * @return fuzzy values for given attributes
     * @throws java.lang.Exception
     */
    @Override
    public List<String> processCollection(List objects) throws Exception {
        Double averageDistance = 0.0;
        List < String > result = new ArrayList < String >();
        this.results = new ArrayList < Double >();
        result.add("");
        for (int i = 0; i < objects.size(); i++) {
            Trip trip = (Trip) objects.get(i);
            result.add(this.processElement(trip));
            averageDistance += trip.getDistance();
        }
        averageDistance /= objects.size();
        result.set(0, this.processElement(averageDistance));
        return result;
    }
}
