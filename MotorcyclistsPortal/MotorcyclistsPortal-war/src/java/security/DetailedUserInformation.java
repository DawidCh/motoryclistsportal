/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package security;

import entities.LoginData;
import entities.Motorcycle;
import facades.UserFacadeLocal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import org.acegisecurity.GrantedAuthority;
import org.acegisecurity.GrantedAuthorityImpl;
import org.acegisecurity.userdetails.User;
import utils.BeanGetter;
import utils.MPLogger;

/**
 *
 * @author kalosh
 */
public class DetailedUserInformation extends User{

    private entities.User user;

    public DetailedUserInformation(entities.User user){
        super(user.getLoginData().getLogin(), user.getLoginData().getPassword(),
                user.getLoginData().getEnable(), new GrantedAuthority[]{
                    new GrantedAuthorityImpl(user.getLoginData().getPrivileges().getDescription())});
        this.user = user;
    }
    public entities.User getUser() {
        return user;
    }

    public List<Motorcycle> getMotorcycleCollection(){
        return BeanGetter.lookupMotorcycleFacade().findByLogin(this.getUsername());
    }

    public void setBirthdate(Date birthdate) {
        this.user.setBirthdate(birthdate);
    }

    public void setGender(String gender) {
        this.user.setGender(gender);
    }

    public void setCity(String city) {
        this.user.setCity(city);
    }

    public void setMileageType(String mileageType) {
        this.user.setMileageType(mileageType);
    }
    
    public String getMileageType(){
        return this.user.getMileageType();
    }

    public void setSurname(String surname) {
        this.user.setSurname(surname);
    }

    public void setName(String name) {
        this.user.setName(name);
    }

    public void setLocale(Locale locale) {
        this.user.setLocale(locale);
    }

    public String getName() {
        return this.user.getName();
    }

    public LoginData getLoginData() {
        return this.user.getLoginData();
    }

    public Locale getLocale() {
        return this.user.getLocale();
    }

    public String getGender() {
        return this.user.getGender();
    }

    public String getCity() {
        return this.user.getCity();
    }

    public Date getBirthdate() {
        return this.user.getBirthdate();
    }

    public String getSurname() {
        return this.user.getSurname();
    }
}
