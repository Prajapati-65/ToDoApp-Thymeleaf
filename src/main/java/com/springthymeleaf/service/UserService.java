package com.springthymeleaf.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.springthymeleaf.model.User;

public interface UserService {

	public void saveUser(User user , HttpServletRequest request);
	
	public void saveUser(User user);
	
	public int loginUser(User user);
	
	/**
	 * @param integer id
	 * @return user
	 */
	User getUserById(int id);

	/**
	 * @param User object
	 * @return boolean
	 */
	boolean updateUser(User user);

	/**
	 * @param String email
	 * @return User object
	 */
	public User emailValidate(String email);
	
	/**
	 * @return List of all users
	 */
	public List<User> getUserEmailId();
}
