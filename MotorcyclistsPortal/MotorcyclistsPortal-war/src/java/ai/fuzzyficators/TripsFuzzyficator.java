/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ai.fuzzyficators;

import ai.FuzzyDriver;
import entities.Distance;
import fuzzyelements.Fuzzyficable;
import fuzzyelements.TrapeziumMembershipFunctionInterface;
import entities.Trip;
import java.util.ArrayList;
import java.util.List;
import utils.BeanGetter;
import utils.MPException;

/**
 *
 * @author kalosh
 */
public class TripsFuzzyficator extends AbstractFuzzyficator {

    /**
     * Method using for computing fuzzy value for given Trip.
     * @param object given for computing fuzzy value
     * @return fuzzy value represented by String
     * @throws java.lang.Exception
     */
    @Override
    public final Distance processElement(Fuzzyficable object) throws Exception {
        if (!(object instanceof Trip)) {
            throw new MPException("Object passed to"
                    + "TripsFuzzyficator.processElement"
                    + "is not properly");
        }
        Trip trip = (Trip) object;
        List < TrapeziumMembershipFunctionInterface > distances =
                //Distance objects in the list
                new ArrayList < TrapeziumMembershipFunctionInterface > (
                BeanGetter.lookupDistanceFacade().findAll());
        Distance distanceResults = (Distance) FuzzyDriver.
                getTrapeziumFuzzySetForValue(
                distances, trip.getDistance());
        return distanceResults;
    }
}
