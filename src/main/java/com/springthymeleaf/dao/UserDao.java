package com.springthymeleaf.dao;

import java.util.List;

import com.springthymeleaf.model.User;

public interface UserDao {

	int saveUser(User user);
	
	public User loginUser(User user);
	
	/**
	 * @param integer id
	 * @return user
	 */
	User getUserById(int id);

	/**
	 * @param user object
	 * @return boolean
	 */
	boolean updateUser(User user);
	
	/**
	 * @param String email
	 * @return user
	 */
	public User emailValidation(String email);
	
	/**
	 * @return List of users
	 */
	public List<User> getUserEmailId();
	
	
}
