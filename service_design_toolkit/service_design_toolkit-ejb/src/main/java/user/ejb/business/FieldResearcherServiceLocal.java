/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package user.ejb.business;

import javax.ejb.Local;
import user.dto.FieldResearcherDTO;

/**
 *
 * @author longnguyen
 */
@Local
public interface FieldResearcherServiceLocal {
    public void refreshCurrentLocation(FieldResearcherDTO fieldResearcherDTO);
}
