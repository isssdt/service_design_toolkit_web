/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package user.entity;

import common.entity.MasterData;
import common.entity.Rating;
import touchpoint.entity.TouchPoint;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author samru
 */
@Entity
@Table(name = "touchpoint_field_researcher")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TouchpointFieldResearcher.findAll", query = "SELECT t FROM TouchpointFieldResearcher t"),
    @NamedQuery(name = "TouchpointFieldResearcher.findById", query = "SELECT t FROM TouchpointFieldResearcher t WHERE t.id = :id"),
    @NamedQuery(name = "TouchpointFieldResearcher.findByComments", query = "SELECT t FROM TouchpointFieldResearcher t WHERE t.comments = :comments"),
    @NamedQuery(name = "TouchpointFieldResearcher.findByReaction", query = "SELECT t FROM TouchpointFieldResearcher t WHERE t.reaction = :reaction"),
    @NamedQuery(name = "TouchpointFieldResearcher.findByTouchpointIdAndFieldResearcherName", 
            query = "SELECT t FROM TouchpointFieldResearcher t WHERE t.touchpointId = :touchpointId and t.fieldResearcherId.sdtUser.username = :username"),
    @NamedQuery(name = "TouchpointFieldResearcher.findByStatusAndFieldResearcherName", 
            query = "SELECT t FROM TouchpointFieldResearcher t WHERE t.status = :status and t.fieldResearcherId.sdtUser.username = :username"),
    @NamedQuery(name = "TouchpointFieldResearcher.findByJourneyName", 
            query = "SELECT t FROM TouchpointFieldResearcher t WHERE t.touchpointId.journeyId.journeyName = :journeyName and t.ratingId IS NOT NULL ORDER BY t.touchpointId.sequenceNo"),
    @NamedQuery(name = "TouchpointFieldResearcher.findByJourneyNameAndUsername", 
            query = "SELECT t FROM TouchpointFieldResearcher t WHERE t.touchpointId.journeyId.journeyName = :journeyName and t.fieldResearcherId.sdtUser.username = :username and t.ratingId IS NOT NULL ORDER BY t.touchpointId.sequenceNo"),
    @NamedQuery(name = "TouchpointFieldResearcher.00002", 
            query = "SELECT t.touchpointId.id, COUNT(1) FROM TouchpointFieldResearcher t WHERE t.touchpointId.journeyId.journeyName = :journeyName AND t.ratingId.id = 3 GROUP BY t.touchpointId.id"),
    @NamedQuery(name = "TouchpointFieldResearcher.00003", 
            query = "SELECT t.touchpointId.id, COUNT(1) FROM TouchpointFieldResearcher t WHERE t.touchpointId.journeyId.journeyName = :journeyName AND t.ratingId.id in (1,2) GROUP BY t.touchpointId.id"),
    @NamedQuery(name = "TouchpointFieldResearcher.00004", 
            query = "SELECT t.touchpointId.id, COUNT(1) FROM TouchpointFieldResearcher t WHERE t.touchpointId.journeyId.journeyName = :journeyName AND t.ratingId.id in (4,5) GROUP BY t.touchpointId.id"),
    @NamedQuery(name = "TouchpointFieldResearcher.00001", 
            query = "SELECT t FROM TouchpointFieldResearcher t WHERE t.fieldResearcherId.sdtUser.username = :username and t.touchpointId.journeyId IN (SELECT J.journeyId FROM JourneyFieldResearcher J WHERE J.fieldResearcherId.sdtUser.username = :username and J.status = 'IN PROGRESS') ORDER BY t.touchpointId.sequenceNo")})           
public class TouchpointFieldResearcher implements Serializable {

    @JoinColumn(name = "duration_unit", referencedColumnName = "id")
    @ManyToOne
    private MasterData durationUnit;

    @Size(max = 500)
    @Column(name = "photo_location")
    private String photoLocation;
    @Basic(optional = false)
    @NotNull
    @Column(name = "action_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date actionTime;

    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "duration")
    private Integer duration;

    @Size(max = 50)
    @Column(name = "status")
    private String status;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 200)
    @Column(name = "comments")
    private String comments;
    @Size(max = 200)
    @Column(name = "reaction")
    private String reaction;
    @JoinColumn(name = "rating_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Rating ratingId;
    @JoinColumn(name = "touchpoint_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private TouchPoint touchpointId;
    @JoinColumn(name = "field_researcher_id", referencedColumnName = "id")
    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    private FieldResearcher fieldResearcherId;
    

    public TouchpointFieldResearcher() {
    }

    public TouchpointFieldResearcher(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public FieldResearcher getFieldResearcherId() {
        return fieldResearcherId;
    }

    public void setFieldResearcherId(FieldResearcher fieldResearcherId) {
        this.fieldResearcherId = fieldResearcherId;
    }

    public String getReaction() {
        return reaction;
    }

    public void setReaction(String reaction) {
        this.reaction = reaction;
    }

    public Rating getRatingId() {
        return ratingId;
    }

    public void setRatingId(Rating ratingId) {
        this.ratingId = ratingId;
    }

    public TouchPoint getTouchpointId() {
        return touchpointId;
    }

    public void setTouchpointId(TouchPoint touchpointId) {
        this.touchpointId = touchpointId;
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
        if (!(object instanceof TouchpointFieldResearcher)) {
            return false;
        }
        TouchpointFieldResearcher other = (TouchpointFieldResearcher) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "journey.entity.TouchpointFieldResearcher[ id=" + id + " ]";
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getPhotoLocation() {
        return photoLocation;
    }

    public void setPhotoLocation(String photoLocation) {
        this.photoLocation = photoLocation;
    }

    public Date getActionTime() {
        return actionTime;
    }

    public void setActionTime(Date actionTime) {
        this.actionTime = actionTime;
    }

    public MasterData getDurationUnit() {
        return durationUnit;
    }

    public void setDurationUnit(MasterData durationUnit) {
        this.durationUnit = durationUnit;
    }
    
}
