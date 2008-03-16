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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
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

    public Fishier() {
    }

    public Fishier(Integer id) {
        this.id = id;
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