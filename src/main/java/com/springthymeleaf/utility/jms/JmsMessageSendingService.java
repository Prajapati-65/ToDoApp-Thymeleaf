package com.springthymeleaf.utility.jms;

/**
 * @author Om Prajapati
 *
 */
public interface JmsMessageSendingService {
	
	/**
	 * @param String emailId
	 * @param String message
	 */
	public void sendMessage(String emailId , String message);
	
}
