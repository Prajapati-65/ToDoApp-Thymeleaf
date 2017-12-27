package com.springthymeleaf.utility.jms;


/**
 * @author Om Prajapati
 *
 */
public interface JmsMessageReceivingService {
	
	/**
	 * @param JmsData object
	 */
	public void messageReceive(JmsData jmsData);

}
