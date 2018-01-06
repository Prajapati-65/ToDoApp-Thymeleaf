package com.springthymeleaf.service;

import java.util.List;

import com.springthymeleaf.model.Collaborater;
import com.springthymeleaf.model.DocDetails;
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
	
	public int saveCollborator(Collaborater collborate);

	public List<User> getListOfUser(int noteId);
	

	public List<Note> getCollboratedNotes(int userId);
	
	public int removeCollborator(int shareWith,int noteId);
	
	public List<DocDetails> getAllDoc();
	
	public DocDetails getDocDetails(int id);
}
