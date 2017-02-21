/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package touchpoint.ejb.business;

import common.ejb.business.BusinessService;
import touchpoint.dto.TouchPointFieldResearcherListDTO;
import common.exception.AppException;
import common.exception.CustomReasonPhraseException;
import common.rest.dto.RESTReponse;
import java.util.List;
import javax.ejb.Local;
import journey.dto.JourneyDTO;
import touchpoint.dto.TouchPointDTO;
import user.dto.TouchPointFieldResearcherDTO;
import user.dto.SdtUserDTO;

/**
 *
 * @author longnguyen
 */
@Local
public interface TouchPointServiceLocal extends BusinessService {
    public TouchPointFieldResearcherListDTO getTouchPointListOfRegisteredJourney(SdtUserDTO sdtUserDTO) 
            throws AppException, CustomReasonPhraseException;
    
    /**
     * This method is used to create or update rating, comment and reaction for a Touch Point by
     * a Field Researcher
     * @param touchpointFieldResearcherDTO contains rating, comment, reaction, Touch Point ID and Field Researcher ID
     * @return status saying that the creation or updating is successful or error message if fail.
     * @throws AppException for business logic exception
     * @throws CustomReasonPhraseException for system exception
     */
    public RESTReponse saveResponse(TouchPointFieldResearcherDTO touchpointFieldResearcherDTO)
            throws AppException, CustomReasonPhraseException;
    
    public List<TouchPointDTO> getTouchPointListJourney(JourneyDTO journeyDTO);   
    
    public RESTReponse addTouchPointToJourney(TouchPointFieldResearcherDTO touchPointFieldResearcherDTO) throws AppException, CustomReasonPhraseException;
}
