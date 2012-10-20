package com.cinemall.Accessories;


import java.util.Properties;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


public class MailClient {
    private static final String SMTP_HOST_NAME = "smtp.gmail.com";
    private static final int SMTP_HOST_PORT = 465;
    private static final String SMTP_AUTH_USER = "cinimall7@gmail.com";
    private static final String SMTP_AUTH_PWD  = "industrial strength";
    
	 public static void send(String to,String sub,String text) throws Exception{
		
        Properties props = new Properties();

        props.put("mail.transport.protocol", "smtps");
        props.put("mail.smtps.host", SMTP_HOST_NAME);
        props.put("mail.smtps.auth", "true");
        /*
        System.setProperty("http.proxyHost", "myProxyServer.com");
        System.setProperty("http.proxyPort", "80");
        
        
        System.getProperties().put(proxySet,true);
        System.getProperties().put(proxyPort,8080);
        System.getProperties().put(proxyHost,host);
        */
        
        Session mailSession = Session.getDefaultInstance(props);
        mailSession.setDebug(true);
		
        Transport transport = mailSession.getTransport();

        MimeMessage message = new MimeMessage(mailSession);
        message.setSubject(sub);
        message.setContent(text, "text/html");

        message.addRecipient(Message.RecipientType.TO,
             new InternetAddress(to));

        transport.connect
          (SMTP_HOST_NAME, SMTP_HOST_PORT, SMTP_AUTH_USER, SMTP_AUTH_PWD);

        transport.sendMessage(message,
            message.getRecipients(Message.RecipientType.TO));
        transport.close();
	}

}
