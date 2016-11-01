/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package touchpoint.ejb.business;

import common.exception.AppException;
import common.exception.CustomReasonPhraseException;
import common.rest.dto.RESTReponse;
import java.util.List;
import javax.ejb.Local;
import journey.dto.TouchPointFieldResearcherDTO;
import user.dto.SdtUserDTO;

/**
 *
 * @author longnguyen
 */
@Local
public interface TouchPointServiceLocal {
    public List<TouchPointFieldResearcherDTO> getTouchPointListOfRegisteredJourney(SdtUserDTO sdtUserDTO) 
            throws AppException, CustomReasonPhraseException;
    
    public RESTReponse saveResponse(TouchPointFieldResearcherDTO touchpointFieldResearcherDTO)
            throws AppException, CustomReasonPhraseException;
}
