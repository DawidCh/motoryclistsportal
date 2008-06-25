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
import javax.persistence.Table;

/**
 *
 * @author kalosh
 */
@Entity
@Table(name = "fuzzyusage")
@NamedQueries({})
public class Usage implements Serializable, TrapesiumInterface {

    private static final long serialVersionUID = 1L;
    @Column(name = "description", nullable = false)
    private String description;
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name = "alpha", nullable = false)
    private Double alpha;
    @Column(name = "beta", nullable = false)
    private Double beta;
    @Column(name = "gamma", nullable = false)
    private Double gamma;
    @Column(name = "delta", nullable = false)
    private Double delta;

    public Usage() {
    }

    public Usage(Integer id) {
        this.id = id;
    }

    public Usage(Integer id, String description) {
        this.id = id;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {

        if (!(object instanceof Usage)) {
            return false;
        }
        Usage other = (Usage) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Usage[id=" + id + "]";
    }

    public Double getDelta() {
        return delta;
    }

    public void setDelta(Double delta) {
        this.delta = delta;
    }

    public Double getGamma() {
        return gamma;
    }

    public void setGamma(Double gamma) {
        this.gamma = gamma;
    }

    public Double getBeta() {
        return beta;
    }

    public void setBeta(Double beta) {
        this.beta = beta;
    }

    public Double getAlpha() {
        return alpha;
    }

    public void setAlpha(Double alpha) {
        this.alpha = alpha;
    }
}
