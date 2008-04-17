/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package entities;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
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
 * @author kalosh
 */
@Entity
@Table(name = "trips")
@NamedQueries({@NamedQuery(name = "Trip.findById", query = "SELECT t FROM Trip t WHERE t.id = :id"), @NamedQuery(name = "Trip.findByDate", query = "SELECT t FROM Trip t WHERE t.date = :date"), @NamedQuery(name = "Trip.findByDescription", query = "SELECT t FROM Trip t WHERE t.description = :description"), @NamedQuery(name = "Trip.findByDistance", query = "SELECT t FROM Trip t WHERE t.distance = :distance")})
public class Trip implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name="triprgenerator", sequenceName="trip_id_seq")
    @GeneratedValue(generator="triprgenerator", strategy=GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name = "date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date date;
    @Column(name = "description", nullable = false)
    private String description;
    @Column(name = "distance", nullable = false)
    private double distance;
    @JoinColumn(name = "bike", referencedColumnName = "id")
    @ManyToOne
    private Motorcycle bike;
    @JoinColumn(name = "type", referencedColumnName = "description")
    @ManyToOne
    private TripType type;
    @JoinColumn(name = "user", referencedColumnName = "login")
    @ManyToOne
    private User user;
    @Column(name = "title", nullable = false)
    private String title;

    public Trip() {
    }

    public Trip(Integer id) {
        this.id = id;
    }

    public Trip(Integer id, Date date, String description, double distance) {
        this.id = id;
        this.date = date;
        this.description = description;
        this.distance = distance;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDate() throws ParseException {
        DateFormat sdf = new SimpleDateFormat("dd MMMM yyyy");
        return sdf.format(this.date);
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public Motorcycle getBike() {
        return bike;
    }

    public void setBike(Motorcycle bike) {
        this.bike = bike;
    }

    public TripType getType() {
        return type;
    }

    public void setType(TripType type) {
        this.type = type;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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
        if (!(object instanceof Trip)) {
            return false;
        }
        Trip other = (Trip) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Trip[id=" + id + "]";
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
