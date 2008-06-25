/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package entities;

/**
 * Interface used when object represents trapesium membership function. Alpha,
 * beta, gamma delta points are characteristic points of the trapesium.
 * @author kalosh
 */
public interface TrapesiumInterface {

    /**
     * Method used for getting alpha value.
     * @return Double value
     */
    Double getAlpha();

    /**
     * Method used for getting beta value.
     * @return Double value
     */
    Double getBeta();

    /**
     * Method used for getting gamma value.
     * @return Double value
     */
    Double getGamma();

    /**
     * Method used for getting delta value.
     * @return Double value
     */
    Double getDelta();
}
