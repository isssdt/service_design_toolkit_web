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
import user.entity.UserRole;

/**
 *
 * @author longnguyen
 */
@Local
public interface UserRoleFacadeLocal extends EAOFacade {

    UserRole create(UserRole userRole);

    void edit(UserRole userRole);

    void remove(UserRole userRole);

    UserRole find(Object id);

    List<UserRole> findAll();

    List<UserRole> findRange(int[] range);

    int count();        
    
    UserRole findSingleByQueryName(String queryName, Map<String, Object> params);
    
    UserRole findByRoleName(Object roleName);
}
