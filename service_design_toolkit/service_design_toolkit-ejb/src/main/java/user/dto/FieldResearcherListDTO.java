/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package user.dto;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author longnguyen
 */
public class FieldResearcherListDTO {
    private List<FieldResearcherDTO> fieldResearcherDTOList = new ArrayList<>();

    public List<FieldResearcherDTO> getFieldResearcherDTOList() {
        return fieldResearcherDTOList;
    }

    public void setFieldResearcherDTOList(List<FieldResearcherDTO> fieldResearcherDTOList) {
        this.fieldResearcherDTOList = fieldResearcherDTOList;
    }
    
}
