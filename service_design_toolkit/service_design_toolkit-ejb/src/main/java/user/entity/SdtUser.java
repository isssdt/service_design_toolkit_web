/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package user.entity;

import java.io.Serializable;
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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author longnguyen
 */
@Entity
@Table(name = "sdt_user")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SdtUser.findAll", query = "SELECT s FROM SdtUser s"),
    @NamedQuery(name = "SdtUser.findById", query = "SELECT s FROM SdtUser s WHERE s.id = :id"),
    @NamedQuery(name = "SdtUser.findByUsername", query = "SELECT s FROM SdtUser s WHERE s.username = :username"),
    @NamedQuery(name = "SdtUser.findByIsActive", query = "SELECT s FROM SdtUser s WHERE s.isActive = :isActive")})
public class SdtUser implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "username")
    private String username;

    private static final long serialVersionUID = 1L;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "is_active")
    private Character isActive;
    @JoinColumn(name = "user_role_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private UserRole userRoleId;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "sdtUser")
    private FieldResearcher fieldResearcher;

    public SdtUser() {
    }

    public SdtUser(Integer id) {
        this.id = id;
    }

    public SdtUser(Integer id, String username, Character isActive) {
        this.id = id;
        this.username = username;
        this.isActive = isActive;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Character getIsActive() {
        return isActive;
    }

    public void setIsActive(Character isActive) {
        this.isActive = isActive;
    }

    public UserRole getUserRoleId() {
        return userRoleId;
    }

    public void setUserRoleId(UserRole userRoleId) {
        this.userRoleId = userRoleId;
    }

    public FieldResearcher getFieldResearcher() {
        return fieldResearcher;
    }

    public void setFieldResearcher(FieldResearcher fieldResearcher) {
        this.fieldResearcher = fieldResearcher;
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
        if (!(object instanceof SdtUser)) {
            return false;
        }
        SdtUser other = (SdtUser) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "user.entity.SdtUser[ id=" + id + " ]";
    }

    public SdtUser(Integer id, String username) {
        this.id = id;
        this.username = username;
    }
    
}
