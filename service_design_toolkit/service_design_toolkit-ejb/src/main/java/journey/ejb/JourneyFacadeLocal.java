/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package journey.ejb;

import java.util.List;
import javax.ejb.Local;
import journey.entity.Journey;

/**
 *
 * @author longnguyen
 */
@Local
public interface JourneyFacadeLocal {

    void create(Journey journey);

    void edit(Journey journey);

    void remove(Journey journey);

    Journey find(Object id);

    List<Journey> findAll();

    List<Journey> findRange(int[] range);

    int count();
    
}
