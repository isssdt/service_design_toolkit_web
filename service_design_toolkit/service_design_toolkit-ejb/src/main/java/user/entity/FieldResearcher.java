/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package user.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author longnguyen
 */
@Entity
@Table(name = "field_researcher")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FieldResearcher.findAll", query = "SELECT f FROM FieldResearcher f"),
    @NamedQuery(name = "FieldResearcher.findById", query = "SELECT f FROM FieldResearcher f WHERE f.id = :id"),
    @NamedQuery(name = "FieldResearcher.findByCurrentLatitude", query = "SELECT f FROM FieldResearcher f WHERE f.currentLatitude = :currentLatitude"),
    @NamedQuery(name = "FieldResearcher.findByCurrentLongitude", query = "SELECT f FROM FieldResearcher f WHERE f.currentLongitude = :currentLongitude"),
    @NamedQuery(name = "FieldResearcher.findByLastActive", query = "SELECT f FROM FieldResearcher f WHERE f.lastActive = :lastActive")})
public class FieldResearcher implements Serializable {

    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Integer id;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fieldResearcherId")
    private List<JourneyFieldResearcher> journeyFieldResearcherList;

    private static final long serialVersionUID = 1L;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "current_latitude")
    private String currentLatitude;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "current_longitude")
    private String currentLongitude;
    @Basic(optional = false)
    @NotNull
    @Column(name = "last_active")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastActive;
    @JoinColumn(name = "id", referencedColumnName = "id", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private SdtUser sdtUser;

    public FieldResearcher() {
    }

    public FieldResearcher(Integer id) {
        this.id = id;
    }

    public FieldResearcher(Integer id, String currentLatitude, String currentLongitude, Date lastActive) {
        this.id = id;
        this.currentLatitude = currentLatitude;
        this.currentLongitude = currentLongitude;
        this.lastActive = lastActive;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCurrentLatitude() {
        return currentLatitude;
    }

    public void setCurrentLatitude(String currentLatitude) {
        this.currentLatitude = currentLatitude;
    }

    public String getCurrentLongitude() {
        return currentLongitude;
    }

    public void setCurrentLongitude(String currentLongitude) {
        this.currentLongitude = currentLongitude;
    }

    public Date getLastActive() {
        return lastActive;
    }

    public void setLastActive(Date lastActive) {
        this.lastActive = lastActive;
    }

    public SdtUser getSdtUser() {
        return sdtUser;
    }

    public void setSdtUser(SdtUser sdtUser) {
        this.sdtUser = sdtUser;
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
        if (!(object instanceof FieldResearcher)) {
            return false;
        }
        FieldResearcher other = (FieldResearcher) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "user.entity.FieldResearcher[ id=" + id + " ]";
    }

    @XmlTransient
    public List<JourneyFieldResearcher> getJourneyFieldResearcherList() {
        return journeyFieldResearcherList;
    }

    public void setJourneyFieldResearcherList(List<JourneyFieldResearcher> journeyFieldResearcherList) {
        this.journeyFieldResearcherList = journeyFieldResearcherList;
    }    
}
