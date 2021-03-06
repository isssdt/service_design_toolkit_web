/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package user.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import journey.entity.Journey;

/**
 *
 * @author longnguyen
 */
@Entity
@Table(name = "journey_field_researcher")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "JourneyFieldResearcher.findAll", query = "SELECT j FROM JourneyFieldResearcher j"),
    @NamedQuery(name = "JourneyFieldResearcher.findById", query = "SELECT j FROM JourneyFieldResearcher j WHERE j.id = :id"),
    @NamedQuery(name = "JourneyFieldResearcher.findJourneyOfFieldResearcherByStatus", 
            query = "SELECT j FROM JourneyFieldResearcher j inner join j.fieldResearcherId fr WHERE j.status = :status and fr.sdtUser.username = :username"),
    @NamedQuery(name = "JourneyFieldResearcher.findJourneyByNameAndFieldResearcher", 
            query = "SELECT j FROM JourneyFieldResearcher j inner join j.fieldResearcherId fr INNER JOIN j.journeyId jr WHERE jr.journeyName = :journeyName and fr.sdtUser.username = :username"),
    @NamedQuery(name = "JourneyFieldResearcher.0001", 
            query = "SELECT j FROM JourneyFieldResearcher j WHERE j.fieldResearcherId.sdtUser.username = :username")})

public class JourneyFieldResearcher implements Serializable {

    @Size(max = 50)
    @Column(name = "status")
    private String status;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @JoinColumn(name = "journey_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Journey journeyId;
    @JoinColumn(name = "field_researcher_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private FieldResearcher fieldResearcherId;

    public JourneyFieldResearcher() {
    }

    public JourneyFieldResearcher(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Journey getJourneyId() {
        return journeyId;
    }

    public void setJourneyId(Journey journeyId) {
        this.journeyId = journeyId;
    }

    public FieldResearcher getFieldResearcherId() {
        return fieldResearcherId;
    }

    public void setFieldResearcherId(FieldResearcher fieldResearcherId) {
        this.fieldResearcherId = fieldResearcherId;
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
        if (!(object instanceof JourneyFieldResearcher)) {
            return false;
        }
        JourneyFieldResearcher other = (JourneyFieldResearcher) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "journey.entity.JourneyFieldResearcher[ id=" + id + " ]";
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
}
