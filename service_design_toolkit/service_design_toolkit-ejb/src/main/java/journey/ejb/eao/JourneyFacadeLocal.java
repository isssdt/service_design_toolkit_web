/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package journey.ejb.eao;

import java.util.List;
import java.util.Map;
import javax.ejb.Local;
import journey.entity.Journey;

/**
 *
 * @author longnguyen
 */
@Local
public interface JourneyFacadeLocal {

    Journey create(Journey journey);

    void edit(Journey journey);

    void remove(Journey journey);

    Journey find(Object id);

    List<Journey> findAll();

    List<Journey> findRange(int[] range);

    int count();
    
    Journey findJourneyByName(Object journeyName);
    
    List<Journey> findListOfJourneyByIsActive(Object isActive);    
    
    List<Journey> findListByNativeQuery(String query, List<Object> params);
    
    Journey findSingleByNativeQuery(String query, List<Object> params);
    
    List<Journey> findListByQueryName(String queryName, Map<String, Object> queryParamValues);
    
    Journey findSingleByQueryName(String queryName, Map<String, Object> params);
    
}
