/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package touchpoint.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import java.io.Serializable;
import java.util.List;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import common.entity.Channel;
import common.entity.MasterData;
import journey.entity.Journey;
import user.entity.TouchpointFieldResearcher;

/**
 *
 * @author longnguyen
 */
@Entity
@Table(name = "touch_point")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TouchPoint.findAll", query = "SELECT t FROM TouchPoint t"),
    @NamedQuery(name = "TouchPoint.findById", query = "SELECT t FROM TouchPoint t WHERE t.id = :id"),
    @NamedQuery(name = "TouchPoint.findByTouchPointDesc", query = "SELECT t FROM TouchPoint t WHERE t.touchPointDesc = :touchPointDesc"),
    @NamedQuery(name = "TouchPoint.findByLatitude", query = "SELECT t FROM TouchPoint t WHERE t.latitude = :latitude"),
    @NamedQuery(name = "TouchPoint.findByLongitude", query = "SELECT t FROM TouchPoint t WHERE t.longitude = :longitude"),
    @NamedQuery(name = "TouchPoint.00001", query = "SELECT t FROM TouchPoint t WHERE t.journeyId.journeyName = :journeyName AND t.subSeqNo IS NULL ORDER BY t.sequenceNo, t.subSeqNo"),
    @NamedQuery(name = "TouchPoint.00002", query = "SELECT MAX(t.subSeqNo), t.sequenceNo FROM TouchPoint t WHERE t.sequenceNo = :sequenceNo and t.journeyId.journeyName = :journeyName"),
    @NamedQuery(name = "TouchPoint.findByRadius", query = "SELECT t FROM TouchPoint t WHERE t.radius = :radius")})
public class TouchPoint implements Serializable {

    @Column(name = "sub_seq_no")
    private Integer subSeqNo;

    @Column(name = "sequence_no")
    private Integer sequenceNo;

    @Column(name = "duration")
    private Integer duration;

    @JoinColumn(name = "duration_unit", referencedColumnName = "id")
    @ManyToOne
    private MasterData durationUnit;


    @Size(max = 200)
    @Column(name = "action")
    private String action;
    @Size(max = 200)
    @Column(name = "channel_description")
    private String channelDescription;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "touchpointId")
    private List<TouchpointFieldResearcher> touchpointFieldResearcherList;
    @JoinColumn(name = "channel_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Channel channelId;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 500)
    @Column(name = "touch_point_desc")
    private String touchPointDesc;
    @Basic(optional = false)
    @Size(min = 1, max = 20)
    @Column(name = "latitude")
    private String latitude;
    @Basic(optional = false)
    @Size(min = 1, max = 20)
    @Column(name = "longitude")
    private String longitude;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "radius")
    private String radius;
    @JoinColumn(name = "journey_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    @JsonBackReference
    private Journey journeyId;

    public TouchPoint(String action, String channelDescription, Channel channelId, String touchPointDesc, String latitude, String longitude, String radius, Journey journeyId) {
        this.action = action;
        this.channelDescription = channelDescription;
        this.channelId = channelId;        
        this.touchPointDesc = touchPointDesc;
        this.latitude = latitude;
        this.longitude = longitude;
        this.radius = radius;
        this.journeyId = journeyId;
    }

    public TouchPoint() {
    }

    public TouchPoint(Integer id) {
        this.id = id;
    }

    public TouchPoint(Integer id, String touchPointDesc, String latitude, String longitude, String radius) {
        this.id = id;
        this.touchPointDesc = touchPointDesc;
        this.latitude = latitude;
        this.longitude = longitude;
        this.radius = radius;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTouchPointDesc() {
        return touchPointDesc;
    }

    public void setTouchPointDesc(String touchPointDesc) {
        this.touchPointDesc = touchPointDesc;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getRadius() {
        return radius;
    }

    public void setRadius(String radius) {
        this.radius = radius;
    }

    public Journey getJourneyId() {
        return journeyId;
    }

    public void setJourneyId(Journey journeyId) {
        this.journeyId = journeyId;
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
        if (!(object instanceof TouchPoint)) {
            return false;
        }
        TouchPoint other = (TouchPoint) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "team8ft.journey.entity.TouchPoint[ id=" + id + " ]";
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getChannelDescription() {
        return channelDescription;
    }

    public void setChannelDescription(String channelDescription) {
        this.channelDescription = channelDescription;
    }

    @XmlTransient
    public List<TouchpointFieldResearcher> getTouchpointFieldResearcherList() {
        return touchpointFieldResearcherList;
    }

    public void setTouchpointFieldResearcherList(List<TouchpointFieldResearcher> touchpointFieldResearcherList) {
        this.touchpointFieldResearcherList = touchpointFieldResearcherList;
    }

    public Channel getChannelId() {
        return channelId;
    }

    public void setChannelId(Channel channelId) {
        this.channelId = channelId;
    }    


    public MasterData getDurationUnit() {
        return durationUnit;
    }

    public void setDurationUnit(MasterData durationUnit) {
        this.durationUnit = durationUnit;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Integer getSequenceNo() {
        return sequenceNo;
    }

    public void setSequenceNo(Integer sequenceNo) {
        this.sequenceNo = sequenceNo;
    }

    public Integer getSubSeqNo() {
        return subSeqNo;
    }

    public void setSubSeqNo(Integer subSeqNo) {
        this.subSeqNo = subSeqNo;
    }
    
}
