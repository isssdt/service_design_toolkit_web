/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package journey.ejb.eao;

import java.util.List;
import java.util.Map;
import javax.ejb.Local;
import journey.entity.Rating;

/**
 *
 * @author samru
 */
@Local
public interface RatingFacadeLocal {
    
    Rating create(Rating rating);
    void edit(Rating channel);

    void remove(Rating channel);

    Rating find(Object id);

    List<Rating> findAll();
    
    List<Rating> findRange(int[] range);

    int count();
    
    Rating findRatingByValue(Object Rating);   
    
    List<Rating> findListByNativeQuery(String query, List<Object> params);
    
    Rating findSingleByNativeQuery(String query, List<Object> params);
    
    List<Rating> findListByQueryName(String queryName, Map<String, Object> queryParamValues);
    
    List<Rating>  findListOfRating();
             
}
