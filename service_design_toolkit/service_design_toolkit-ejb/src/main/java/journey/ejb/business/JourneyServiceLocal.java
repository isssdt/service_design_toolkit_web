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
     *
     * @param journeyDTO
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
    
    public Integer updateStatusOfJourneyForFieldResearcher(SdtUserDTO sdtUserDTO) throws AppException, CustomReasonPhraseException;
}
