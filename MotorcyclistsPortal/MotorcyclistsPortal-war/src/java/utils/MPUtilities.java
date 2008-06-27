/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import ai.fuzzycomputers.TrapeziumComputer;
import entities.Distance;
import entities.Fishier;
import entities.FishierElementBridge;
import entities.Motorcycle;
import entities.TrapesiumInterface;
import entities.Trip;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author kalosh
 */
public class MPUtilities {

    /**
     * Private constructor.
     */
    private MPUtilities() {
    }

    /**
     * Method used for finding bike by given id.
     * @param bikeId number id of bike to find
     * @return Motorcycle object if found, null otherwise
     * @throws utils.MPException
     */
    public static Motorcycle findBike(String bikeId) throws MPException {
        for (Motorcycle bike : BeanGetter.getUserInfo().
                getMotorcycleCollection()) {
            if (bike.getId().toString().equals(bikeId)) {
                return bike;
            }
        }
        throw new MPException("Bike not found at Utilities.findBike");
    }

    /**
     * Method used for finding trips for logged in user.
     * @return List of Trip objects or null
     */
    public static List<Trip> findTrips() {
        List<Trip> trips = null;
        try {
            trips = BeanGetter.getUserInfo().getTripCollection();
        } catch (MPException mpException) {
            MPLogger.error("Exception caught in MPUtilities.findTrips: "
                    + mpException.getMessage());
        }
        return trips;
    }

    /**
     * Method used for finding trip with given id.
     * @param tripId number id of trip to find
     * @return found trip
     * @throws utils.MPException
     */
    public static Trip findTrip(String tripId) throws MPException {
        for (Trip trip : BeanGetter.getUserInfo().getTripCollection()) {
            if (trip.getId().toString().equals(tripId)) {
                return trip;
            }
        }
        throw new MPException("Trip not found at MPUtilities.findTrip");
    }

    /**
     * Method used for finding fishiers for logged in user.
     * @return Fishier if found
     */
    public static List<Fishier> findFishiers() {
        List<Fishier> fishiers = null;
        try {
            fishiers = BeanGetter.getUserInfo().getFishiers();
        } catch (MPException mpException) {
            MPLogger.error("Exception caught in MPUtilities.findFishiers: "
                    + mpException.getMessage());
        }
        return fishiers;
    }

    /**
     * Method used for finding fishier by given id.
     * @param fishierId number id of given fishier
     * @return Fishier object if found
     * @throws utils.MPException
     */
    public static Fishier findFishier(String fishierId) throws MPException {
        for (Fishier fishier : BeanGetter.getUserInfo().getFishiers()) {
            if (fishier.getId().toString().equals(fishierId)) {
                return fishier;
            }
        }
        throw new MPException("Fishier not found at MPUtilities.findFishier");
    }

    /**
     * Method which is used for finding motorcycles connected to any fishier for
     * specified user.
     * @return List of Motorcycle objects or null if user is not logged in
     */
    public static List < Motorcycle > findBikesWFishiers() {
        List < Motorcycle > result = new ArrayList < Motorcycle > ();
        List < Motorcycle > bikes;
        try {
            bikes = BeanGetter.lookupMotorcycleFacade().
                    findByLogin(BeanGetter.getUserInfo().getUsername());
        } catch (MPException mpException) {
            MPLogger.error("Exception cautght in Report.findBikesWFishiers: "
                    + mpException.getMessage());
            return null;
        }
        for (Iterator < Motorcycle > it = bikes.iterator(); it.hasNext();) {
            Motorcycle motorcycle = it.next();
            if (motorcycle.getFishier() != null) {
                result.add(motorcycle);
            }
        }
        return result;
    }

    /**
     * Method used for finding FishierElementBridge by given fishier id.
     * @param fishierId fishier id number
     * @return List of found FishierElementBridge objects
     */
    public static List < FishierElementBridge >
            findFishierElementBridgeByFishier(final String fishierId) {
        return BeanGetter.lookupFishierElementBridgeFacade().
                findAllByFishier(fishierId);
    }

    /**
     * Method used for prividing fuzzy average distance value for current user.
     * @return Distance object
     */
    public static TrapesiumInterface getFuzzySetForValue(
        List < TrapesiumInterface > fuzzySets, Double value) {
        List parameters = new ArrayList();
        TrapesiumInterface result = null;
        parameters.add(value);
        parameters.addAll(fuzzySets);
        try {
            result = new TrapeziumComputer().extractFuzzyValue(parameters);
        } catch (MPException exception) {
            MPLogger.error("Exception caught in MPUtilities:"
                    + exception.getMessage());
        }
        return result;
    }

    /**
     * Method used for computing fuzzy average trips distance value
     * @return fuzzyfied average distance
     */
    public static Distance getFuzzyAvgDist() {
        List distances =
                BeanGetter.lookupDistanceFacade().findAll();
        Distance distanceResults = null;
        try {
            Double avgTripDist =
                    BeanGetter.getUserInfo().getAverageTripDistance();
            distanceResults = (Distance) MPUtilities.getFuzzySetForValue(
                distances, avgTripDist);
            MPLogger.fatal("getter: "+distanceResults + " " + avgTripDist);
        } catch (MPException exception) {
            MPLogger.error("Exception caught in MPUtilities:"
                    + exception.getMessage());
        }
        return distanceResults;
    }

    /**
     * Method used for refreshing average distance in db
     * @return new average distance
     */
    public static Double recalculateAverageTripDistance() {
        //todo:najpier odpala się getter a później przeliczanie - dziwne
        Double averageResult = 0.0;
        List < Trip > trips = MPUtilities.findTrips();
        for (Iterator<Trip> it = trips.iterator(); it.hasNext();) {
            Trip trip = it.next();
            averageResult += trip.getDistance();
        }
        averageResult /= trips.size();
        MPLogger.fatal("recalc: "+averageResult);
        try {
            BeanGetter.getUserInfo().setAverageTripDistance(averageResult);
            BeanGetter.lookupUserFacade().
                    edit(BeanGetter.getUserInfo().getUser());
        } catch (MPException ex) {
            MPLogger.error("Exception caught in"
                    + "MPUtilities.recalculateAverageTripDistance: "
                    + ex.getMessage());
        }
        return averageResult;
    }
}
