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
@Table(name = "fuzzydistance")
@NamedQueries({@NamedQuery(name = "Distance.findById", query = "SELECT d FROM Distance d WHERE d.id = :id"), @NamedQuery(name = "Distance.findByDescription", query = "SELECT d FROM Distance d WHERE d.description = :description"), @NamedQuery(name = "Distance.findByAlpha", query = "SELECT d FROM Distance d WHERE d.alpha = :alpha"), @NamedQuery(name = "Distance.findByBeta", query = "SELECT d FROM Distance d WHERE d.beta = :beta"), @NamedQuery(name = "Distance.findByGamma", query = "SELECT d FROM Distance d WHERE d.gamma = :gamma"), @NamedQuery(name = "Distance.findByDelta", query = "SELECT d FROM Distance d WHERE d.delta = :delta")})
public class Distance implements Serializable, TrapesiumInterface {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name = "description", nullable = false)
    private String description;
    @Column(name = "alpha", nullable = false)
    private double alpha;
    @Column(name = "beta", nullable = false)
    private double beta;
    @Column(name = "gamma", nullable = false)
    private double gamma;
    @Column(name = "delta", nullable = false)
    private Double delta;

    public Distance() {
    }

    public Distance(Integer id) {
        this.id = id;
    }

    public Distance(Integer id, String description, double alpha, double beta, double gamma, double delta) {
        this.id = id;
        this.description = description;
        this.alpha = alpha;
        this.beta = beta;
        this.gamma = gamma;
        this.delta = delta;
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

    public Double getAlpha() {
        return alpha;
    }

    public void setAlpha(Double alpha) {
        this.alpha = alpha;
    }

    public Double getBeta() {
        return beta;
    }

    public void setBeta(Double beta) {
        this.beta = beta;
    }

    public Double getGamma() {
        return gamma;
    }

    public void setGamma(Double gamma) {
        this.gamma = gamma;
    }

    public Double getDelta() {
        return delta;
    }

    public void setDelta(double delta) {
        this.delta = delta;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Distance)) {
            return false;
        }
        Distance other = (Distance) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Distance[id=" + id + "]";
    }

}
