/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package common.util;

import java.util.Properties;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author longnguyen
 */
public class MailGoogle implements MailBridge {

    @Override
    public void sendMail(Session mailSession, String from, String to, String content, String subject) throws Exception {
//        Properties mailServerProperties = System.getProperties();
//        mailServerProperties.put("mail.smtp.port", "587");
//        mailServerProperties.put("mail.smtp.auth", "true");
//        mailServerProperties.put("mail.smtp.starttls.enable", "true");       
//        
//        Session getMailSession = Session.getDefaultInstance(mailServerProperties, null);
//        MimeMessage generateMailMessage = new MimeMessage(getMailSession);        
//        generateMailMessage.setFrom(new InternetAddress(from));
//        generateMailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(to));        
//        generateMailMessage.setSubject(subject);        
//        generateMailMessage.setContent(content, "text/html");
//        
//        Transport transport = getMailSession.getTransport("smtp");        
//        transport.connect("smtp.gmail.com", "iss.service.design.toolkit@gmail.com", "HoangYen1601");
//        transport.sendMessage(generateMailMessage, generateMailMessage.getAllRecipients());        
//        transport.close();
        MimeMessage m = new MimeMessage(mailSession);
        Address[] toAddresses = new InternetAddress[]{new InternetAddress(to)};

        m.setRecipients(Message.RecipientType.TO, toAddresses);
        m.setSubject(subject);
        m.setSentDate(new java.util.Date());
        m.setContent(content, "text/plain");

        Transport.send(m);
    }
}
