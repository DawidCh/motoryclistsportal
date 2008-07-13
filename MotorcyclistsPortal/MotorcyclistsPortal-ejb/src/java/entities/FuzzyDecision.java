/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author Dawid Chojnacki
 */
@Entity
@Table(name = "fuzzydecision")
@NamedQueries({@NamedQuery(name = "FuzzyDecision.findById", query = "SELECT f FROM FuzzyDecision f WHERE f.id = :id")})
public class FuzzyDecision implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;
    @JoinColumn(name = "usage", referencedColumnName = "id")
    @ManyToOne
    private Usage usage;
    @JoinColumn(name = "distance", referencedColumnName = "id")
    @ManyToOne
    private Distance distance;
    @JoinColumn(name = "advise", referencedColumnName = "id")
    @ManyToOne
    private FuzzyAdvise advise;

    public Usage getUsage() {
        return usage;
    }

    public Distance getDistance() {
        return distance;
    }

    public FuzzyDecision() {
    }

    public FuzzyDecision(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public FuzzyAdvise getAdvise() {
        return advise;
    }

    public void setAdvise(FuzzyAdvise advise) {
        this.advise = advise;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof FuzzyDecision)) {
            return false;
        }
        FuzzyDecision other = (FuzzyDecision) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.FuzzyDecision[id=" + id + "]";
    }

}
