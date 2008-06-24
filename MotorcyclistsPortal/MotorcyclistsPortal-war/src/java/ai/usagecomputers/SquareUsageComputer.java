/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ai.usagecomputers;

import utils.MPLogger;

/**
 *
 * @author kalosh
 */
public class SquareUsageComputer implements UsageComputerInterface {

    /**
     * Method used for computing percentage usage. It uses ax^2 function.
     * @param partUsageInUnits actually usage of part
     * @param partAvailabilityUsage reange of usage where part is available
     * @return
     */
     public double computeUsage(int partUsageInUnits, int partAvailabilityUsage) {
        double aFactor = 1.0 / Math.pow(partAvailabilityUsage, 2.0);
        double percetnageUsage = aFactor * Math.pow(partUsageInUnits, 2.0);
        if (percetnageUsage > 1.0) {
            MPLogger.severe("Percentage usage upper"
                    + "100% in FuzzyDriver.computeUsage");
        }
        return percetnageUsage;
    }

}