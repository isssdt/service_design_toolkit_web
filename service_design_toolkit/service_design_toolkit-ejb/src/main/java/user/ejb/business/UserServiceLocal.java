/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package user.ejb.business;

import javax.ejb.Local;
import user.dto.FieldResearcherDTO;
import user.entity.FieldResearcher;

/**
 *
 * @author longnguyen
 */
@Local
public interface UserServiceLocal {
    public void refreshCurrentLocation(FieldResearcherDTO fieldResearcherDTO);
    public FieldResearcher getFieldResearcherByName(user.dto.FieldResearcherDTO fieldResearcherDTO);    
}
