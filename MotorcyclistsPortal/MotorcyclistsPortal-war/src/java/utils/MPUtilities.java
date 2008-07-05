/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import entities.Fishier;
import entities.FishierElementBridge;
import entities.Motorcycle;
import entities.Trip;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.log4j.Logger;

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
        Logger.getLogger("E").trace("Entering to: findBike");
        Motorcycle result = null;
        for (Motorcycle bike : BeanGetter.getUserInfo().
                getMotorcycleCollection()) {
            if (bike.getId().toString().equals(bikeId)) {
                result = bike;
                break;
            }
        }
        if (result == null) {
            throw new MPException("Bike not found at Utilities.findBike");
        }
        Logger.getLogger("E").trace("Exiting from: findBike");
        return result;
    }

    /**
     * Method used for finding trips for logged in user.
     * @return List of Trip objects or null
     */
    public static List<Trip> findTrips() {
        Logger.getLogger("E").trace("Entering to: findTrips");
        List<Trip> trips = null;
        try {
            trips = BeanGetter.getUserInfo().getTripCollection();
        } catch (MPException mpException) {
            Logger.getLogger("E").
                    error("Exception caught in MPUtilities.findTrips: "
                    + mpException.getMessage());
        }
        Logger.getLogger("E").trace("Exiting from: findTrips");
        return trips;
    }

    /**
     * Method used for finding trip with given id.
     * @param tripId number id of trip to find
     * @return found trip
     * @throws utils.MPException
     */
    public static Trip findTrip(String tripId) throws MPException {
        Logger.getLogger("E").trace("Entering to: findTrip");
        Trip result = null;
        for (Trip trip : BeanGetter.getUserInfo().getTripCollection()) {
            if (trip.getId().toString().equals(tripId)) {
                result =trip;
            }
        }
        if (result == null) {
            throw new MPException("Trip not found at MPUtilities.findTrip");
        }
        Logger.getLogger("E").trace("Exiting from: findTrip");
        return result;
    }

    /**
     * Method used for finding fishiers for logged in user.
     * @return Fishier if found
     */
    public static List<Fishier> findFishiers() {
        Logger.getLogger("E").trace("Entering to: findFishiers");
        List<Fishier> fishiers = null;
        try {
            fishiers = BeanGetter.getUserInfo().getFishiers();
        } catch (MPException mpException) {
            Logger.getLogger("E").
                    error("Exception caught in MPUtilities.findFishiers: "
                    + mpException.getMessage());
        }
        Logger.getLogger("E").trace("Exiting from: findFishiers");
        return fishiers;
    }

    /**
     * Method used for finding fishier by given id.
     * @param fishierId number id of given fishier
     * @return Fishier object if found
     * @throws utils.MPException
     */
    public static Fishier findFishier(String fishierId) throws MPException {
        Logger.getLogger("E").trace("Entering to: findFishier");
        Fishier result = null;
        for (Fishier fishier : BeanGetter.getUserInfo().getFishiers()) {
            if (fishier.getId().toString().equals(fishierId)) {
                result = fishier;
            }
        }
        if (result == null) {
            throw new MPException(
                    "Fishier not found at MPUtilities.findFishier");
        }
        Logger.getLogger("E").trace("Exiting from: findFishier");
        return result;
    }

    /**
     * Method which is used for finding motorcycles connected to any fishier for
     * specified user.
     * @return List of Motorcycle objects or null if user is not logged in
     */
    public static List < Motorcycle > findBikesWFishiers() {
        Logger.getLogger("E").trace("Entering to: findBikesWFishiers");
        List < Motorcycle > result = null;
        List < Motorcycle > bikes = null;
        try {
            bikes = BeanGetter.lookupMotorcycleFacade().
                    findByLogin(BeanGetter.getUserInfo().getUsername());
            for (Iterator < Motorcycle > it = bikes.iterator(); it.hasNext();) {
                Motorcycle motorcycle = it.next();
                if (motorcycle.getFishier() != null) {
                    result.add(motorcycle);
                }
            }
        } catch (MPException mpException) {
            Logger.getLogger("E").
                    error("Exception cautght in Report.findBikesWFishiers: "
                    + mpException.getMessage());
        }
        Logger.getLogger("E").trace("Exiting from: findBikesWFishiers");
        return result;
    }

    /**
     * Method used for finding FishierElementBridge by given fishier id.
     * @param fishierId fishier id number
     * @return List of found FishierElementBridge objects
     */
    public static List < FishierElementBridge >
            findFishierElementBridgeByFishier(final String fishierId) {
        Logger.getLogger("E").
                trace("Entering to: findFishierElementBridgeByFishier");
        List < FishierElementBridge > result =
                BeanGetter.lookupFishierElementBridgeFacade().
                findAllByFishier(fishierId);
        Logger.getLogger("E").
                trace("Exiting from: findFishierElementBridgeByFishier");
        return result;
    }

    /**
     * Method for computing sha1 digest algorithm for given String.
     * @param messageToDigest arguement to hash
     * @return hashed value of the message
     * @throws java.lang.Exception
     */
    public static String computeSha(String messageToDigest) throws Exception {
        Logger.getLogger("E").trace("Entering to: computeSha");
        String result = new String();
        MessageDigest md = MessageDigest.getInstance("sha");
        md.reset();
        md.update(messageToDigest.getBytes());
        for (byte b : md.digest()) {
            result += Integer.toHexString(b & 0xff);
        }
        Logger.getLogger("E").trace("Exiting from: computeSha");
        return result;
    }

    /**
     * Method used for checking if given passwords are correct.
     * @param pass first password
     * @param secPass second password
     * @return 1 if passwords are not equal, 2 if length of password is
     * incorrect, 0 if passwords are given properly
     */
    public static int checkPassword(final String pass, final String secPass) {
        Logger.getLogger("E").trace("Entering to: checkPassword");
        int result = 0;
        if (!pass.equals(secPass)) {
            result = 1;
        } else if (pass.length() > DefaultValues.getPassLength()[1]
                || pass.length() < DefaultValues.getPassLength()[0]) {
            result = 2;
        }
        Logger.getLogger("E").trace("Exiting from: checkPassword");
        return result;
    }
}
