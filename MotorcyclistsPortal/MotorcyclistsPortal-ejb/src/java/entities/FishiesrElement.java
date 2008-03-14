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
@NamedQueries({@NamedQuery(name = "FishiesrElement.findById", query = "SELECT f FROM FishiesrElement f WHERE f.id = :id"), @NamedQuery(name = "FishiesrElement.findByDescription", query = "SELECT f FROM FishiesrElement f WHERE f.description = :description")})
public class FishiesrElement implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name="generator", sequenceName="fishierselements_id_seq")
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="generator")
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name = "description", nullable = false)
    private String description;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fishierelement")
    private Collection<FishierElementBridge> fishierElementBridgeCollection;

    public FishiesrElement() {
    }

    public FishiesrElement(Integer id) {
        this.id = id;
    }

    public FishiesrElement(Integer id, String description) {
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
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FishiesrElement)) {
            return false;
        }
        FishiesrElement other = (FishiesrElement) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.FishiesrElement[id=" + id + "]";
    }

}
