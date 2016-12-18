/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package user.ejb.business;

import common.rest.dto.RESTReponse;
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
    
    /**
     * This method is used to authenticate user using username and password
     * @param sdtUserDTO contains username and password
     * @return message indicate whether username and password are correct or not
     * @throws common.exception.AppException for business exception
     * @throws common.exception.CustomReasonPhraseException for system exception
     */
    public RESTReponse authenticate(SdtUserDTO sdtUserDTO) throws AppException, CustomReasonPhraseException;
    
    /**
     * This method is used to reset a password for a username
     * @param sdtUserDTO contains username that need to reset password
     * @return message indicate whether password has been reset or not
     * @throws common.exception.AppException for business exception
     * @throws common.exception.CustomReasonPhraseException for system exception
     */
    public RESTReponse resetPassword(SdtUserDTO sdtUserDTO) throws AppException, CustomReasonPhraseException;
    
    /**
     * This method is used to change a password for a username
     * @param sdtUserDTO contains username that need to change password, 
     * old password and new password
     * @return message indicate whether password has been changed or not
     * @throws common.exception.AppException for business exception
     * @throws common.exception.CustomReasonPhraseException for system exception
     */
    public RESTReponse changePassword(SdtUserDTO sdtUserDTO) throws AppException, CustomReasonPhraseException;
}
