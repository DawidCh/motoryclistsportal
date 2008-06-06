/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utils;

import entities.Motorcycle;

/**
 *
 * @author kalosh
 */
public class MPUtilities {
    
    public static Motorcycle findBike(String bikeId) throws MPException {
        for (Motorcycle bike : BeanGetter.getUserInfo().getMotorcycleCollection()) {
            if (bike.getId().toString().equals(bikeId)) {
                return bike;
            }
        }
        throw new MPException("Bike not found at Utilities.findBike");
    }

}
