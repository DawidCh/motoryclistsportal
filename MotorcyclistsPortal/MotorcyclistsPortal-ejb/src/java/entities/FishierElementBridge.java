/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 *
 * @author kalosh
 */
@Entity
@Table(name = "fishierselbridge")
@NamedQueries({@NamedQuery(name = "FishierElementBridge.findById", query = "SELECT f FROM FishierElementBridge f WHERE f.id = :id"), @NamedQuery(name = "FishierElementBridge.findByPeriodlength", query = "SELECT f FROM FishierElementBridge f WHERE f.periodlength = :periodlength")})
public class FishierElementBridge implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name="fishierelementbrdggenerator", sequenceName="fishierselbridge_id_seq")
    @GeneratedValue(generator="fishierelementbrdggenerator", strategy=GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name = "periodlength", nullable = false)
    private int periodlength;
    @JoinColumn(name = "activityperiod", referencedColumnName = "description")
    @ManyToOne
    private ActivityPeriod activityperiod;
    @JoinColumn(name = "action", referencedColumnName = "description")
    @ManyToOne
    private Action action;
    @JoinColumn(name = "fishier", referencedColumnName = "id")
    @ManyToOne
    private Fishier fishier;
    @JoinColumn(name = "fishierelement", referencedColumnName = "id")
    @ManyToOne
    private FishiersElement fishierelement;

    public FishierElementBridge() {
    }

    public FishierElementBridge(Integer id) {
        this.id = id;
    }

    public FishierElementBridge(Integer id, int periodlength) {
        this.id = id;
        this.periodlength = periodlength;
    }

    public Action getAction() {
        return action;
    }
    
    public void setAction(String action){
        this.action = new Action(action);
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getPeriodlength() {
        return periodlength;
    }

    public void setPeriodlength(int periodlength) {
        this.periodlength = periodlength;
    }

    public ActivityPeriod getActivityperiod() {
        return activityperiod;
    }

    public void setActivityperiod(ActivityPeriod activityperiod) {
        this.activityperiod = activityperiod;
    }
    
    public void setActivityperiod(String activityPeriod){
        this.activityperiod = new ActivityPeriod(activityPeriod);
    }

    public Fishier getFishier() {
        return fishier;
    }

    public void setFishier(Fishier fishier) {
        this.fishier = fishier;
    }

    public FishiersElement getFishierelement() {
        return fishierelement;
    }

    public void setFishierelement(FishiersElement fishierelement) {
        this.fishierelement = fishierelement;
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
        if (!(object instanceof FishierElementBridge)) {
            return false;
        }
        FishierElementBridge other = (FishierElementBridge) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.FishierElementBridge[id=" + id + "]";
    }

}
