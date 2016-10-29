/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package user.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 *
 * @author longnguyen
 */

@JsonInclude(Include.NON_NULL)
public class SdtUserDTO {

    private String username;
    private Character isActive;
    private FieldResearcherDTO fieldResearcherDTO;

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

    public FieldResearcherDTO getFieldResearcherDTO() {
        return fieldResearcherDTO;
    }

    public void setFieldResearcherDTO(FieldResearcherDTO fieldResearcherDTO) {
        this.fieldResearcherDTO = fieldResearcherDTO;
    }
}
