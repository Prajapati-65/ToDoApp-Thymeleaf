package com.springthymeleaf.utility.jms;

/**
 * @author Om Prajapati
 *
 */
public interface MailService {

	/**
	 * @param String to
	 * @param String text
	 */
	public void sendEmail(String to, String text);
}
