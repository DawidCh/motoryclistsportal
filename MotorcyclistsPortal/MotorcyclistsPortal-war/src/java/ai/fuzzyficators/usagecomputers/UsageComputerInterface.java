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
    /**
     * Method used for computing how hard part is used (in %).
     * @param partUsageInUnits current part usage
     * @param partAvailabilityUsage part availability usage
     * @return value of usage from range 0-1
     */
    double computePercentageValue(int partUsageInUnits,
            int partAvailabilityUsage);
}
