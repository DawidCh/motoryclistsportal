/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ai.fuzzyficators.usagecomputers;

import org.apache.log4j.Logger;

/**
 *
 * @author kalosh
 */
public class SquareUsageComputer implements UsageComputerInterface {

    /**
     * Method used for computing percentage usage. It uses ax^2 function.
     * @param partUsageInUnits actually usage of part
     * @param partAvailabilityUsage reange of usage where part is available
     * @return value of percentage usage of part
     */
    public double computePercentageValue(final int partUsageInUnits,
            final int partAvailabilityUsage) {
        Logger.getLogger("E").trace("Entering to: computePercentageValue");
        double aFactor = 1.0 / Math.pow(partAvailabilityUsage, 2.0);
        double percetnageUsage = aFactor * Math.pow(partUsageInUnits, 2.0);
        if (percetnageUsage > 1.0) {
            Logger.getLogger("fuzzyLogger").info("Percentage usage upper"
                    + "100% in FuzzyDriver.computeUsage");
        }
        Logger.getLogger("E").trace("Exiting from: computePercentageValue");
        return percetnageUsage;
    }
}
