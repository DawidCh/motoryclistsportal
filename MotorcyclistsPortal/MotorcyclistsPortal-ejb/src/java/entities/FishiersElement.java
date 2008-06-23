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
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 *
 * @author kalosh
 */
@Entity
@Table(name = "fishierselements")
@NamedQueries({@NamedQuery(name = "FishiersElement.findById", query = "SELECT f FROM FishiersElement f WHERE f.id = :id"), @NamedQuery(name = "FishiersElement.findByDescription", query = "SELECT f FROM FishiersElement f WHERE f.description = :description")})
public class FishiersElement implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name="fishierselementgenerator", sequenceName="fishierselements_id_seq")
    @GeneratedValue(generator="fishierselementgenerator", strategy=GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name = "description", nullable = false)
    private String description;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fishierelement")
    private Collection<FishierElementBridge> fishierElementBridgeCollection;

    public FishiersElement() {
    }

    public FishiersElement(Integer id) {
        this.id = id;
    }

    public FishiersElement(Integer id, String description) {
        this.id = id;
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        
        if (!(object instanceof FishiersElement)) {
            return false;
        }
        FishiersElement other = (FishiersElement) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.FishiersElement[id=" + id + "]";
    }

}
