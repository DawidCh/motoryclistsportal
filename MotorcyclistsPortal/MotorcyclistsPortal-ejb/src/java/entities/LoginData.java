/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "logindata")
@NamedQueries({@NamedQuery(name = "LoginData.findByPassword", query = "SELECT l FROM LoginData l WHERE l.password = :password"), @NamedQuery(name = "LoginData.findByLastlogindate", query = "SELECT l FROM LoginData l WHERE l.lastlogindate = :lastlogindate"), @NamedQuery(name = "LoginData.findByCurrentlogindate", query = "SELECT l FROM LoginData l WHERE l.currentlogindate = :currentlogindate"), @NamedQuery(name = "LoginData.findByLogin", query = "SELECT l FROM LoginData l WHERE l.login = :login")})
public class LoginData implements Serializable {
    private static final long serialVersionUID = 1L;
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "lastlogindate", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastlogindate;
    @Column(name = "currentlogindate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date currentlogindate;
    @Id
    @Column(name = "login", nullable = false)
    private String login;
    @JoinColumn(name = "privileges", referencedColumnName = "description")
    @ManyToOne
    private Privileges privileges;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "loginData")
    private User user;

    public LoginData() {
    }

    public LoginData(String login) {
        this.login = login;
    }

    public LoginData(String login, String password, Date lastlogindate) {
        this.login = login;
        this.password = password;
        this.lastlogindate = lastlogindate;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getLastlogindate() {
        return lastlogindate;
    }

    public void setLastlogindate(Date lastlogindate) {
        this.lastlogindate = lastlogindate;
    }

    public Date getCurrentlogindate() {
        return currentlogindate;
    }

    public void setCurrentlogindate(Date currentlogindate) {
        this.currentlogindate = currentlogindate;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public Privileges getPrivileges() {
        return privileges;
    }

    public void setPrivileges(Privileges privileges) {
        this.privileges = privileges;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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
        if (!(object instanceof LoginData)) {
            return false;
        }
        LoginData other = (LoginData) object;
        if ((this.login == null && other.login != null) || (this.login != null && !this.login.equals(other.login))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.LoginData[login=" + login + "]";
    }

}