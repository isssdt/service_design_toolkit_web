/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package user.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author longnguyen
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class JourneyFieldResearcherListDTO implements Serializable {
    private List<JourneyFieldResearcherDTO> journeyFieldResearcherDTOList;

    public List<JourneyFieldResearcherDTO> getJourneyFieldResearcherDTOList() {
        return journeyFieldResearcherDTOList;
    }

    public void setJourneyFieldResearcherDTOList(List<JourneyFieldResearcherDTO> journeyFieldResearcherDTOList) {
        this.journeyFieldResearcherDTOList = journeyFieldResearcherDTOList;
    }
}
