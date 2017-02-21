/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package journey.ejb.eao;

import common.ejb.eao.EAOFacade;
import java.util.List;
import java.util.Map;
import touchpoint.entity.TouchPoint;

/**
 *
 * @author samru
 */
public interface TouchPointFacadeLocal extends EAOFacade {
    TouchPoint create(TouchPoint touchpoint);
    void edit(TouchPoint touchpoint);

    void remove(TouchPoint touchpoint);

    TouchPoint find(Object id);

    List<TouchPoint> findAll();
    
    List<TouchPoint> findRange(int[] range);

    int count();     
    
    List<TouchPoint> findListByNativeQuery(String query, List<Object> params);
    
    TouchPoint findSingleByNativeQuery(String query, List<Object> params);
    
    List<TouchPoint> findListByQueryName(String queryName, Map<String, Object> queryParamValues);             
}
