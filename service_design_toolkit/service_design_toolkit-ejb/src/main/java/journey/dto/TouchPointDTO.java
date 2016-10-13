/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package journey.dto;

/**
 *
 * @author longnguyen
 */
public class TouchPointDTO {
    
    private Integer id;
    private String touchPointDesc;
    private String latitude;
    private String longitude;
    private String radius;
    private String action;
    private String channelDescription;
    private ChannelDTO channelDTO;

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
