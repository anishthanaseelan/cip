/*******************************************************************************
 * (C) Copyright 2016 Cognizant Worldwide Limited (fka, CTS UK Ltd) (“CWW”)
 *  
 * Licensed under the Apache License, Version 2.0 (the "License");
 * You may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *  
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *  
 *   Contributors:
 *       Cognizant Worldwide Limited (fka, CTS UK Ltd) (“CWW”)
 *******************************************************************************/
package com.cts.cip.util;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class NotificationManager {
	static Logger logger = LoggerFactory.getLogger(NotificationManager.class);
	public static final String NOTIFICATION_PROPS_FILE="properties/notification.properties";
	

	private Session session;
	private Map<String,String> propsMap;
	
	enum Protocol{
		SMTP("SMTP"),
		SMTPS("SMTPS"),
		TLS("TLS");

		public String type;
		
		private Protocol(String type){
			this.type = type;
		}
		
		public String toString(){
			return this.type;
		}
		

	};
	
	
	public void initialize() {
		
		Properties props ;
		String authenticated;
		String userName;
		String password;
		
		propsMap = getMapFromPropertyFile(NOTIFICATION_PROPS_FILE);
		
		
		
		if (null != propsMap){
			props = new Properties();
			props.put(CommonConstants.SMTP_HOST, propsMap.get(CommonConstants.SMTP_HOST));
			props.put(CommonConstants.SMTP_PORT, propsMap.get(CommonConstants.SMTP_PORT));
			
			authenticated = propsMap.get(CommonConstants.SMTP_AUTHENTICATE);
			userName = propsMap.get(CommonConstants.SMTP_USERNAME);
			password = propsMap.get(CommonConstants.SMTP_PASSWORD);
			
			Protocol protocol = Protocol.valueOf(propsMap.get(CommonConstants.SMTP_PROTOCOL));
			switch(protocol){
			 case SMTPS:
			        props.put("mail.smtp.ssl.enable", true);
			        break;
			 case TLS:
			        props.put("mail.smtp.starttls.enable", true);
			        break;
			 case SMTP:
				break;
			 default:
				break;
			}
			
			Authenticator authenticator = null;
			if (Boolean.valueOf(authenticated)) {
			    props.put("mail.smtp.auth", true);
			    authenticator = new Authenticator() {
			        private PasswordAuthentication pa = new PasswordAuthentication(userName, password);
			        @Override
			        public PasswordAuthentication getPasswordAuthentication() {
			            return pa;
			        }
			    };
			}
			
			session = Session.getInstance(props, authenticator);
			
			
			
		}
		
	}
	
	public void sendNotification(String message, String recipients ,String subject) {
		
		String receipientList = null ;
		String fromAddress ;
		if (null != propsMap && null != session){
			MimeMessage mimeMessage = new MimeMessage(session);
			
			fromAddress = propsMap.get(CommonConstants.MAIL_FROM_ADDRESS);		
			
			if (null == recipients)
				receipientList = propsMap.get(CommonConstants.MAIL_TO_ADDRESS_LIST);
			
		
				try {
					mimeMessage.setFrom(new InternetAddress(fromAddress));
					InternetAddress[] toAddress = getToAddressList(receipientList);
					mimeMessage.setRecipients(Message.RecipientType.TO, toAddress);
					mimeMessage.setSubject(subject);
					mimeMessage.setSentDate(new Date());
					mimeMessage.setText(message);
					Transport.send(mimeMessage);
					logger.debug("Email Sent Successfully");
				} catch (MessagingException mesgEx) {
					logger.debug(message);
					logger.error("Unable to send Email Notification -" + mesgEx.getMessage());
					
				}
					
		}else{
			logger.error("Unable to send Email Notification - Mail session not initialized " );
			if(null == session){
				logger.error("Unable to create SMTP session for sending email");
				
			}
		}
		
	
		
			
	}
	
	
	
	private InternetAddress[] getToAddressList(String toList) throws AddressException {
		String[] addressList = toList.split(",");
		InternetAddress[] iaddr = new InternetAddress[addressList.length];
		for(int index=0 ; index< addressList.length;index++){
			iaddr[index] = new InternetAddress(addressList[index]);
		}
		
		return iaddr;
		
		
	}

	public Session getSession() {
		return session;
	}

	public void setSession(Session session) {
		this.session = session;
	}
	
	/** Load data from property file
	 * @param fileName
	 * @return
	 * @throws CarrierClientException
	 */
	public Map<String,String> getMapFromPropertyFile(String fileName)  {
		
		HashMap<String, String> propMap = new HashMap<>();
		try {
			InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(fileName);
			BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
			String line;
		    while ((line = reader.readLine()) != null) {
		    	String[] keyVal = line.split("=",2);
		    	if (keyVal.length==2) {
		    		propMap.put(keyVal[0], keyVal[1]);
		    	}
		     }		     
		     reader.close();
			if (inputStream == null)
				throw new IOException(fileName + "- properties file not found.");
			
		} catch (IOException ioexcep) {
			logger.error("Exception " + ioexcep);
			logger.error("Unable to Read property File : " + fileName);
			
		}
		return propMap;

	}


	
	public static void main(String[] args){
//	    CarrierCommonUtils commonUtils = new CarrierCommonUtils();
//		NotificationManager nManager = new NotificationManager();
//		nManager.setCommonUtils(commonUtils);
//		nManager.initialize();
//		nManager.sendNotification("Test Message", null, "Test Subject");
			
		
	}

	
	
}
