/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package touchpoint.dto;

import common.dto.ChannelDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import common.dto.MasterDataDTO;
import java.io.Serializable;
import java.math.BigDecimal;
import journey.dto.JourneyDTO;

/**
 *
 * @author longnguyen
 */

@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class TouchPointDTO implements Serializable {
    
    private Integer id;
    private String touchPointDesc;
    private String latitude;
    private String longitude;
    private String radius;
    private String action;
    private String channelDescription;
    private BigDecimal duration;    
    private MasterDataDTO durationUnit;    

    private ChannelDTO channelDTO;
    private JourneyDTO journeyDTO;

    public TouchPointDTO() {
        channelDTO = new ChannelDTO();
    }

    public TouchPointDTO(Integer id, String touchPointDesc, String latitude, String longitude, String radius, String action, String channelDescription, ChannelDTO channelDTO, JourneyDTO journeyDTO) {
        this.id = id;
        this.touchPointDesc = touchPointDesc;
        this.latitude = latitude;
        this.longitude = longitude;
        this.radius = radius;
        this.action = action;
        this.channelDescription = channelDescription;
        this.channelDTO = channelDTO;
        this.journeyDTO = journeyDTO;
    }    

    public BigDecimal getDuration() {
        return duration;
    }

    public void setDuration(BigDecimal duration) {
        this.duration = duration;
    }

    public MasterDataDTO getDurationUnit() {
        return durationUnit;
    }

    public void setDurationUnit(MasterDataDTO durationUnit) {
        this.durationUnit = durationUnit;
    }
    
    public JourneyDTO getJourneyDTO() {
        return journeyDTO;
    }

    public void setJourneyDTO(JourneyDTO journeyDTO) {
        this.journeyDTO = journeyDTO;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ChannelDTO getChannelDTO() {
        return channelDTO;
    }

    public void setChannelDTO(ChannelDTO channelDTO) {
        this.channelDTO = channelDTO;
    }
    

    public String getTouchPointDesc() {
        return touchPointDesc;
    }

    public void setTouchPointDesc(String touchPointDesc) {
        this.touchPointDesc = touchPointDesc;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getRadius() {
        return radius;
    }

    public void setRadius(String radius) {
        this.radius = radius;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getChannelDescription() {
        return channelDescription;
    }

    public void setChannelDescription(String channelDescription) {
        this.channelDescription = channelDescription;
    }
    
}