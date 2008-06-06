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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 *
 * @author kalosh
 */
@Entity
@Table(name = "fishiers")
@NamedQueries({@NamedQuery(name = "Fishier.findById", query = "SELECT f FROM Fishier f WHERE f.id = :id"), @NamedQuery(name = "Fishier.findByDescription", query = "SELECT f FROM Fishier f WHERE f.description = :description")})
public class Fishier implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name="fishiergenerator", sequenceName="fishiers_id_seq")
    @GeneratedValue(generator="fishiergenerator", strategy=GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name = "description", nullable = false)
    private String description;
    @OneToMany(mappedBy = "fishier")
    private Collection<Motorcycle> motorcycleCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fishier")
    private Collection<FishierElementBridge> fishierElementBridgeCollection;
    @JoinColumn(name = "login", referencedColumnName = "login")
    @ManyToOne
    private User login;
    @JoinColumn(name = "motorcycle", referencedColumnName = "id")
    @OneToOne
    private Motorcycle motorcycle;

    public Fishier() {
    }

    public Fishier(Fishier fish) {
        this.description = fish.description;
        this.login = fish.login;
    }

    public Fishier(Integer id) {
        this.id = id;
    }

    public void setLogin(User login) {
        this.login = login;
    }

    public User getLogin() {
        return login;
    }

    public void setMotorcycle(Motorcycle motorcycle) {
        this.motorcycle = motorcycle;
    }

    public Motorcycle getMotorcycle() {
        return motorcycle;
    }

    public Fishier(Integer id, String description) {
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

    public Collection<Motorcycle> getMotorcycleCollection() {
        return motorcycleCollection;
    }

    public void setMotorcycleCollection(Collection<Motorcycle> motorcycleCollection) {
        this.motorcycleCollection = motorcycleCollection;
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
        if (!(object instanceof Fishier)) {
            return false;
        }
        Fishier other = (Fishier) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Fishier[id=" + id + "]";
    }

}
