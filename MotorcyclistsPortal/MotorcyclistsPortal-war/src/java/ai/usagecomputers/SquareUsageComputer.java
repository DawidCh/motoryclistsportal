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
public class SquareUsageComputer implements UsageComputerInterface{

     public double computeUsage(int partUsageInUnits, int partAvailabilityUsage) {
        double aFactor = 1.0 / partAvailabilityUsage;
        double percetnageUsage = aFactor * partUsageInUnits;
        if(percetnageUsage > 1.0){
            MPLogger.severe("Percentage usage upper 100% in FuzzyDriver.computeUsage");
        }
        return percetnageUsage;
    }

}
