/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package user.ejb.business;

import common.constant.ConstantValues;
import common.rest.dto.RESTReponse;
import common.ejb.eao.EAOFactory;
import common.exception.AppException;
import common.exception.CustomReasonPhraseException;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import user.dto.JourneyFieldResearcherDTO;
import org.apache.commons.beanutils.BeanUtils;
import user.dto.FieldResearcherDTO;
import user.dto.SdtUserDTO;
import user.ejb.eao.FieldResearcherFacadeLocal;
import user.ejb.eao.SdtUserFacadeLocal;
import user.ejb.eao.UserRoleFacadeLocal;
import user.entity.FieldResearcher;
import user.entity.SdtUser;

/**
 *
 * @author longnguyen
 */
@Stateless
public class UserService implements UserServiceLocal {

    @EJB
    private UserRoleFacadeLocal userRoleFacade;

    @EJB
    private SdtUserFacadeLocal sdtUserFacade;

    @EJB
    private FieldResearcherFacadeLocal fieldResearcherFacade;
    
    @EJB
    private EAOFactory factory;

    @Override
    public void refreshCurrentLocation(FieldResearcherDTO fieldResearcherDTO) {
        FieldResearcher fieldResearcher;
        SdtUser sdtUser;
        try {
            //check whether User of this Field Researcher already exist 
            Map<String, Object> params = new HashMap<>();
            params.put("username", fieldResearcherDTO.getSdtUserDTO().getUsername());
            sdtUser = sdtUserFacade.findSingleByQueryName("SdtUser.findByUsername", params);            

            //if exists then check whether this Field Researcher already exists
            if (null != sdtUser) {
                fieldResearcher = sdtUser.getFieldResearcher();

                //if this Field Researcher exist then just update
                if (null != fieldResearcher) {
                    //TODO dirty way to copy properties
                    fieldResearcher.setCurrentLatitude(fieldResearcherDTO.getCurrentLatitude());
                    fieldResearcher.setCurrentLongitude(fieldResearcherDTO.getCurrentLongitude());
                    fieldResearcher.setLastActive(new Date());
                    fieldResearcherFacade.edit(fieldResearcher);
                } //if not then create one
                else {
                    fieldResearcher = initFieldResearcher(fieldResearcherDTO, sdtUser);
                    fieldResearcherFacade.create(fieldResearcher);
                }
            } //User does not exist so create new one along with Field Researcher
            else {
                sdtUser = new SdtUser();
                BeanUtils.copyProperties(sdtUser, fieldResearcherDTO.getSdtUserDTO());
                sdtUser.setIsActive('Y');
                
                params.clear();
                params.put("roleName", ConstantValues.FIELD_RESEARCHER_ROLE_NAME);
                sdtUser.setUserRoleId(userRoleFacade.findSingleByQueryName("UserRole.findByRoleName", params));
                SdtUser newSdtUser = sdtUserFacade.create(sdtUser);

                fieldResearcher = initFieldResearcher(fieldResearcherDTO, newSdtUser);
                newSdtUser.setFieldResearcher(fieldResearcher);

                fieldResearcherFacade.create(fieldResearcher);

            }
        } catch (IllegalAccessException | InvocationTargetException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private FieldResearcher initFieldResearcher(FieldResearcherDTO fieldResearcherDTO, SdtUser sdtUser) {
        try {
            FieldResearcher fieldResearcher = new FieldResearcher();
            BeanUtils.copyProperties(fieldResearcher, fieldResearcherDTO);
            fieldResearcher.setLastActive(new Date());
            fieldResearcher.setId(sdtUser.getId());
            fieldResearcher.setSdtUser(sdtUser);

            return fieldResearcher;
        } catch (IllegalAccessException | InvocationTargetException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public FieldResearcherDTO getFieldResearcherByName(user.dto.FieldResearcherDTO fieldResearcherDTO) throws CustomReasonPhraseException {
        Map<String, Object> params = new HashMap<>();
        params.put("username", fieldResearcherDTO.getSdtUserDTO().getUsername());
        SdtUser sdtUser = sdtUserFacade.findSingleByQueryName("SdtUser.findByUsername", params);                
        
        try {
            BeanUtils.copyProperties(fieldResearcherDTO, sdtUser.getFieldResearcher());
        } catch (IllegalAccessException | InvocationTargetException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
            throw new CustomReasonPhraseException(ConstantValues.GENERIC_APP_ERROR_CODE, ex.getMessage());
        }
        return fieldResearcherDTO;
    }

    @Override
    public RESTReponse registerFieldResearcher(SdtUserDTO sdtUserDTO) throws AppException, CustomReasonPhraseException {
        SdtUser sdtUser = factory.getSdtUserFacade().findUserByUsername(sdtUserDTO);
        if (null == sdtUser) {
            sdtUser = new SdtUser();
            try {
                BeanUtils.copyProperties(sdtUser, sdtUserDTO);
            } catch (IllegalAccessException | InvocationTargetException ex) {
                Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
                throw new CustomReasonPhraseException(ConstantValues.GENERIC_APP_ERROR_CODE, ex.getMessage());
            }
            sdtUser.setUserRoleId(factory.getUserRoleFacade().findByRoleName(ConstantValues.FIELD_RESEARCHER_ROLE_NAME));
            sdtUser.setIsActive('Y');            
                        
            factory.getSdtUserFacade().create(sdtUser);
            
            FieldResearcher fieldResearcher = new FieldResearcher(sdtUser.getId());
            fieldResearcher.setCurrentLatitude("-1");
            fieldResearcher.setCurrentLongitude("-1");
            fieldResearcher.setLastActive(new Date());
            fieldResearcher.setSdtUser(sdtUser);
            
            factory.getFieldResearcherFacade().create(fieldResearcher);
            
        }
        
        JourneyFieldResearcherDTO journeyFieldResearcherDTO = new JourneyFieldResearcherDTO();
        FieldResearcherDTO fieldResearcherDTO = new FieldResearcherDTO();
        fieldResearcherDTO.setSdtUserDTO(sdtUserDTO);
        journeyFieldResearcherDTO.setFieldResearcherDTO(fieldResearcherDTO);
        journeyFieldResearcherDTO.setStatus(ConstantValues.JOURNEY_FIELD_RESEARCHER_STATUS_IN_PROGRESS);
        if (null != factory.getJourneyFieldResearcherFacade().findJourneyOfFieldResearcherByStatus(journeyFieldResearcherDTO)) {
            return new RESTReponse("This Field Researcher already registered with a Journey");
        }
        
        return new RESTReponse("This Field Researcher has not registered with any Journey");
    }

    @Override
    public RESTReponse authenticate(SdtUserDTO sdtUserDTO) throws AppException, CustomReasonPhraseException {
        //if there is no username or password, return error
        if (null == sdtUserDTO.getUsername() || sdtUserDTO.getUsername().isEmpty() || null == sdtUserDTO.getPassword() 
                || sdtUserDTO.getPassword().isEmpty()) {
            return new RESTReponse(ConstantValues.SDT_USER_ERROR_INCORRECT_USERNAME_OR_PASSWORD);
        }
        
        //get SdtUser base on username and password
        Map<String, Object> params = new HashMap<>();
        params.put("username", sdtUserDTO.getUsername());
        params.put("password", sdtUserDTO.getPassword());        
        SdtUser sdtUser = factory.getSdtUserFacade().findSingleByQueryName(ConstantValues.SDT_USER_QUERY_AUTHENTICATE, params);
        
        //can not get SdtUser, this means incorrect username or password
        if (null == sdtUser) {
            return new RESTReponse(ConstantValues.SDT_USER_ERROR_INCORRECT_USERNAME_OR_PASSWORD);
        }
        
        //username and password are correct
        return new RESTReponse(ConstantValues.SDT_USER_STATUS_AUTHENTICATED);
    }

    @Override
    public RESTReponse resetPassword(SdtUserDTO sdtUserDTO) throws AppException, CustomReasonPhraseException {
        //if there is no username, return error
        if (null == sdtUserDTO.getUsername() || sdtUserDTO.getUsername().isEmpty()) {
            return new RESTReponse(ConstantValues.SDT_USER_ERROR_NO_USERNAME);
        }
        
        //get SdtUser base on username
        Map<String, Object> params = new HashMap<>();
        params.put("username", sdtUserDTO.getUsername());
        SdtUser sdtUser = factory.getSdtUserFacade().findSingleByQueryName(ConstantValues.SDT_USER_QUERY_FIND_BY_USERNAME, params);
        
        //can not get SdtUser, this means incorrect username
        if (null == sdtUser) {
            return new RESTReponse(ConstantValues.SDT_USER_ERROR_INCORRECT_USERNAME);
        }     
        
        //reset password        
        String generatedPassword = UUID.randomUUID().toString().substring(0, 10);
        sdtUser.setPassword(generatedPassword);        
        factory.getSdtUserFacade().edit(sdtUser);
        
        //return successful message and set the generated password to DTO
        sdtUserDTO.setPassword(generatedPassword);
        return new RESTReponse(ConstantValues.SDT_USER_STATUS_PASSWORD_CHANGE);
    }

    @Override
    public RESTReponse changePassword(SdtUserDTO sdtUserDTO) throws AppException, CustomReasonPhraseException {
        //if there is no username or password or old password, return error
        if (null == sdtUserDTO.getUsername() || sdtUserDTO.getUsername().isEmpty() || null == sdtUserDTO.getPassword() 
                || sdtUserDTO.getPassword().isEmpty() || null == sdtUserDTO.getOldPassword() || sdtUserDTO.getOldPassword().isEmpty()) {
            return new RESTReponse(ConstantValues.SDT_USER_ERROR_INCORRECT_USERNAME_OR_PASSWORD);
        }
        
        //if old password is the same as new password, return error
        if (sdtUserDTO.getOldPassword().equals(sdtUserDTO.getPassword())) {
            return new RESTReponse(ConstantValues.SDT_USER_ERROR_NEW_OLD_PASSWORD_SAME);
        }
        
        //get SdtUser base on username and old password
        Map<String, Object> params = new HashMap<>();
        params.put("username", sdtUserDTO.getUsername());
        params.put("password", sdtUserDTO.getOldPassword());
        SdtUser sdtUser = factory.getSdtUserFacade().findSingleByQueryName(ConstantValues.SDT_USER_QUERY_AUTHENTICATE, params);
        
        //can not get SdtUser, this means incorrect username or old password
        if (null == sdtUser) {
            return new RESTReponse(ConstantValues.SDT_USER_ERROR_INCORRECT_USERNAME_OR_PASSWORD);
        }     
        
        //change password
        sdtUser.setPassword(sdtUserDTO.getPassword());
        factory.getSdtUserFacade().edit(sdtUser);
        
        //return successful status
        return new RESTReponse(ConstantValues.SDT_USER_STATUS_PASSWORD_CHANGE);
    }
}
