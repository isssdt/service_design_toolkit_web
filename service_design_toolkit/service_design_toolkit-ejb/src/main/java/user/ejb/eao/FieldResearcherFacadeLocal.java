/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package user.ejb.eao;

import java.util.List;
import java.util.Map;
import javax.ejb.Local;
import user.entity.FieldResearcher;

/**
 *
 * @author longnguyen
 */
@Local
public interface FieldResearcherFacadeLocal {

    FieldResearcher create(FieldResearcher fieldResearcher);

    void edit(FieldResearcher fieldResearcher);

    void remove(FieldResearcher fieldResearcher);

    FieldResearcher find(Object id);

    List<FieldResearcher> findAll();

    List<FieldResearcher> findRange(int[] range);

    int count();    
    
    public List<FieldResearcher> findListByQueryName(String queryName, Map<String, Object> queryParamValues);    
}
