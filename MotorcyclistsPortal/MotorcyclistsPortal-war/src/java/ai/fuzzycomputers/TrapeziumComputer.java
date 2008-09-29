/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ai.fuzzycomputers;

import fuzzyelements.TrapeziumMembershipFunction;
import java.util.List;
import org.apache.log4j.Logger;
import utils.MPException;

/**
 * Class which computes fuzzy value in trapezium scope.
 * @author Dawid Chojnacki
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
    public TrapeziumMembershipFunction extractFuzzyValue
            (Object collection) throws MPException {
        Logger.getLogger("errorLogger").trace("Entering to: extractFuzzyValue");
        if (!(collection instanceof List)) {
            throw new MPException("Object passed to" +
                    "TrapeziumComputer.processFuzzyfication is not properly");
        }
        List parameters = (List) collection;
        Double percentageValue = (Double) parameters.get(0);
        TrapeziumMembershipFunction maxValue =
                (TrapeziumMembershipFunction) parameters.get(1);
        double membershipFunctionValue = this.computeMembershipFunctionValue(
                (TrapeziumMembershipFunction)
                parameters.get(1), percentageValue);
        double tempMemFunVal = 0.0;
        for (int i = 2; i < parameters.size(); i++) {
            tempMemFunVal = this.computeMembershipFunctionValue(
                    (TrapeziumMembershipFunction)
                    parameters.get(i), percentageValue);
            if (tempMemFunVal > membershipFunctionValue) {
                membershipFunctionValue = tempMemFunVal;
                maxValue = (TrapeziumMembershipFunction)
                        parameters.get(i);
            }
        }
        maxValue.setMembershipFunctionValue(membershipFunctionValue);
        Logger.getLogger("errorLogger").trace("Exiting from: extractFuzzyValue");
        return maxValue;
    }

    /**
     * Method used for computing membership function value for specified pair
     * of TrapesiumInterface and specified value.
     * @param trapesiumInterface
     * TrapesiumInterface object representing fuzzy set
     * @param doubleValue value to compute
     * @return value from 0 to 1.
     */
    private double computeMembershipFunctionValue(
            TrapeziumMembershipFunction trapesiumInterface,
            Double doubleValue) {
        Logger.getLogger("errorLogger").
                trace("Entering to: computeMembershipFunctionValue");
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
        }
        Logger.getLogger("errorLogger").
                trace("Exiting from: computeMembershipFunctionValue");
        return result;
    }
}
