package com.springthymeleaf.utility.jms;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;


/**
 * @author Om Prajapati
 *
 */
@Service
public class JmsMessageSendingServiceImplement implements JmsMessageSendingService {
	
	@Autowired
	private JmsTemplate jmsTemplate; 
	
	@Override
	public void sendMessage(String emailId , String message) {
		
		JmsData jmsData=new JmsData();
		jmsData.setEmailId(emailId);
		jmsData.setMessage(message);
		jmsTemplate.send(new MessageCreator() {
			
			@Override
			public Message createMessage(Session session) throws JMSException {
				Message message=session.createObjectMessage(jmsData);
				return message;
			}
		});
		
	}


}
