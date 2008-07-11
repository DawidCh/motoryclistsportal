/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package security;

import entities.Fishier;
import entities.LoginData;
import entities.Motorcycle;
import entities.Trip;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import org.springframework.security.GrantedAuthority;
import org.springframework.security.GrantedAuthorityImpl;
import org.springframework.security.userdetails.User;
import utils.BeanGetter;

/**
 * Class represents current logged in user's information.
 * @author Dawid Chojnacki
 */
public class DetailedUserInformation extends User {

    /**
     * Field used for keeping user information.
     */
    private entities.User user;

    /**
     * Parametrized constructor.
     * @param entitiesUser entities.User passed from authenticating method
     */
    public DetailedUserInformation(final entities.User entitiesUser) {
        super(entitiesUser.getLoginData().getLogin(), entitiesUser.
                getLoginData().getPassword(),
                entitiesUser.getLoginData().getEnabled(),
                new GrantedAuthority[]{
                    new GrantedAuthorityImpl(entitiesUser.getLoginData().
                    getPrivileges().getDescription())
                });
        this.user = entitiesUser;
    }

    /**
     * Method used for retrieving average trip distance.
     * @return Double value of average trips distance
     */
    public final Double getAverageTripDistance() {
        return this.user.getAverageTripDistance();
    }

    /**
     * Method used for setting average trip distance.
     * @param distance new average trip distance
     */
    public final void setAverageTripDistance(final Double distance) {
        this.user.setAverageTripDistance(distance);
    }

    /**
     * Method used for providing User.
     * @return entities.User object representing current user
     */
    public final entities.User getUser() {
        return user;
    }

    /**
     * Method used for providing List of Motorcycles own by user.
     * @return List of Motorcycle objects if found
     */
    public final List < Motorcycle > getMotorcycleCollection() {
        return BeanGetter.lookupMotorcycleFacade().
                findByLogin(this.getUsername());
    }

    /**
     * Method used for providing List of user's trips.
     * @return List of entities.Trip objects
     */
    public final List < Trip > getTripCollection() {
        return BeanGetter.lookupTripFacade().findByLogin(this.getUsername());
    }

    /**
     * Method used for providing List of user's fishiers.
     * @return List of entities.Fishier objects
     */
    public final List < Fishier > getFishiers() {
        return BeanGetter.lookupFishierFacade().
                findByLogin(this.getUsername());
    }

    /**
     * Method used for setting user's birthdate.
     * @param birthdate Date to set
     */
    public final void setBirthdate(final Date birthdate) {
        this.user.setBirthdate(birthdate);
    }

    /**
     * Method used for setting user's gender.
     * @param gender gender to set
     */
    public final void setGender(final String gender) {
        this.user.setGender(gender);
    }

    /**
     * Method used for setting user's city.
     * @param city city to set
     */
    public final void setCity(final String city) {
        this.user.setCity(city);
    }

    /**
     * Method used for setting user's mileage type.
     * @param mileageType mileage type to set: km or mil
     */
    public final void setMileageType(final String mileageType) {
        this.user.setMileageType(mileageType);
    }

    /**
     * Method used for geting user's mileage type.
     * @return mileage type as a String
     */
    public final String getMileageType() {
        return this.user.getMileageType();
    }

    /**
     * Method used for setting user's surname.
     * @param surname value to set
     */
    public final void setSurname(final String surname) {
        this.user.setSurname(surname);
    }

    /**
     * Method used for setting user's name.
     * @param name value to set
     */
    public final void setName(final String name) {
        this.user.setName(name);
    }

    /**
     * Method used for setting user's locale.
     * @param locale value to set
     */
    public final void setLocale(final Locale locale) {
        this.user.setLocale(locale);
    }

    /**
     * Method used for geting user's name.
     * @return name as a String
     */
    public final String getName() {
        return this.user.getName();
    }

    /**
     * Method used for geting user's LoginData like password, login,
     * priviledges.
     * @return LoginData contains user information about authentication
     */
    public final LoginData getLoginData() {
        return this.user.getLoginData();
    }

    /**
     * Method used for geting user's localization.
     * @return user's Locale
     */
    public final Locale getLocale() {
        return this.user.getLocale();
    }

    /**
     * Method used for geting user's gender.
     * @return user's gender as a String
     */
    public final String getGender() {
        return this.user.getGender();
    }

    /**
     * Method used for geting user's city.
     * @return city as a String
     */
    public final String getCity() {
        return this.user.getCity();
    }

    /**
     * Method used for geting user's birthdate.
     * @return Date object represents user's birthdate
     */
    public final Date getBirthdate() {
        return this.user.getBirthdate();
    }

    /**
     * Method used for geting user's surname.
     * @return surname as a String
     */
    public final String getSurname() {
        return this.user.getSurname();
    }
}
