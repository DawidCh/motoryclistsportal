/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package entities;

import fuzzyelements.FuzzyValue;
import fuzzyelements.TrapeziumMembershipFunctionInterface;
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
@Table(name = "fuzzyadvise")
@NamedQueries({@NamedQuery(name = "FuzzyAdvise.findById", query = "SELECT f FROM FuzzyAdvise f WHERE f.id = :id"), @NamedQuery(name = "FuzzyAdvise.findByDescription", query = "SELECT f FROM FuzzyAdvise f WHERE f.description = :description"), @NamedQuery(name = "FuzzyAdvise.findByAlpha", query = "SELECT f FROM FuzzyAdvise f WHERE f.alpha = :alpha"), @NamedQuery(name = "FuzzyAdvise.findByBeta", query = "SELECT f FROM FuzzyAdvise f WHERE f.beta = :beta"), @NamedQuery(name = "FuzzyAdvise.findByGamma", query = "SELECT f FROM FuzzyAdvise f WHERE f.gamma = :gamma"), @NamedQuery(name = "FuzzyAdvise.findByDelta", query = "SELECT f FROM FuzzyAdvise f WHERE f.delta = :delta")})
public class FuzzyAdvise extends TrapeziumMembershipFunctionInterface
        implements Serializable, FuzzyValue {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name = "description", nullable = false)
    private String description;
    @Column(name = "alpha", nullable = false)
    private Double alpha;
    @Column(name = "beta", nullable = false)
    private Double beta;
    @Column(name = "gamma", nullable = false)
    private Double gamma;
    @Column(name = "delta", nullable = false)
    private Double delta;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "advise")
    private Collection<FuzzyDecision> fuzzyDecisionCollection;

    public FuzzyAdvise() {
    }

    public FuzzyAdvise(Integer id) {
        this.id = id;
    }

    public FuzzyAdvise(Integer id, String description, Double alpha, Double beta, Double gamma, Double delta) {
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

    public void setDelta(Double delta) {
        this.delta = delta;
    }

    public Collection<FuzzyDecision> getFuzzyDecisionCollection() {
        return fuzzyDecisionCollection;
    }

    public void setFuzzyDecisionCollection(Collection<FuzzyDecision> fuzzyDecisionCollection) {
        this.fuzzyDecisionCollection = fuzzyDecisionCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof FuzzyAdvise)) {
            return false;
        }
        FuzzyAdvise other = (FuzzyAdvise) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }
}
