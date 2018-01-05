package com.springthymeleaf.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springthymeleaf.model.User;
import com.springthymeleaf.utility.Encryption;

@Service
public class UserDaoImpl implements UserDao {

	@Autowired
	SessionFactory sessionFactory;

	@Autowired
	Encryption encryption;
	
	public int saveUser(User user) {
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			int id = (int) session.save(user);
			return id;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			transaction.commit();
			session.close();
		}
		return 0;
	}

	public User loginUser(User user) {
		Session session = sessionFactory.openSession();
		@SuppressWarnings("deprecation")
		Criteria criteria = session.createCriteria(User.class);
		criteria.add(Restrictions.eq("email", user.getEmail()));
		criteria.add(Restrictions.eq("password", encryption.encryptPassword(user.getPassword())));
		User finalUser = (User) criteria.uniqueResult();
		if (finalUser == null) {
			session.close();
			return null;
		}
		session.close();
		return finalUser;
	}
	
	
	@Override
	public User getUserById(int id) {
		Session session = sessionFactory.openSession();
		User user = session.get(User.class, id);
		session.close();
		return user;
	}

	
	@Override
	public boolean updateUser(User user) {
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			session.saveOrUpdate(user);
			transaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
		session.close();
		return true;
	}

	
	public User emailValidation(String email) {

		Session session = sessionFactory.openSession();
		@SuppressWarnings("deprecation")
		Criteria criteria = session.createCriteria(User.class);
		criteria.add(Restrictions.eq("email", email));
		User user = (User) criteria.uniqueResult();
		session.close();
		return user;
	}

	@Override
	public List<User> getUserEmailId() {
		Session  session = sessionFactory.openSession();
		Criteria criteria = session.createCriteria(User.class);
		criteria.setProjection(Projections.property("email"));
		List<User> userList = criteria.list();
		return userList;
	}
	
	
}
