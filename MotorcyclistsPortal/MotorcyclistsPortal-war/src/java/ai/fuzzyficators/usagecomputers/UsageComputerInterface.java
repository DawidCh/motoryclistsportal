/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ai.fuzzyficators.usagecomputers;

/**
 *
 * @author kalosh
 */
public interface UsageComputerInterface {
    double computePercentageValue(int partUsageInUnits, int partAvailabilityUsage);
}