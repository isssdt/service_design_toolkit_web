/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package user.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author longnguyen
 */

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
public class FieldResearcherDTO implements Serializable {
    private String currentLatitude;
    private String currentLongitude;
    private Date lastActive;
    private SdtUserDTO sdtUserDTO;
    private Integer id;
    private JourneyFieldResearcherListDTO journeyFieldResearcherListDTO;

    public FieldResearcherDTO() {
        
    }

    public JourneyFieldResearcherListDTO getJourneyFieldResearcherListDTO() {
        return journeyFieldResearcherListDTO;
    }

    public void setJourneyFieldResearcherListDTO(JourneyFieldResearcherListDTO journeyFieldResearcherListDTO) {
        this.journeyFieldResearcherListDTO = journeyFieldResearcherListDTO;
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

    public SdtUserDTO getSdtUserDTO() {
        return sdtUserDTO;
    }

    public void setSdtUserDTO(SdtUserDTO sdtUserDTO) {
        this.sdtUserDTO = sdtUserDTO;
    }
}
