/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package common.dto;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author samru
 */
public class ChannelListDTO {
      private List<ChannelDTO> channelDTOList = new ArrayList<>();

    public List<ChannelDTO> getChannelDTOList() {
        return channelDTOList;
    }

    public void setChannelDTOList(List<ChannelDTO> channelDTOList) {
        this.channelDTOList = channelDTOList;
    }

   
}
