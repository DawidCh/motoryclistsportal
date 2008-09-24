/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ai.fuzzyficators;

import ai.FuzzyDriver;
import entities.Distance;
import fuzzyelements.Fuzzyficable;
import fuzzyelements.TrapeziumMembershipFunction;
import entities.Trip;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import utils.BeanGetter;
import utils.MPException;

/**
 * Class wich computes Distance (fuzzy value) for given trip (Fuzzyficable).
 * @author Dawid Chojnacki
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
        Logger.getLogger("E").trace("Entering to: processElement");
        if (!(object instanceof Trip)) {
            throw new MPException("Object passed to"
                    + "TripsFuzzyficator.processElement"
                    + "is not properly");
        }
        Trip trip = (Trip) object;
        List < TrapeziumMembershipFunction  > distances =
                //Distance objects in the list
                new ArrayList < TrapeziumMembershipFunction  > (
                BeanGetter.lookupDistanceFacade().findAll());
        Distance distanceResults = (Distance) FuzzyDriver.
                getTrapeziumFuzzySetForValue(
                distances, trip.getDistance());
        Logger.getLogger("E").trace("Exiting from: processElement");
        return distanceResults;
    }
}
