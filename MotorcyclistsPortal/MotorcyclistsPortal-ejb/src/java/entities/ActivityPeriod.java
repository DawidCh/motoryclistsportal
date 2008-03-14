/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package entities;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
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
@Table(name = "activityperiod")
@NamedQueries({@NamedQuery(name = "ActivityPeriod.findByDescription", query = "SELECT a FROM ActivityPeriod a WHERE a.description = :description")})
public class ActivityPeriod implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "description", nullable = false)
    private String description;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "activityperiod")
    private Collection<FishierElementBridge> fishierElementBridgeCollection;

    public ActivityPeriod() {
    }

    public ActivityPeriod(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Collection<FishierElementBridge> getFishierElementBridgeCollection() {
        return fishierElementBridgeCollection;
    }

    public void setFishierElementBridgeCollection(Collection<FishierElementBridge> fishierElementBridgeCollection) {
        this.fishierElementBridgeCollection = fishierElementBridgeCollection;
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
        if (!(object instanceof ActivityPeriod)) {
            return false;
        }
        ActivityPeriod other = (ActivityPeriod) object;
        if ((this.description == null && other.description != null) || (this.description != null && !this.description.equals(other.description))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.ActivityPeriod[description=" + description + "]";
    }

}
