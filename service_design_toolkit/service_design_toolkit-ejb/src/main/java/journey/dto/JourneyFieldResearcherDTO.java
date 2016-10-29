/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package journey.dto;

import user.dto.FieldResearcherDTO;

/**
 *
 * @author longnguyen
 */
public class JourneyFieldResearcherDTO {
    private JourneyDTO journeyDTO;
    private FieldResearcherDTO fieldResearcherDTO;
    private String status;

    public JourneyDTO getJourneyDTO() {
        return journeyDTO;
    }

    public void setJourneyDTO(JourneyDTO journeyDTO) {
        this.journeyDTO = journeyDTO;
    }

    public FieldResearcherDTO getFieldResearcherDTO() {
        return fieldResearcherDTO;
    }

    public void setFieldResearcherDTO(FieldResearcherDTO fieldResearcherDTO) {
        this.fieldResearcherDTO = fieldResearcherDTO;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
