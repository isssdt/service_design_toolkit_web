/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package touchpoint.dto;

import java.util.List;
import user.dto.TouchPointFieldResearcherDTO;

/**
 *
 * @author longnguyen
 */
public class TouchPointFieldResearcherListDTO {
    private List<TouchPointFieldResearcherDTO> touchPointFieldResearcherDTOList;

    public List<TouchPointFieldResearcherDTO> getTouchPointFieldResearcherDTOList() {
        return touchPointFieldResearcherDTOList;
    }

    public void setTouchPointFieldResearcherDTOList(List<TouchPointFieldResearcherDTO> touchPointFieldResearcherDTOList) {
        this.touchPointFieldResearcherDTOList = touchPointFieldResearcherDTOList;
    }
}
