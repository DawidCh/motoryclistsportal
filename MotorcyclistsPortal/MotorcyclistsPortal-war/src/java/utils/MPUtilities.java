/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import entities.Fishier;
import entities.Motorcycle;
import entities.Trip;
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
            MPLogger.severe("Exception caught in MPUtilities.findTrips: "
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
            MPLogger.severe("Exception caught in MPUtilities.findFishiers: "
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
}
