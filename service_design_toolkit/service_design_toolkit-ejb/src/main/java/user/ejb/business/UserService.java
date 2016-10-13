/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package user.ejb.business;

import common.constant.ConstantValues;
import common.dto.QueryParamValue;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import org.apache.commons.beanutils.BeanUtils;
import user.dto.FieldResearcherDTO;
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

    @Override
    public void refreshCurrentLocation(FieldResearcherDTO fieldResearcherDTO) {
        FieldResearcher fieldResearcher;
        SdtUser sdtUser;
        try {
            //check whether User of this Field Researcher already exist 
            sdtUser = sdtUserFacade.findSingleByQueryName("SdtUser.findByUsername",
                    new QueryParamValue[]{new QueryParamValue("username", fieldResearcherDTO.getSdtUserDTO().getUsername())});

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
                sdtUser.setUserRoleId(userRoleFacade.findSingleByQueryName("UserRole.findByRoleName",
                        new QueryParamValue[]{new QueryParamValue("roleName", ConstantValues.FIELD_RESEARCHER_ROLE_NAME)}));              
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
    public FieldResearcher getFieldResearcherByName(user.dto.FieldResearcherDTO fieldResearcherDTO) {
        SdtUser sdtUser = sdtUserFacade.findSingleByQueryName("SdtUser.findByUsername",
                    new QueryParamValue[]{new QueryParamValue("username", fieldResearcherDTO.getSdtUserDTO().getUsername())});
        return sdtUser.getFieldResearcher();
    }
    
    
}
