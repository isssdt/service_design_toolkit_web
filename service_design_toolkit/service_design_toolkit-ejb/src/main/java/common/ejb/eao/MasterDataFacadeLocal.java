/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package common.ejb.eao;

import common.entity.MasterData;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

/**
 *
 * @author longnguyen
 */
@Local
public interface MasterDataFacadeLocal extends EAOFacade {

    MasterData create(MasterData masterData);

    void edit(MasterData masterData);

    void remove(MasterData masterData);

    MasterData find(Object id);

    List<MasterData> findAll();

    List<MasterData> findRange(int[] range);

    int count();
    
    MasterData findSingleByQueryName(String queryName, Map<String, Object> params);    
    
    List<MasterData> findListByQueryName(String queryName, Map<String, Object> queryParamValues);
}
