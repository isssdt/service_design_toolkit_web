/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package common.util;

/**
 *
 * @author longnguyen
 */
public interface MailBridge {
    public void sendMail(String from, String to, String content, String subject) throws Exception;
}
