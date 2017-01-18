/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package touchpoint.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;

/**
 *
 * @author longnguyen
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class TouchPointListDTO {
    private List<TouchPointDTO> touchPointDTOList = null;

    public List<TouchPointDTO> getTouchPointDTOList() {
        return touchPointDTOList;
    }

    public void setTouchPointDTOList(List<TouchPointDTO> touchPointDTOList) {
        this.touchPointDTOList = touchPointDTOList;
    }
    
    
}
