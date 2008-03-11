/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author kalosh
 */
@Entity
@Table(name = "users")
@NamedQueries({@NamedQuery(name = "User.findByName", query = "SELECT u FROM User u WHERE u.name = :name"), @NamedQuery(name = "User.findBySurname", query = "SELECT u FROM User u WHERE u.surname = :surname"), @NamedQuery(name = "User.findByCity", query = "SELECT u FROM User u WHERE u.city = :city"), @NamedQuery(name = "User.findByGender", query = "SELECT u FROM User u WHERE u.gender = :gender"), @NamedQuery(name = "User.findByBirthdate", query = "SELECT u FROM User u WHERE u.birthdate = :birthdate"), @NamedQuery(name = "User.findByLogin", query = "SELECT u FROM User u WHERE u.login = :login")})
public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "surname", nullable = false)
    private String surname;
    @Column(name = "city", nullable = false)
    private String city;
    @Column(name = "gender", nullable = false)
    private boolean gender;
    @Column(name = "birthdate", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date birthdate;
    @Id
    @Column(name = "login", nullable = false)
    private String login;
    @JoinColumn(name = "login", referencedColumnName = "login", insertable = false, updatable = false)
    @OneToOne
    private LoginData loginData;

    public User() {
    }

    public User(String login) {
        this.login = login;
    }

    public User(String login, String name, String surname, String city, String gender, Date birthdate) {
        this.login = login;
        this.name = name;
        this.surname = surname;
        this.city = city;
        if(gender.equals("male"))
            this.gender = true;
        else
            this.gender = false;
        this.birthdate = birthdate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getGender() {
        if(gender)
            return "male";
        else
            return "female";
    }

    public void setGender(String gender) {
        if(gender.equals("male"))
            this.gender = true;
        else
            this.gender = false;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public LoginData getLoginData() {
        return loginData;
    }

    public void setLoginData(LoginData loginData) {
        this.loginData = loginData;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (login != null ? login.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof User)) {
            return false;
        }
        User other = (User) object;
        if ((this.login == null && other.login != null) || (this.login != null && !this.login.equals(other.login))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.User[login=" + login + "]";
    }

}
