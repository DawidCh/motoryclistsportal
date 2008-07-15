/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ai.userdistance;

import entities.Trip;
import java.util.List;

/**
 * Interface used for computing user trips' distances.
 * @author Dawid Chojnacki
 */
public interface UserDistanceComputerInterface {

    /**
     * Method computes user trips' distances.
     * @param trips Collection of trips
     * @return value of user trips' distance used (indirectly)
     * as input into FuzzyDriver
     */
    Double computeUserDistance(List < Trip > trips);
}
