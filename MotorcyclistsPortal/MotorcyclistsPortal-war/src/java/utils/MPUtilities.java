/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import entities.Fishier;
import entities.FishierElementBridge;
import entities.FishiersElement;
import entities.Motorcycle;
import entities.Trip;
import java.security.MessageDigest;
import java.util.List;
import org.apache.log4j.Logger;

/**
 * Class wich provides many services (mostly searchers) for other classes.
 * @author Dawid Chojnacki
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
     * @throws MPException
     */
    public static Motorcycle findBike(String bikeId) throws MPException {
        Logger.getLogger("errorLogger").trace("Entering to: findBike");
        Motorcycle result = null;
        for (Motorcycle bike : BeanGetter.getUserInfo().
                getMotorcycleCollection()) {
            if (bike.getId().toString().equals(bikeId)) {
                result = bike;
                break;
            }
        }
        if (result == null) {
            throw new MPException("Bike not found at Utilities.findBike: "
                    + bikeId);
        }
        Logger.getLogger("errorLogger").trace("Exiting from: findBike");
        return result;
    }

    /**
     * Method used for finding trips for logged in user.
     * @return List of Trip objects or null
     */
    public static List<Trip> findTrips() {
        Logger.getLogger("errorLogger").trace("Entering to: findTrips");
        List<Trip> trips = null;
        try {
            trips = BeanGetter.getUserInfo().getTripCollection();
            if (trips == null || trips.size() == 0) {
                Logger.getLogger("errorLogger").warn("No trips found");
            }
        } catch (MPException mpException) {
            Logger.getLogger("errorLogger").
                    error("Exception caught in MPUtilities.findTrips: "
                    + mpException.getMessage());
        }
        Logger.getLogger("errorLogger").trace("Exiting from: findTrips");
        return trips;
    }

    /**
     * Method used for finding trip with given id.
     * @param tripId number id of trip to find
     * @return found trip
     * @throws utils.MPException
     */
    public static Trip findTrip(String tripId) throws MPException {
        Logger.getLogger("errorLogger").trace("Entering to: findTrip");
        Trip result = null;
        for (Trip trip : BeanGetter.getUserInfo().getTripCollection()) {
            if (trip.getId().toString().equals(tripId)) {
                result =trip;
            }
        }
        if (result == null) {
            throw new MPException("Trip not found at MPUtilities.findTrip");
        }
        Logger.getLogger("errorLogger").trace("Exiting from: findTrip");
        return result;
    }

    /**
     * Method used for finding fishiers for logged in user.
     * @return Fishier if found
     */
    public static List<Fishier> findFishiers() {
        Logger.getLogger("errorLogger").trace("Entering to: findFishiers");
        List<Fishier> fishiers = null;
        try {
            fishiers = BeanGetter.getUserInfo().getFishiers();
        } catch (MPException mpException) {
            Logger.getLogger("errorLogger").
                    error("Exception caught in MPUtilities.findFishiers: "
                    + mpException.getMessage());
        }
        Logger.getLogger("errorLogger").trace("Exiting from: findFishiers");
        return fishiers;
    }

    /**
     * Method used for finding fishier by given id.
     * @param fishierId number id of given fishier
     * @return Fishier object if found
     * @throws utils.MPException
     */
    public static Fishier findFishier(String fishierId) throws MPException {
        Logger.getLogger("errorLogger").trace("Entering to: findFishier");
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
        Logger.getLogger("errorLogger").trace("Exiting from: findFishier");
        return result;
    }

    /**
     * Method which is used for finding motorcycles connected to any fishier for
     * specified user.
     * @return List of Motorcycle objects or null if user is not logged in
     */
    public static List < Motorcycle > findBikesWithFishiers() {
        Logger.getLogger("errorLogger").
                trace("Entering to: findBikesWithFishiers");
        List < Motorcycle > bikes = null;
        try {
            bikes = BeanGetter.lookupMotorcycleFacade().
                    findWithFishier(BeanGetter.getUserInfo().getUsername());
            if (bikes == null || bikes.size() == 0) {
                Logger.getLogger("errorLogger").warn("No bikes found");
            }
        } catch (MPException mpException) {
            Logger.getLogger("errorLogger").
                    error("Exception cautght in Report.findBikesWithFishiers: "
                    + mpException.getMessage());
        }
        Logger.getLogger("errorLogger").
                trace("Exiting from: findBikesWithFishiers");
        return bikes;
    }

    /**
     * Method used for finding FishierElementBridge by given fishier id.
     * @param fishierId fishier id number
     * @return List of found FishierElementBridge objects
     */
    public static List < FishierElementBridge >
            findFishierElementBridgeByFishier(final String fishierId) {
        Logger.getLogger("errorLogger").
                trace("Entering to: findFishierElementBridgeByFishier");
        List < FishierElementBridge > result =
                BeanGetter.lookupFishierElementBridgeFacade().
                findAllByFishier(fishierId);
        if (result == null || result.size() == 0) {
            Logger.getLogger("errorLogger").warn("No elements found");
        }
        Logger.getLogger("errorLogger").
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
        Logger.getLogger("errorLogger").trace("Entering to: computeSha");
        String result = new String();
        MessageDigest md = MessageDigest.getInstance("sha");
        md.reset();
        md.update(messageToDigest.getBytes());
        for (byte b : md.digest()) {
            result += Integer.toHexString(b & 0xff);
        }
        Logger.getLogger("errorLogger").trace("Exiting from: computeSha");
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
        Logger.getLogger("errorLogger").trace("Entering to: checkPassword");
        int result = 0;
        if (!pass.equals(secPass)) {
            result = 1;
        } else if (pass.length() > ApplicationSettings.getPassLength()[1]
                || pass.length() < ApplicationSettings.getPassLength()[0]) {
            result = 2;
        }
        Logger.getLogger("errorLogger").trace("Exiting from: checkPassword");
        return result;
    }

    /**
     * Method used for finding all Motorcycles for logged user
     * @return List of Motorcycle objects
     */
    public static List<Motorcycle> findBikesWithoutFishier() {
        List < Motorcycle > bikes = null;
        try {
            bikes = BeanGetter.lookupMotorcycleFacade().
                    findWithoutFishier(BeanGetter.getUserInfo().getUsername());
        } catch (MPException mpException) {
            Logger.getLogger("errorLogger").
                    error("Exception caught in"
                    + " MPUtilities.findBikesWithoutFishier: "
                    + mpException.getMessage());
        }
        return bikes;
    }

    /**
     * Method used for finding all FishierElementBridge.
     * @return List of FishierElementBridge objects
     */
    public static List <FishierElementBridge> findFishierElementBridges() {
        return BeanGetter.lookupFishierElementBridgeFacade().findAll();
    }

    /**
     * Methods used for finding FishierElementBridge by given id
     * @param elementId id number of FishierElementBridge to find
     * @return FishierElementBridge element if found
     */
    public static FishierElementBridge findFishierElementBridge(
            String elementId) {
        return BeanGetter.lookupFishierElementBridgeFacade().
                find(Integer.parseInt(elementId));
    }

    /**
     * Method used for finding FishierElement objects NOT connected
     * with specified fishier
     * @param fishierId fishier's id number
     * @return List of FishierElement objects
     */
    public static List<FishiersElement> findElementsNotConnWithFisher(
            String fishierId) {
        return BeanGetter.lookupFishiersElementFacade().
                findAllNotConnWithFishier(fishierId);
    }
}
