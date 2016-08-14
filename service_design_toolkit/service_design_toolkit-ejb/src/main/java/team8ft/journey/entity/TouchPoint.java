/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package team8ft.journey.entity;

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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

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
    @NamedQuery(name = "TouchPoint.findByRadius", query = "SELECT t FROM TouchPoint t WHERE t.radius = :radius")})
public class TouchPoint implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "touch_point_desc")
    private String touchPointDesc;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "latitude")
    private String latitude;
    @Basic(optional = false)
    @NotNull
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
    private Journey journeyId;

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
    
}
