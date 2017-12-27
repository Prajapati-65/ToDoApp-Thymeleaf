package com.springthymeleaf.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.springthymeleaf.dao.NoteDao;
import com.springthymeleaf.model.Note;
import com.springthymeleaf.model.User;


/**
 * @author Om Prajapati
 *
 */
public class NoteServiceImpl implements NoteService {

	@Autowired
	NoteDao noteDao;

	public int createNote(Note note) {
		
		return noteDao.createNote(note);
	}

	public boolean updateNote(Note note) {

		return noteDao.updateNote(note);
	}

	public boolean deleteNote(Note note) {

		return noteDao.deleteNote(note);

	}

	public Note getNoteById(int noteId) {

		return noteDao.getNoteById(noteId);
	}

	public List<Note> getAllNotes(User user) {

		return noteDao.getAllNotes(user);
	}

	
}
