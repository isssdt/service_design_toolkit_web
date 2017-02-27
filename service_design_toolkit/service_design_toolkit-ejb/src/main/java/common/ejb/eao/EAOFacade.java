/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package common.ejb.eao;

import java.util.List;
import java.util.Map;

/**
 *
 * @author longnguyen
 */
public interface EAOFacade {
    List<Object[]> aggregateByQueryName(String queryName, Map<String, Object> queryParamValues);
}
