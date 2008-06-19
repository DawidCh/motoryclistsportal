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
@Table(name = "usage")
@NamedQueries({})
public class Usage implements Serializable {
    private static final long serialVersionUID = 1L;
    @Column(name = "description", nullable = false)
    private String description;
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

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
        // TODO: Warning - this method won't work in the case the id fields are not set
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

}
