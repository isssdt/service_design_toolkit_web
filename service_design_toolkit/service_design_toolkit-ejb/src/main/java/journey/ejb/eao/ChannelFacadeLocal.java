/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package journey.ejb.eao;

import java.util.List;
import java.util.Map;
import javax.ejb.Local;
import common.entity.Channel;

/**
 *
 * @author samru
 */
@Local
public interface ChannelFacadeLocal {
    Channel create(Channel channel);
    void edit(Channel channel);

    void remove(Channel channel);

    Channel find(Object id);

    List<Channel> findAll();
    
    List<Channel> findRange(int[] range);

    int count();
    
    Channel findChannelByName(Object ChannelName);   
    
    List<Channel> findListByNativeQuery(String query, List<Object> params);
    
    Channel findSingleByNativeQuery(String query, List<Object> params);
    
    List<Channel> findListByQueryName(String queryName, Map<String, Object> queryParamValues);
    
    List<Channel>  findListOfChannel();
             
    
}

