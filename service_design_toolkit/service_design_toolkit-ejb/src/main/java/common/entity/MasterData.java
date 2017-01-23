/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package common.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import touchpoint.entity.TouchPoint;

/**
 *
 * @author longnguyen
 */
@Entity
@Table(name = "master_data")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MasterData.findAll", query = "SELECT m FROM MasterData m"),
    @NamedQuery(name = "MasterData.findById", query = "SELECT m FROM MasterData m WHERE m.id = :id"),
    @NamedQuery(name = "MasterData.findByDataValue", query = "SELECT m FROM MasterData m WHERE m.dataValue = :dataValue")})
public class MasterData implements Serializable {

    @OneToMany(mappedBy = "durationUnit")
    private List<TouchPoint> touchPointList;

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "id")
    private String id;
    @Size(max = 50)
    @Column(name = "data_value")
    private String dataValue;

    public MasterData() {
    }

    public MasterData(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDataValue() {
        return dataValue;
    }

    public void setDataValue(String dataValue) {
        this.dataValue = dataValue;
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
        if (!(object instanceof MasterData)) {
            return false;
        }
        MasterData other = (MasterData) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "common.entity.MasterData[ id=" + id + " ]";
    }

    @XmlTransient
    public List<TouchPoint> getTouchPointList() {
        return touchPointList;
    }

    public void setTouchPointList(List<TouchPoint> touchPointList) {
        this.touchPointList = touchPointList;
    }
    
}
