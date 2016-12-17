/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package journey.ejb.business;

import common.rest.dto.RESTReponse;
import common.exception.AppException;
import common.exception.CustomReasonPhraseException;
import java.util.List;
import javax.ejb.Local;
import journey.dto.ChannelListDTO;
import journey.dto.JourneyDTO;
import journey.dto.JourneyFieldResearcherDTO;
import journey.dto.JourneyListDTO;
import journey.dto.TouchPointFieldResearcherDTO;
import touchpoint.dto.TouchPointFieldResearcherListDTO;
import user.dto.FieldResearcherDTO;
import user.dto.SdtUserDTO;

/**
 *
 * @author longnguyen
 */
@Local
public interface JourneyServiceLocal {

    /**
     *
     * @param content
     * @param queryName
     * @return
     */
    public List<JourneyDTO> getAllJourney() throws AppException, CustomReasonPhraseException;

    /**
     * This method is used to create Journey with all of its Touch Point
     * @param journeyDTO contains information about Journey and all of its Touch Point
     * @return ID of the newly created Journey
     * @throws common.exception.AppException for business exception
     * @throws common.exception.CustomReasonPhraseException for system exception
     */
    public Integer createJourney(JourneyDTO journeyDTO) throws AppException, CustomReasonPhraseException;

    /**
     *
     * @param journeyDTO
     * @return
     */
    public JourneyDTO getJourneyByName(JourneyDTO journeyDTO);

    /**
     *
     * @param journeySdtUserDTO
     * @return
     */
    /**
     *
     * @param content
     * @param queryName
     * @return
     */
    public ChannelListDTO getChannelList();

    public RESTReponse registerFieldResearcherWithJourney(JourneyFieldResearcherDTO journeySdtUserDTO) throws AppException, CustomReasonPhraseException;

    public List<FieldResearcherDTO> getRegisteredFieldResearchersByJourneyName(JourneyDTO journeyDTO);
    
    public TouchPointFieldResearcherDTO getTouchPointDetails(TouchPointFieldResearcherDTO touchPointFieldResearcherDTO);
    
    public JourneyListDTO findJourneyListForRegister() throws AppException, CustomReasonPhraseException;
    
    public RESTReponse updateStatusOfJourneyForFieldResearcher(SdtUserDTO sdtUserDTO) throws AppException, CustomReasonPhraseException;
    
    /**
     * This method is used to get list of all TouchPointFieldResearcher of a Journey which has been rated already
     * @param journeyDTO contains name of Journey to get list of all TouchPointFieldResearcher
     * @return list of all TouchPointFieldResearcher of that Journey
     */
    public TouchPointFieldResearcherListDTO getTouchPointFiedlResearcherListOfJourney(JourneyDTO journeyDTO);
    
    /**
     * This method is used to get list of all TouchPointFieldResearcher of a Journey which is done by a SdtUser, which means it has been
     * rated already
     * @param journeyDTO contains name of Journey to get list of all TouchPointFieldResearcher
     * @param sdtUserDTO contains username of the user who worked on the Journey
     * @return list of all TouchPointFieldResearcher of the Journey that the user has worked on
     */
    public TouchPointFieldResearcherListDTO getTouchPointFiedlResearcherListByJourneyNameAndUsername(JourneyDTO journeyDTO, SdtUserDTO sdtUserDTO);
}
