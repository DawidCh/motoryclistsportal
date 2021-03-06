/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import fuzzyelements.Fuzzyficable;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Dawid Chojnacki
 */
@Entity
@Table(name = "fishierselbridge")
@NamedQueries({@NamedQuery(name = "FishierElementBridge.findById", query = "SELECT f FROM FishierElementBridge f WHERE f.id = :id"), @NamedQuery(name = "FishierElementBridge.findByPeriodlength", query = "SELECT f FROM FishierElementBridge f WHERE f.periodlength = :periodlength"), @NamedQuery(name = "FishierElementBridge.findByChangedate", query = "SELECT f FROM FishierElementBridge f WHERE f.changedate = :changedate"), @NamedQuery(name = "FishierElementBridge.findByChangemileage", query = "SELECT f FROM FishierElementBridge f WHERE f.changemileage = :changemileage")})
public class FishierElementBridge implements Serializable, Fuzzyficable {

    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name = "fishierelementbrdggenerator", sequenceName = "fishierselbridge_id_seq")
    @GeneratedValue(generator = "fishierelementbrdggenerator", strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name = "periodlength", nullable = false)
    private int periodlength;
    @Column(name = "changedate", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date changedate;
    @Column(name = "changemileage", nullable = false)
    private int changemileage;
    @JoinColumn(name = "action", referencedColumnName = "description")
    @ManyToOne
    private Action action;
    @JoinColumn(name = "activityperiod", referencedColumnName = "description")
    @ManyToOne
    private ActivityPeriod activityperiod;
    @JoinColumn(name = "fishier", referencedColumnName = "id")
    @ManyToOne
    private Fishier fishier;
    @JoinColumn(name = "fishierelement", referencedColumnName = "id")
    @ManyToOne
    private FishiersElement fishierelement;

    public FishierElementBridge() {
    }

    public FishierElementBridge(FishierElementBridge fishElBr) {
        this.action = fishElBr.action;
        this.activityperiod = fishElBr.activityperiod;
        this.fishierelement = fishElBr.fishierelement;   
        this.periodlength = fishElBr.periodlength;
    }

    public FishierElementBridge(Integer id) {
        this.id = id;
    }

    public FishierElementBridge(Integer id, int periodlength) {
        this.id = id;
        this.periodlength = periodlength;
    }

    public FishierElementBridge(Integer id, int periodlength, Date changedate, int changemileage) {
        this.id = id;
        this.periodlength = periodlength;
        this.changedate = changedate;
        this.changemileage = changemileage;
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

    public String getChangedate() {
        DateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        return sdf.format(this.changedate);
    }

    public void setChangedate(Date changedate) {
        this.changedate = changedate;
    }

    public int getChangemileage() {
        return changemileage;
    }

    public void setChangemileage(int changemileage) {
        this.changemileage = changemileage;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = new Action(action);
    }

    public ActivityPeriod getActivityperiod() {
        return activityperiod;
    }

    public void setActivityperiod(String activityperiod) {
        this.activityperiod = new ActivityPeriod(activityperiod);
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
    
    public void setChangemileage(Double mileage){
        this.changemileage = mileage.intValue();
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        
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
