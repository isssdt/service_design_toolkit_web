/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package touchpoint.view;

import java.io.Serializable;

/**
 *
 * @author longnguyen
 */
public class NetworkElement implements Serializable {

    private String name;
    private String channelName;
    private String channelDesc;

    public NetworkElement() {
    }

    

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getChannelDesc() {
        return channelDesc;
    }

    public void setChannelDesc(String channelDesc) {
        this.channelDesc = channelDesc;
    }

    public NetworkElement(String name, String channelName, String channelDesc) {
        this.name = name;
        this.channelName = channelName;
        this.channelDesc = channelDesc;
    }

   

    @Override
    public String toString() {
        return name;
    }
}
