/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package user.ejb.eao;

import common.ejb.eao.EAOFacade;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;
import user.dto.SdtUserDTO;
import user.entity.SdtUser;

/**
 *
 * @author longnguyen
 */
@Local
public interface SdtUserFacadeLocal extends EAOFacade {

    SdtUser create(SdtUser sdtUser);

    void edit(SdtUser sdtUser);

    void remove(SdtUser sdtUser);

    SdtUser find(Object id);

    List<SdtUser> findAll();

    List<SdtUser> findRange(int[] range);

    int count();

    SdtUser findSingleByQueryName(String queryName, Map<String, Object> params);
    
    SdtUser findUserByUsername(SdtUserDTO sdtUserDTO);
    
    List<SdtUser> findListByQueryName(String queryName, Map<String, Object> queryParamValues);
}
