/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package journey.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author longnguyen
 */

@JsonInclude(Include.NON_NULL)
public class JourneyListDTO {
    private List<JourneyDTO> journeyDTOList = new ArrayList<>();

    public List<JourneyDTO> getJourneyDTOList() {
        return journeyDTOList;
    }

    public void setJourneyDTOList(List<JourneyDTO> journeyDTOList) {
        this.journeyDTOList = journeyDTOList;
    }
}
