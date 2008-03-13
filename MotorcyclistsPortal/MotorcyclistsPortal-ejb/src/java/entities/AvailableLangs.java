/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author kalosh
 */
@Entity
@Table(name = "availlangs")
@NamedQueries({@NamedQuery(name = "AvailableLangs.findByLocale", query = "SELECT a FROM AvailableLangs a WHERE a.locale = :locale")})
public class AvailableLangs implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "locale", nullable = false)
    private String locale;

    public AvailableLangs() {
    }

    public AvailableLangs(String locale) {
        this.locale = locale;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (locale != null ? locale.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AvailableLangs)) {
            return false;
        }
        AvailableLangs other = (AvailableLangs) object;
        if ((this.locale == null && other.locale != null) || (this.locale != null && !this.locale.equals(other.locale))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.AvailableLangs[locale=" + locale + "]";
    }

}
