/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package user.ejb.eao;

import common.exception.CustomReasonPhraseException;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;
import user.entity.SdtUser;

/**
 *
 * @author longnguyen
 */
@Local
public interface SdtUserFacadeLocal {

    SdtUser create(SdtUser sdtUser);

    void edit(SdtUser sdtUser);

    void remove(SdtUser sdtUser);

    SdtUser find(Object id);

    List<SdtUser> findAll();

    List<SdtUser> findRange(int[] range);

    int count();

    public SdtUser findSingleByQueryName(String queryName, Map<String, Object> params);

}
