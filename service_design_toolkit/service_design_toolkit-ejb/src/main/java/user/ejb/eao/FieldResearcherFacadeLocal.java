/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package user.ejb.eao;

import common.dto.QueryParamValue;
import java.util.List;
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
    
    public FieldResearcher findSingleByQueryName(String queryName, QueryParamValue[] queryParamValues);
    
    public List<FieldResearcher> findListByQueryName(String queryName, QueryParamValue[] queryParamValues);    
}
