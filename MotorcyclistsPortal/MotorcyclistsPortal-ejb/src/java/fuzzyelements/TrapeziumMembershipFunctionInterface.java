/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package fuzzyelements;

/**
 * Interface used when object represents trapesium membership function. Alpha,
 * beta, gamma delta points are characteristic points of the trapesium.
 * @author kalosh
 */
public abstract class TrapeziumMembershipFunctionInterface {

    /**
     * Field which contains value of membership function.
     */
    protected Double membershipFunctionValue;

    /**
     * Method used for getting alpha value.
     * @return Double value
     */
    public abstract Double getAlpha();

    /**
     * Method used for getting beta value.
     * @return Double value
     */
    public abstract Double getBeta();

    /**
     * Method used for getting gamma value.
     * @return Double value
     */
    public abstract Double getGamma();

    /**
     * Method used for getting delta value.
     * @return Double value
     */
    public abstract Double getDelta();

    /**
     * Getter for membershipFunctionValue field.
     * @return membershipFunctionValue field
     */
    public final Double getMembershipFunctionValue() {
        return membershipFunctionValue;
    }

    /**
     * Setter for membershipFunctionValue field.
     * @param  newMembershipFunctionValue new value of field
     */
    public final void setMembershipFunctionValue(
            final Double newMembershipFunctionValue) {
        this.membershipFunctionValue = newMembershipFunctionValue;
    }
}
