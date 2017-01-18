/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package journey.entity;

import user.entity.JourneyFieldResearcher;
import touchpoint.entity.TouchPoint;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.io.Serializable;
import java.util.Date;
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
@Table(name = "journey")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Journey.findAll", query = "SELECT j FROM Journey j"),
    @NamedQuery(name = "Journey.findById", query = "SELECT j FROM Journey j WHERE j.id = :id"),
    @NamedQuery(name = "Journey.findByJourneyName", query = "SELECT j FROM Journey j WHERE j.journeyName = :journeyName"),
    @NamedQuery(name = "Journey.findByNoOfFieldResearcher", query = "SELECT j FROM Journey j WHERE j.noOfFieldResearcher = :noOfFieldResearcher"),
    @NamedQuery(name = "Journey.findByIsActiveAndCanBeRegistered", query = "SELECT j FROM Journey j WHERE j.isActive = :isActive and j.canBeRegistered = :canBeRegistered"),
    @NamedQuery(name = "Journey.findByIsActive", query = "SELECT j FROM Journey j WHERE j.isActive = :isActive"),
    @NamedQuery(name = "Journey.findJourneyListForRegister", 
            query = "SELECT j FROM Journey j WHERE j.startDate <= :startDate and j.endDate >= :endDate and j.canBeRegistered = 'Y'")})
public class Journey implements Serializable {

    @Column(name = "is_sequence")
    private Character isSequence;

    @Size(max = 500)
    @Column(name = "description")
    private String description;

    @Basic(optional = false)
    @NotNull
    @Column(name = "end_date")
    @Temporal(TemporalType.DATE)
    private Date endDate;

    @Basic(optional = false)
    @NotNull
    @Column(name = "can_be_registered")
    private Character canBeRegistered;

    @Basic(optional = false)
    @NotNull
    @Column(name = "no_of_field_researcher")
    private int noOfFieldResearcher;
    @Basic(optional = false)
    @NotNull
    @Column(name = "start_date")
    @Temporal(TemporalType.DATE)
    private Date startDate;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "journeyId")
    private List<JourneyFieldResearcher> journeyFieldResearcherList;

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

    public Journey(String description, Date endDate, Character canBeRegistered, int noOfFieldResearcher, Date startDate, String journeyName, Character isActive) {
        this.description = description;
        this.endDate = endDate;
        this.canBeRegistered = canBeRegistered;
        this.noOfFieldResearcher = noOfFieldResearcher;
        this.startDate = startDate;        
        this.journeyName = journeyName;
        this.isActive = isActive;
    }

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

    @XmlTransient
    public List<JourneyFieldResearcher> getJourneyFieldResearcherList() {
        return journeyFieldResearcherList;
    }

    public void setJourneyFieldResearcherList(List<JourneyFieldResearcher> journeyFieldResearcherList) {
        this.journeyFieldResearcherList = journeyFieldResearcherList;
    }

    public int getNoOfFieldResearcher() {
        return noOfFieldResearcher;
    }

    public void setNoOfFieldResearcher(int noOfFieldResearcher) {
        this.noOfFieldResearcher = noOfFieldResearcher;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

   

    public Character getCanBeRegistered() {
        return canBeRegistered;
    }

    public void setCanBeRegistered(Character canBeRegistered) {
        this.canBeRegistered = canBeRegistered;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Character getIsSequence() {
        return isSequence;
    }

    public void setIsSequence(Character isSequence) {
        this.isSequence = isSequence;
    }
}
