package com.springthymeleaf.service;

import java.util.List;

import com.springthymeleaf.model.Note;
import com.springthymeleaf.model.User;


/**
 * @author Om Prajapati
 *
 */
public interface NoteService {

	int createNote(Note note);
	
	boolean updateNote(Note note);

	boolean deleteNote(Note note);
	
	Note getNoteById(int noteId);

	List<Note> getAllNotes(User user);

}
