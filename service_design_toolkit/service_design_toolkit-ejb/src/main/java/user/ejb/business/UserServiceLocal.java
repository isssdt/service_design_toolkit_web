/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package user.ejb.business;

import common.dto.RESTReponse;
import common.exception.AppException;
import common.exception.CustomReasonPhraseException;
import javax.ejb.Local;
import user.dto.FieldResearcherDTO;
import user.dto.SdtUserDTO;

/**
 *
 * @author longnguyen
 */
@Local
public interface UserServiceLocal {
    public void refreshCurrentLocation(FieldResearcherDTO fieldResearcherDTO);
    public FieldResearcherDTO getFieldResearcherByName(user.dto.FieldResearcherDTO fieldResearcherDTO) throws CustomReasonPhraseException;    
    public RESTReponse registerFieldResearcher(SdtUserDTO sdtUserDTO) throws AppException, CustomReasonPhraseException;
}
