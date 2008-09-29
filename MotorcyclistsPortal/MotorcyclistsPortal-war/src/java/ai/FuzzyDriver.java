/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ai;

import ai.fuzzycomputers.TrapeziumComputer;
import ai.fuzzyficators.AbstractFuzzyficator;
import ai.fuzzyficators.FishierElementBridgeFuzzyficator;
import ai.fuzzyficators.TripsFuzzyficator;
import ai.userdistance.AverageUserDistanceComputer;
import ai.userdistance.UserDistanceComputerInterface;
import entities.Distance;
import entities.FuzzyAdvise;
import entities.FuzzyDecision;
import entities.Trip;
import entities.User;
import fuzzyelements.FuzzyValue;
import fuzzyelements.Fuzzyficable;
import fuzzyelements.TrapeziumMembershipFunction;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.log4j.Logger;
import utils.BeanGetter;
import utils.MPException;
import utils.MPUtilities;

/**
 * Class used for providing processing and fuzzyficating data.
 * @author Dawid Chojnacki
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
     * Field for holding fuzzy average distance.
     */
    private static Distance fuzzyUserDistance;

    /**
     * Field which holds computer of user distance.
     */
    private static UserDistanceComputerInterface userDistComputer;

    /**
     * Field for holding fishier elements bridges after fuzzyfication.
     */
    private List < FuzzyValue > usageOfFuzzyElementBridges;

    static {
        FuzzyDriver.userDistComputer = new AverageUserDistanceComputer();
    }

    /**
     * Default constructor.
     */
    public FuzzyDriver() {
        Logger.getLogger("errorLogger").trace("Entering to: FuzzyDriver()");
        this.tripsFuzzyficator = new TripsFuzzyficator();
        this.fishierElementBridgeFuzzyficator =
                new FishierElementBridgeFuzzyficator();
        Logger.getLogger("errorLogger").trace("Exiting from: FuzzyDriver()");
    }

    /**
     * Parametrized constructor.
     * @param tripsFuzzyficator Trips fuzzyficator object
     * @param fishierElementBridgeFuzzyficator Fishier element bridge
     * fuzzyficator object
     */
    public FuzzyDriver(final AbstractFuzzyficator tripsFuzzyficator,
            final AbstractFuzzyficator fishierElementBridgeFuzzyficator) {
        Logger.getLogger("errorLogger").trace("Entering to: "
                + "FuzzyDriver(AbstractFuzzyficator, AbstractFuzzyficator)");
        this.tripsFuzzyficator = tripsFuzzyficator;
        this.fishierElementBridgeFuzzyficator =
                fishierElementBridgeFuzzyficator;
        Logger.getLogger("errorLogger").trace("Exiting from: "
                + "FuzzyDriver(AbstractFuzzyficator, AbstractFuzzyficator)");
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
        Logger.getLogger("errorLogger").trace("Entering to: "
                + this.getClass().getCanonicalName() + " processAdvision");
        List < FuzzyValue > result = new ArrayList < FuzzyValue >();
        List < FuzzyDecision > decisions =
                BeanGetter.lookupFuzzyDecisionFacade().findAll();
        FuzzyValue fuzzyUsage = null;
        FuzzyAdvise adviceResult = null;
        Distance fuzzyAverageDistance = FuzzyDriver.getFuzzyUserDistance();
        for (Iterator < FuzzyValue > it =
                this.usageOfFuzzyElementBridges.iterator();
                it.hasNext();) {
            fuzzyUsage = (FuzzyValue) it.next();
            for (FuzzyDecision fuzzyDecision : decisions) {
                if (fuzzyDecision.getUsage().equals(fuzzyUsage)) {
                    if (fuzzyDecision.getDistance().
                            equals(fuzzyAverageDistance)) {
                        adviceResult = fuzzyDecision.getAdvise();
                        Logger.getLogger("fuzzyLogger").debug("Fits perfectly: "
                                + adviceResult.getDescription());
                    }
                }
            }
            if (adviceResult == null) {
                adviceResult = BeanGetter.
                        lookupFuzzyAdviseFacade().findHighest();
                Logger.getLogger("fuzzyLogger").
                        warn("Do not fits to any decision:\n"
                        + "\tFuzzyUsage: " + fuzzyUsage.getDescription()
                        + "\tFuzzyDistance: "
                        + FuzzyDriver.fuzzyUserDistance.getDescription());
            }
            result.add(adviceResult);
        }
        Logger.getLogger("errorLogger").trace("Exiting from: "
                + this.getClass().getCanonicalName() + " processAdvision");
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
    private void setTripsFuzzyficator(final AbstractFuzzyficator
            tripsFuzzyficator) {
        this.tripsFuzzyficator = tripsFuzzyficator;
    }

    /**
     * Method used for processing fishier element bridge collection to proper
     * fuzzyficator.
     * @param fishierElements List with FishierElementBridge objects
     * @return list of FuzzyValue objects
     * @throws java.lang.Exception
     */
    public final List < FuzzyValue > processFishierElementBridgeCollection(
            final List < Fuzzyficable > fishierElements) throws Exception {
        Logger.getLogger("errorLogger").
                trace("Entering to: processFishierElementBridgeCollection");
        if (fishierElements == null || fishierElements.size() == 0) {
            throw new MPException("error.partListEmpty");
        }
        this.fishierElements = fishierElements;
        this.usageOfFuzzyElementBridges = this.fishierElementBridgeFuzzyficator.
                processCollection(fishierElements);
        Logger.getLogger("errorLogger").
                trace("Exiting from: processFishierElementBridgeCollection");
        return this.usageOfFuzzyElementBridges;
    }

    /**
     * Method used for processing trip collection to proper fuzzyficator.
     * @param trips List of Trip objects
     * @return list of FuzzyValue objects
     * @throws java.lang.Exception
     */
    public final List < FuzzyValue > processTripCollection(
            final List < Fuzzyficable > trips) throws Exception {
        Logger.getLogger("errorLogger").trace("Entering to: processTripCollection");
        if (trips == null || trips.size() == 0) {
            throw new MPException("error.tripListEmpty");
        }
        List < FuzzyValue > result =
                this.tripsFuzzyficator.processCollection(trips);
        Logger.getLogger("errorLogger").trace("Exiting from: processTripCollection");
        return result;
    }

    /**
     * Method used for refreshing average distance in db.
     * @return new average distance
     */
    public static Double recalculateUserDistance() {
        Logger.getLogger("errorLogger").
                trace("Entering to: recalculateUserDistance");
        Double averageResult = 0.0;
        List < Trip > trips = MPUtilities.findTrips();
        FuzzyDriver.fuzzyUserDistance = null;
        if (trips != null && trips.size() != 0) {
            averageResult =
                    FuzzyDriver.userDistComputer.computeUserDistance(trips);
        }
        try {
            User user = BeanGetter.getUserInfo().getUser();
            user.setUserDistance(averageResult);
            BeanGetter.lookupUserFacade().
                    edit(BeanGetter.getUserInfo().getUser());
            Logger.getLogger("fuzzyLogger").info("User trip distance: "
                    + averageResult);
        } catch (MPException ex) {
            Logger.getLogger("errorLogger").error("Exception caught in"
                    + "MPUtilities.recalculateUserDistance: "
                    + ex.getMessage());
        }
        Logger.getLogger("errorLogger").
                trace("Exiting from: recalculateUserDistance");
        return averageResult;
    }

    /**
     * Method used for prividing fuzzy average distance value for current user.
     * @param fuzzySets sets of object which represents all fuzzy sets in
     * applicaiton
     * @param value value for fuzzyfication
     * @return Distance object
     */
    public static TrapeziumMembershipFunction
            getTrapeziumFuzzySetForValue(
        final List < TrapeziumMembershipFunction  > fuzzySets,
        final Double value) {
        Logger.getLogger("errorLogger").
                trace("Entering to: getTrapeziumFuzzySetForValue");
        List parameters = new ArrayList();
        TrapeziumMembershipFunction result = null;
        parameters.add(value);
        parameters.addAll(fuzzySets);
        try {
            result = new TrapeziumComputer().extractFuzzyValue(parameters);
        } catch (MPException exception) {
            Logger.getLogger("errorLogger").error("Exception caught in MPUtilities:"
                    + exception.getMessage());
        }
        Logger.getLogger("errorLogger").
                trace("Exiting from: getTrapeziumFuzzySetForValue");
        return result;
    }

    /**
     * Method used for computing fuzzy average trips distance value.
     * @return fuzzyfied average distance
     */
    public static Distance getFuzzyUserDistance() {
        Logger.getLogger("errorLogger").trace("Entering to: getFuzzyUserDistance");
        if (FuzzyDriver.fuzzyUserDistance == null) {
            List distances =
                    BeanGetter.lookupDistanceFacade().findAll();
            try {
                Double avgTripDist =
                        BeanGetter.getUserInfo().getUserDistance();
                FuzzyDriver.fuzzyUserDistance = (Distance) FuzzyDriver.
                        getTrapeziumFuzzySetForValue(distances, avgTripDist);
                Logger.getLogger("fuzzyLogger").info("Avg trip distance: "
                        + avgTripDist + " fuzzy value: "
                        + FuzzyDriver.fuzzyUserDistance);
            } catch (MPException exception) {
                Logger.getLogger("errorLogger").error("Exception caught in MPUtilities:"
                        + exception.getMessage());
            }
        }
        Logger.getLogger("errorLogger").trace("Exiting from: getFuzzyUserDistance");
        return FuzzyDriver.fuzzyUserDistance;
    }
}
