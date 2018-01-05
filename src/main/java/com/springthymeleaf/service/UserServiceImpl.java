package com.springthymeleaf.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springthymeleaf.dao.UserDao;
import com.springthymeleaf.utility.jms.JmsMessageSendingService;
import com.springthymeleaf.model.User;
import com.springthymeleaf.utility.Encryption;
import com.springthymeleaf.utility.UrlTemplate;
import com.springthymeleaf.utility.jms.JmsMessageSendingServiceImplement;
import com.springthymeleaf.utility.token.GenerateJWT;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserDao userDao;

	@Autowired
	Encryption encryption;
	
	@Autowired
	JmsMessageSendingService jmsMessageSendingService;
	
	public void saveUser(User user) {
		
		
		String pwd = encryption.encryptPassword(user.getPassword());
		user.setPassword(pwd);
		int userID = userDao.saveUser(user);
		if (userID != 0) {
			String activeToken = GenerateJWT.generate(userID);
			jmsMessageSendingService.sendMessage(user.getEmail(),
					"Please click on this link within 1-hours otherwise your account is not activated--> " + activeToken);
		}
	}
	
	
	/*public void saveUser1(User user) {

		String pwd = encryption.encryptPassword(user.getPassword());
		user.setPassword(pwd);
		userDao.saveUser(user);
	}*/

	
	public void saveUser(User user, HttpServletRequest request) {

		String pwd = encryption.encryptPassword(user.getPassword());
		user.setPassword(pwd);
		int userID = userDao.saveUser(user);
		if (userID != 0) {
			String activeToken = GenerateJWT.generate(userID);
			
			String url = UrlTemplate.urlTemplate(request);
			url = url + "verifyMail/" + activeToken;
			JmsMessageSendingServiceImplement jmsMessageSendingService = new JmsMessageSendingServiceImplement();
			
			jmsMessageSendingService.sendMessage(user.getEmail(), "Please click on this link within 1-hours otherwise your account is not activated--> " + url);
		}
	}
	
	

	public int loginUser(User user) {
		User loggedInUser = userDao.loginUser(user);
		
		if (loggedInUser == null) {
			return 0;
		}

		return loggedInUser.getId();
	}

	@Override
	public User getUserById(int id) {

		return userDao.getUserById(id);
	}

	@Override
	public boolean updateUser(User user) {

		return userDao.updateUser(user);
	}

	public User emailValidate(String email) {
		User user = userDao.emailValidation(email);
		if (user != null) {
			return user;
		}
		return null;
	}

	public List<User> getUserEmailId() {
		return userDao.getUserEmailId();
	}

}
