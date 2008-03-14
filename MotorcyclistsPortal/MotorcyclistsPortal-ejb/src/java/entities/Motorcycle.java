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
@Table(name = "motorcycles")
@NamedQueries({@NamedQuery(name = "Motorcycle.findById", query = "SELECT m FROM Motorcycle m WHERE m.id = :id"), @NamedQuery(name = "Motorcycle.findByModel", query = "SELECT m FROM Motorcycle m WHERE m.model = :model"), @NamedQuery(name = "Motorcycle.findByProducer", query = "SELECT m FROM Motorcycle m WHERE m.producer = :producer"), @NamedQuery(name = "Motorcycle.findByYear", query = "SELECT m FROM Motorcycle m WHERE m.year = :year"), @NamedQuery(name = "Motorcycle.findByEnginecapacity", query = "SELECT m FROM Motorcycle m WHERE m.enginecapacity = :enginecapacity"), @NamedQuery(name = "Motorcycle.findByPower", query = "SELECT m FROM Motorcycle m WHERE m.power = :power"), @NamedQuery(name = "Motorcycle.findByTorque", query = "SELECT m FROM Motorcycle m WHERE m.torque = :torque")})
public class Motorcycle implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name="generator", sequenceName="motorcycles_id_seq")
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="generator")
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name = "model", nullable = false)
    private String model;
    @Column(name = "producer", nullable = false)
    private String producer;
    @Column(name = "year", nullable = false)
    private int year;
    @Column(name = "enginecapacity", nullable = false)
    private int enginecapacity;
    @Column(name = "power", nullable = false)
    private int power;
    @Column(name = "torque", nullable = false)
    private int torque;
    @JoinColumn(name = "fishier", referencedColumnName = "id")
    @ManyToOne
    private Fishier fishier;

    public Motorcycle() {
    }

    public Motorcycle(Integer id) {
        this.id = id;
    }

    public Motorcycle(Integer id, String model, String producer, int year, int enginecapacity, int power, int torque) {
        this.id = id;
        this.model = model;
        this.producer = producer;
        this.year = year;
        this.enginecapacity = enginecapacity;
        this.power = power;
        this.torque = torque;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getEnginecapacity() {
        return enginecapacity;
    }

    public void setEnginecapacity(int enginecapacity) {
        this.enginecapacity = enginecapacity;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public int getTorque() {
        return torque;
    }

    public void setTorque(int torque) {
        this.torque = torque;
    }

    public Fishier getFishier() {
        return fishier;
    }

    public void setFishier(Fishier fishier) {
        this.fishier = fishier;
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
        if (!(object instanceof Motorcycle)) {
            return false;
        }
        Motorcycle other = (Motorcycle) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Motorcycle[id=" + id + "]";
    }

}
