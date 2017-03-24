/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package common.ejb.business;

import common.constant.ConstantValues;
import common.util.MailBridge;
import common.util.MailGoogle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.Asynchronous;
import javax.ejb.Singleton;
import javax.ejb.LocalBean;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.enterprise.event.Observes;
import javax.mail.Session;
import user.dto.SdtUserDTO;
import user.ejb.business.UserService;

/**
 *
 * @author longnguyen
 */
@Singleton
@LocalBean
public class MailService {

    @Resource(mappedName = "java:jboss/mail/Gmail")
    private Session mailSession;

    @Asynchronous
    @Lock(LockType.READ)
    public void sendResetPasswordMail(@Observes SdtUserDTO sdtUserDTO) {
        MailBridge mailBridge = new MailGoogle();
        try {
            mailBridge.sendMail(mailSession, ConstantValues.MAIL_RESET_PASSWORD_FROM_ADDRESS, sdtUserDTO.getUsername(), sdtUserDTO.getPassword(),
                    ConstantValues.MAIL_RESET_PASSWORD_SUBJECT);
        } catch (Exception ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
