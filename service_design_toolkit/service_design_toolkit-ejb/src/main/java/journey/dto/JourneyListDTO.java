/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package journey.dto;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author longnguyen
 */
public class JourneyListDTO {
    private List<JourneyDTO> journeyDTOList = new ArrayList<>();

    public List<JourneyDTO> getJourneyDTOList() {
        return journeyDTOList;
    }

    public void setJourneyDTOList(List<JourneyDTO> journeyDTOList) {
        this.journeyDTOList = journeyDTOList;
    }
}
