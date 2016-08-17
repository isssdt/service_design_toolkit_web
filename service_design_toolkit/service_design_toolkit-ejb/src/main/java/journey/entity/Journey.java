/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package journey.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author longnguyen
 */
@Entity
@Table(name = "journey")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Journey.findAll", query = "SELECT j FROM Journey j"),
    @NamedQuery(name = "Journey.findById", query = "SELECT j FROM Journey j WHERE j.id = :id"),
    @NamedQuery(name = "Journey.findByJourneyName", query = "SELECT j FROM Journey j WHERE j.journeyName = :journeyName"),
    @NamedQuery(name = "Journey.findByNoOfFieldResearcher", query = "SELECT j FROM Journey j WHERE j.noOfFieldResearcher = :noOfFieldResearcher"),
    @NamedQuery(name = "Journey.findByIsActive", query = "SELECT j FROM Journey j WHERE j.isActive = :isActive")})
public class Journey implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "no_of_field_researcher")
    private Integer noOfFieldResearcher;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "journey_name")
    private String journeyName;
    @Column(name = "is_active")
    private Character isActive;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "journeyId")
    @JsonManagedReference
    private List<TouchPoint> touchPointList;

    public Journey() {
    }

    public Journey(Integer id) {
        this.id = id;
    }

    public Journey(Integer id, String journeyName, Integer noOfFieldResearcher) {
        this.id = id;
        this.journeyName = journeyName;
        this.noOfFieldResearcher = noOfFieldResearcher;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getJourneyName() {
        return journeyName;
    }

    public void setJourneyName(String journeyName) {
        this.journeyName = journeyName;
    }

    public Integer getNoOfFieldResearcher() {
        return noOfFieldResearcher;
    }

    public void setNoOfFieldResearcher(Integer noOfFieldResearcher) {
        this.noOfFieldResearcher = noOfFieldResearcher;
    }

    public Character getIsActive() {
        return isActive;
    }

    public void setIsActive(Character isActive) {
        this.isActive = isActive;
    }

    @XmlTransient
    public List<TouchPoint> getTouchPointList() {
        return touchPointList;
    }

    public void setTouchPointList(List<TouchPoint> touchPointList) {
        this.touchPointList = touchPointList;
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
        if (!(object instanceof Journey)) {
            return false;
        }
        Journey other = (Journey) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "team8ft.journey.entity.Journey[ id=" + id + " ]";
    }    
}
