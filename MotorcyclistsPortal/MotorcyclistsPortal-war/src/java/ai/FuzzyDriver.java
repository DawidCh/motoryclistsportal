/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ai;

import ai.fuzzyficators.Fuzzyficator;

/**
 *
 * @author kalosh
 */
public class FuzzyDriver {

    private Fuzzyficator fuzzyficator;

    public FuzzyDriver(Fuzzyficator fuzzyficator) {
        this.fuzzyficator = fuzzyficator;
    }

    public void setFuzzyficator(Fuzzyficator fuzzyficator) {
        this.fuzzyficator = fuzzyficator;
    }
}
