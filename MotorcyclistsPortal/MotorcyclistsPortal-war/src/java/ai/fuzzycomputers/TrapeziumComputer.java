/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ai.fuzzycomputers;

import entities.TrapesiumInterface;
import java.util.List;
import utils.MPException;
import utils.MPLogger;

/**
 *
 * @author kalosh
 */
public class TrapeziumComputer implements FuzzyComputerInterface {

    /**
     * Method which computes fuzzy value for given value and trapezium
     * membership function.
     * @param collection is a list which contains: as first element value to
     * fuzzyfication, rest elements are TrapesiumInterface
     * @return entities.TrapesiumInterface which fits most to given argument
     * @throws MPException
     */
    @Override
    public TrapesiumInterface extractFuzzyValue(Object collection) throws MPException {
        if (!(collection instanceof List)) {
            throw new MPException("Object passed to" +
                    "TrapeziumComputer.processFuzzyfication is not properly");
        }
        List parameters = (List) collection;
        Double percentageValue = (Double) parameters.get(0);
        TrapesiumInterface maxValue = (TrapesiumInterface) parameters.get(1);
        double membershipFunctionValue = this.computeMembershipFunctionValue(
                (TrapesiumInterface) parameters.get(1), percentageValue);
        double tempMemFunVal = 0.0;
        for (int i = 2; i < parameters.size(); i++) {
            tempMemFunVal = this.computeMembershipFunctionValue(
                    (TrapesiumInterface) parameters.get(i), percentageValue);
            if (tempMemFunVal > membershipFunctionValue) {
                membershipFunctionValue = tempMemFunVal;
                maxValue = (TrapesiumInterface) parameters.get(i);
            }
        }
        return maxValue;
    }
//todo:cos się walnęło
    /**
     * Method used for computing membership function value for specified pair
     * of TrapesiumInterface and specified value.
     * @param trapesiumInterface
     * TrapesiumInterface object representing fuzzy set
     * @param doubleValue value to compute
     * @return
     */
    private double computeMembershipFunctionValue(
            TrapesiumInterface trapesiumInterface, Double doubleValue) {
        double alpha = trapesiumInterface.getAlpha();
        double beta = trapesiumInterface.getBeta();
        double gamma = trapesiumInterface.getGamma();
        double delta = trapesiumInterface.getDelta();
        double result = 0.0;
        if (doubleValue > alpha && doubleValue <= beta) {
            result = (1.0 / (beta - alpha)) * doubleValue
                    - (alpha / (beta - alpha));
        } else if (doubleValue > beta && doubleValue <= gamma) {
            result = 1.0;
        } else if (doubleValue > gamma && doubleValue <= delta) {
            result = (1.0 / (gamma - delta)) * doubleValue
                    - (delta / (gamma - delta));
        } else if (doubleValue > delta) {
            result = 1.0;
        }
        MPLogger.severe(result+" "+doubleValue);
        return result;
    }
}
