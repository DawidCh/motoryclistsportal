/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ai.fuzzycomputers;

import entities.Usage;
import java.util.ArrayList;
import java.util.List;
import utils.MPException;
import utils.MPLogger;

/**
 *
 * @author kalosh
 */
public class TrapeziumComputer implements FuzzyComputerInterface {

    /**
     * Method which computes fuzzy value for given value and trapezium membership
     * function.
     * @param argument is a list which contains: as first element value to fuzzyfication, 
     * rest elements are Usages
     * @throws Exception
     * @return entities.Usage which fits most to given argument
     */
    public Usage extractFuzzyValue(Object argument) throws Exception {
        if (!(argument instanceof List)) {
            throw new MPException("Object passed to TrapeziumComputer.processFuzzyfication is not properly");
        }
        List parameters = (List) argument;
        Double percentageValue = (Double) parameters.get(0);
        Usage maxValue = null;
        double membershipFunctionValue = this.computeMembershipFunctionValue((Usage) parameters.get(1), percentageValue);
        double tempMemFunVal = 0.0;
        for (int i = 2; i < parameters.size(); i++) {
            tempMemFunVal = this.computeMembershipFunctionValue((Usage) parameters.get(i), percentageValue);
            if (tempMemFunVal > membershipFunctionValue) {
                membershipFunctionValue = tempMemFunVal;
                maxValue = (Usage) parameters.get(i);
            }
        }
        return maxValue;
    }

    private double computeMembershipFunctionValue(Usage usage, Double percentageUsage) {
        double alpha = usage.getAlpha();
        double beta = usage.getBeta();
        double gamma = usage.getGamma();
        double delta = usage.getDelta();
        double result = 0.0;
        
        if (percentageUsage > alpha && percentageUsage <= beta) {
            result = (1.0 / (beta - alpha)) * percentageUsage - (alpha / (beta - alpha));
        } else if (percentageUsage > beta && percentageUsage <= gamma) {
            result = 1.0;
        } else if (percentageUsage > gamma && percentageUsage <= delta) {
            result = (1.0 / (gamma - delta)) * percentageUsage - (delta / (gamma - delta));
        }
        return result;
    }
}
