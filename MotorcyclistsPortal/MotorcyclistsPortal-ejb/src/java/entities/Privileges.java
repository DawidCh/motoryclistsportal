/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package entities;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author kalosh
 */
@Entity
@Table(name = "privileges")
@NamedQueries({@NamedQuery(name = "Privileges.findByDescription", query = "SELECT p FROM Privileges p WHERE p.description = :description")})
public class Privileges implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "description", nullable = false)
    private String description;
    @OneToMany(mappedBy = "privileges")
    private Collection<LoginData> loginDataCollection;

    public Privileges() {
    }

    public Privileges(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Collection<LoginData> getLoginDataCollection() {
        return loginDataCollection;
    }

    public void setLoginDataCollection(Collection<LoginData> loginDataCollection) {
        this.loginDataCollection = loginDataCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (description != null ? description.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Privileges)) {
            return false;
        }
        Privileges other = (Privileges) object;
        if ((this.description == null && other.description != null) || (this.description != null && !this.description.equals(other.description))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Privileges[description=" + description + "]";
    }

}
