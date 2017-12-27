package com.springthymeleaf.service;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springthymeleaf.dao.UserDao;
import com.springthymeleaf.model.User;
import com.springthymeleaf.utility.Encryption;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserDao userDao;

	@Autowired
	Encryption encryption;
	
	public void saveUser(User user) {

		String pwd = encryption.encryptPassword(user.getPassword());
		user.setPassword(pwd);
		userDao.saveUser(user);
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
