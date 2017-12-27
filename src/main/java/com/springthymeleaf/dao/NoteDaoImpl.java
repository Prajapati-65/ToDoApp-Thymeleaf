package com.springthymeleaf.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.springthymeleaf.model.Note;
import com.springthymeleaf.model.User;



/**
 * @author Om Prajapti
 *
 */
public class NoteDaoImpl implements NoteDao {

	@Autowired
	SessionFactory sessionFactory;

	
	public int createNote(Note note) {
		int noteId = 0;
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			noteId = (Integer) session.save(note);
			transaction.commit();
			session.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return noteId;
	}

	
	public boolean updateNote(Note note) {
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			session.saveOrUpdate(note);
			transaction.commit();
			session.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	
	public Note getNoteById(int noteId) {
		Session session = sessionFactory.openSession();
		Note note = session.get(Note.class, noteId);
		session.close();
		return note;
	}

	public boolean deleteNote(Note note) {
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			session.delete(note);
			transaction.commit();
			session.close();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	
	public List<Note> getAllNotes(User user) {
		Session session = sessionFactory.openSession();
		@SuppressWarnings("deprecation")
		Criteria criteria = session.createCriteria(Note.class);
		criteria.add(Restrictions.eq("user", user));
		criteria.addOrder(Order.desc("modifiedDate"));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		@SuppressWarnings("unchecked")
		List<Note> notes = criteria.list();
		return notes;
	}
	
}
