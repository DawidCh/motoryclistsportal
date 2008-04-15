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
@Table(name = "actions")
@NamedQueries({@NamedQuery(name = "Action.findByDescription", query = "SELECT a FROM Action a WHERE a.description = :description")})
public class Action implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "description", nullable = false)
    private String description;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "action")
    private Collection<FishierElementBridge> fishiersElementBridgeCollection;

    public Action() {
    }

    public Action(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Collection<FishierElementBridge> getFishiersElementBridgeCollection() {
        return fishiersElementBridgeCollection;
    }

    public void setFishiersElementBridgeCollection(Collection<FishierElementBridge> fishiersElementBridgeCollection) {
        this.fishiersElementBridgeCollection = fishiersElementBridgeCollection;
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
        if (!(object instanceof Action)) {
            return false;
        }
        Action other = (Action) object;
        if ((this.description == null && other.description != null) || (this.description != null && !this.description.equals(other.description))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Action[description=" + description + "]";
    }

}
