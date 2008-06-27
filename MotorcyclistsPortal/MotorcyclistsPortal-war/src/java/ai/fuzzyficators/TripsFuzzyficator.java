/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ai.fuzzyficators;

import entities.Distance;
import entities.Trip;
import java.util.ArrayList;
import java.util.List;
import utils.BeanGetter;
import utils.MPException;
import utils.MPUtilities;

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
        if (!(object instanceof Trip)) {
            throw new MPException("Object passed to"
                    + "FishierElementBridgheFuzzyficator.processElement"
                    + "is not properly");
        }
        Trip trip = (Trip) object;
        List distances =
                BeanGetter.lookupDistanceFacade().findAll();
        Distance distanceResults = (Distance) MPUtilities.getFuzzySetForValue(
                distances, trip.getDistance());
        return distanceResults.getDescription();
    }

    /**
     * Method using for computing fuzzy value for collection.
     * @param objects arguments for computing
     * @return fuzzy values for given attributes
     * @throws java.lang.Exception
     */
    @Override
    public List<String> processCollection(List objects) throws Exception {
        List < String > result = new ArrayList < String > ();
        //this.results = new ArrayList < Double >();
        for (int i = 0; i < objects.size(); i++) {
            Trip trip = (Trip) objects.get(i);
            result.add(this.processElement(trip));
        }
        return result;
    }
}
