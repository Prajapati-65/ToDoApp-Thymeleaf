package com.springthymeleaf.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;

import com.springthymeleaf.model.Collaborater;
import com.springthymeleaf.model.DocDetails;
import com.springthymeleaf.model.Note;
import com.springthymeleaf.model.User;



/**
 * @author Om Prajapti
 *
 */
@Service
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
	
	public int saveCollborator(Collaborater collborate) {
		int collboratorId=0;
		Session session=sessionFactory.openSession();
		Transaction transaction=session.beginTransaction();
		try{
		collboratorId=(Integer) session.save(collborate);
		transaction.commit();
		}catch(HibernateException e){
			e.printStackTrace();
			transaction.rollback();
		}finally{
			session.close();
		}
		return collboratorId;
	}

	
	public List<User> getListOfUser(int noteId) {
		Session session = sessionFactory.openSession();
		Query querycollab = session.createQuery("select c.shareId from Collaborater c where c.noteId= " + noteId);
		List<User> listOfSharedCollaborators = querycollab.list();
		System.out.println("listOfSharedCollaborators "+listOfSharedCollaborators);
		session.close();
		return listOfSharedCollaborators;	
	}
	
	
	public List<Note> getCollboratedNotes(int userId) {
		Session session = sessionFactory.openSession();
		Query query = session.createQuery("select c.noteId from Collaborater c where c.shareId= " + userId);
		List<Note> colllboratedNotes = query.list();
		session.close();
		return colllboratedNotes;
	}
	
	
	public int removeCollborator(int shareWith,int noteId) {
		Session session = sessionFactory.openSession();
		Transaction transaction=session.beginTransaction();
		Query query = session.createQuery("delete  Collaborater c where c.shareId= "+shareWith+" and c.noteId="+noteId );
		int status=query.executeUpdate();
		session.close();
		return status;
	}
	
	public List<DocDetails> getAllDoc(){
		Session session = sessionFactory.openSession();
		Criteria criteria = session.createCriteria(DocDetails.class);
		List<DocDetails> docDetails = criteria.list();
		return docDetails;
	}
	
	public DocDetails getDocDetails(int id) {
		Session session = sessionFactory.openSession();
		DocDetails docDetails = session.get(DocDetails.class, id);
		session.close();
		return docDetails;
		
	}
	
	
}
