/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ai.userdistance;

import entities.Trip;
import java.util.Iterator;
import java.util.List;

/**
 * Class which provides average user trips' distances computing.
 * @author Dawid Chojnacki
 */
public class AverageUserDistanceComputer implements
        UserDistanceComputerInterface {

    /**
     * Method which provides average user trips' distances computing.
     * @param trips Trips collection
     * @return double average value of trips' distance
     */
    public Double computeUserDistance(List<Trip> trips) {
        Trip trip;
        Double averageResult = 0.0;
        for (Iterator < Trip > it = trips.iterator(); it.hasNext();) {
            trip = it.next();
            averageResult += trip.getDistance();
        }
        averageResult /= trips.size();
        return averageResult;
    }

}
