/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package user.ejb.eao;

import common.ejb.eao.AbstractFacade;
import java.util.HashMap;
import java.util.Map;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import user.dto.SdtUserDTO;
import user.entity.SdtUser;

/**
 *
 * @author longnguyen
 */
@Stateless
public class SdtUserFacade extends AbstractFacade<SdtUser> implements SdtUserFacadeLocal {

    @PersistenceContext(unitName = "service_design_toolkit")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SdtUserFacade() {
        super(SdtUser.class);
    }

    @Override
    public SdtUser findUserByUsername(SdtUserDTO sdtUserDTO) {
        Map<String, Object> params = new HashMap<>();
        params.put("username", sdtUserDTO.getUsername());
        return findSingleByQueryName("SdtUser.findByUsername", params); 
    }
    
}
