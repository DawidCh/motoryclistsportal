/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ai;

import ai.fuzzycomputers.TrapeziumComputer;
import ai.fuzzyficators.AbstractFuzzyficator;
import ai.fuzzyficators.FishierElementBridgeFuzzyficator;
import ai.fuzzyficators.TripsFuzzyficator;
import entities.Distance;
import entities.FuzzyAdvise;
import entities.FuzzyDecision;
import entities.Trip;
import fuzzyelements.FuzzyValue;
import fuzzyelements.Fuzzyficable;
import fuzzyelements.TrapeziumMembershipFunctionInterface;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.log4j.Logger;
import utils.BeanGetter;
import utils.MPException;
import utils.MPUtilities;

/**
 *
 * @author kalosh
 */
public class FuzzyDriver {

    /**
     * Field for holding reference to trip fuzzyficator.
     */
    private AbstractFuzzyficator tripsFuzzyficator;
    /**
     * Field for holding reference to fishier element bridge fuzzyficator.
     */
    private AbstractFuzzyficator fishierElementBridgeFuzzyficator;
    /**
     * Field for holding fishier element bridges.
     */
    private List < Fuzzyficable > fishierElements;

    /**
     * Default constructor.
     */
    public FuzzyDriver() {
        this.tripsFuzzyficator = new TripsFuzzyficator();
        this.fishierElementBridgeFuzzyficator =
                new FishierElementBridgeFuzzyficator();
    }

    /**
     * Parametrized constructor.
     * @param tripsFuzzyficator Trips fuzzyficator object
     * @param fishierElementBridgeFuzzyficator Fishier element bridge
     * fuzzyficator object
     */
    public FuzzyDriver(final AbstractFuzzyficator tripsFuzzyficator,
            final AbstractFuzzyficator fishierElementBridgeFuzzyficator) {
        this.tripsFuzzyficator = tripsFuzzyficator;
        this.fishierElementBridgeFuzzyficator =
                fishierElementBridgeFuzzyficator;
    }

    /**
     * Getter for fishier element bridge fuzzyficator.
     * @return fishier element bridge fuzzyficator
     */
    private AbstractFuzzyficator getfishierElementBridgeFuzzyficator() {
        return fishierElementBridgeFuzzyficator;
    }

    /**
     * Getter for trips fuzzyficator.
     * @return trips fuzzyficator
     */
    private AbstractFuzzyficator getTripsFuzzyficator() {
        return tripsFuzzyficator;
    }

    /**
     * Method used for processing advision.
     * @return List of Advision objects. Each line represents advision for
     * one fishier element bridge in given fishier.
     */
    public final List < FuzzyValue > processAdvision() {
        List < FuzzyValue > result = new ArrayList < FuzzyValue >();
        List < FuzzyAdvise > advises =
                BeanGetter.lookupFuzzyAdviseFacade().findAll();
        List < FuzzyDecision > decisions =
                BeanGetter.lookupFuzzyDecisionFacade().findAll();
        for (Iterator < Fuzzyficable > it = this.fishierElements.iterator();
        it.hasNext(); it.next()) {
            //todo: zrobić podejmowanie decyzji.
            result.add(advises.get(0));
            Logger.getLogger("E").debug("list size: "+this.fishierElements.size());
        }
        return result;
    }

    /**
     * Settter for fishier element bridge fuzzyficator.
     * @param fishierElementBridgeFuzzyficator new value of
     * fishierElementBridgeFuzzyficator
     */
    public final void setfishierElementBridgeFuzzyficator(
            final AbstractFuzzyficator
            fishierElementBridgeFuzzyficator) {
        this.fishierElementBridgeFuzzyficator =
                fishierElementBridgeFuzzyficator;
    }

    /**
     * Setter for trips fuzzyficator.
     * @param tripsFuzzyficator new value of tripsFuzzyficator
     */
    private final void setTripsFuzzyficator(final AbstractFuzzyficator
            tripsFuzzyficator) {
        this.tripsFuzzyficator = tripsFuzzyficator;
    }

    /**
     * Method used for processing fishier element bridge collection to proper
     * fuzzyficator.
     * @param fishierElements
     * @return list of FuzzyValue objects
     * @throws java.lang.Exception
     */
    public final List < FuzzyValue > processFishierElementBridgeCollection(
            final List < Fuzzyficable > fishierElements) throws Exception {
        this.fishierElements = fishierElements;
        return this.fishierElementBridgeFuzzyficator.
                processCollection(fishierElements);
    }

    /**
     * Method used for processing trip collection to proper fuzzyficator.
     * @param fishierElements
     * @return list of FuzzyValue objects
     * @throws java.lang.Exception
     */
    public final List < FuzzyValue > processTripCollection(
            final List < Fuzzyficable > trips) throws Exception {
        return this.tripsFuzzyficator.processCollection(trips);
    }

    /**
     * Method used for refreshing average distance in db.
     * @return new average distance
     */
    public static Double recalculateAverageTripDistance() {
        Double averageResult = 0.0;
        List < Trip > trips = MPUtilities.findTrips();
        for (Iterator < Trip > it = trips.iterator(); it.hasNext();) {
            Trip trip = it.next();
            averageResult += trip.getDistance();
        }
        averageResult /= trips.size();
        try {
            BeanGetter.getUserInfo().setAverageTripDistance(averageResult);
            BeanGetter.lookupUserFacade().
                    edit(BeanGetter.getUserInfo().getUser());
        } catch (MPException ex) {
            Logger.getLogger("E").error("Exception caught in"
                    + "MPUtilities.recalculateAverageTripDistance: "
                    + ex.getMessage());
        }
        return averageResult;
    }

    /**
     * Method used for prividing fuzzy average distance value for current user.
     * @param fuzzySets sets of object which represents all fuzzy sets in
     * applicaiton
     * @param value value for fuzzyfication
     * @return Distance object
     */
    public static TrapeziumMembershipFunctionInterface
            getTrapeziumFuzzySetForValue(
        final List < TrapeziumMembershipFunctionInterface > fuzzySets,
        final Double value) {
        List parameters = new ArrayList();
        TrapeziumMembershipFunctionInterface result = null;
        parameters.add(value);
        parameters.addAll(fuzzySets);
        try {
            result = new TrapeziumComputer().extractFuzzyValue(parameters);
        } catch (MPException exception) {
            Logger.getLogger("E").error("Exception caught in MPUtilities:"
                    + exception.getMessage());
        }
        return result;
    }

    /**
     * Method used for computing fuzzy average trips distance value.
     * @return fuzzyfied average distance
     */
    public static Distance getFuzzyAvgDist() {
        List distances =
                BeanGetter.lookupDistanceFacade().findAll();
        Distance distanceResults = null;
        try {
            Double avgTripDist =
                    BeanGetter.getUserInfo().getAverageTripDistance();
            distanceResults = (Distance) FuzzyDriver.
                    getTrapeziumFuzzySetForValue(distances, avgTripDist);
        } catch (MPException exception) {
            Logger.getLogger("E").error("Exception caught in MPUtilities:"
                    + exception.getMessage());
        }
        return distanceResults;
    }
}
