/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ai.fuzzycomputers;

/**
 *
 * @author kalosh
 */
public interface FuzzyComputerInterface {

    /**
     * Method which computes fuzzy value for given value and given fuzzy scope.
     * @param argument is a list which contains: as first element value to
     * fuzzyfication, rest elements are fuzzy scopes
     * @return fuzzy scope which fits most to given argument
     * @throws Exception
     */
    Object extractFuzzyValue(Object argument) throws Exception;
}
