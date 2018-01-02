package com.springthymeleaf.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.springthymeleaf.model.Note;
import com.springthymeleaf.model.User;
import com.springthymeleaf.service.NoteService;
import com.springthymeleaf.service.UserService;

/**
 * @author Om Prajapati
 *
 */
@Controller
@RequestMapping(value="/user")
public class NoteController {

	@Autowired
	NoteService noteService;

	@Autowired
	UserService userService;

	
	@RequestMapping(value="/addNote",method = RequestMethod.POST)
	public ModelAndView addNote(Note note,HttpServletRequest request) {
		ModelAndView modelAndView=new ModelAndView("redirect:/user/home");
		modelAndView.addObject("note",note);
		
		int userId = (int) request.getAttribute("userId");
		User user = userService.getUserById(userId);
		Date date = new Date();
		
		note.setCreatedDate(date);
		note.setModifiedDate(date);
		note.setUser(user);
		
		if(!"".equalsIgnoreCase(note.getTitle()) || !"".equalsIgnoreCase(note.getDescription())) {
			noteService.createNote(note);
		}
		return modelAndView;
	}
	
	@RequestMapping(value="/home")
	public ModelAndView home(HttpServletRequest request)
	{
		int userId = (int) request.getAttribute("userId");
		User user = userService.getUserById(userId);
		
		ModelAndView modelAndView = new ModelAndView();
		
		modelAndView.setViewName("home");
		modelAndView.addObject("user", user);
		
		Note note = new Note();
		modelAndView.addObject("note", note);
		
		List<Note> allNotes = noteService.getAllNotes(user);
		modelAndView.addObject("allNotes", allNotes);
		return modelAndView;
	}
	
	
	
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
	public ModelAndView deleteNote(@PathVariable("id") int noteId ,HttpServletRequest request) {
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("home");
		int id = (int) request.getAttribute("userId");
		User user = userService.getUserById(id);
		
		Note note = new Note();
		modelAndView.addObject("note",note);
		
		note.setNoteId(noteId);
		note.setUser(user);
		
		boolean delete = noteService.deleteNote(note);
		
		if (delete != true) {
			modelAndView.addObject("user",user);
			modelAndView.addObject("note",note);
			List<Note> allNotes =noteService.getAllNotes(user);
			modelAndView.addObject("allNotes", allNotes);
			return modelAndView;
		} else {
		
			List<Note> allNotes =noteService.getAllNotes(user);
			modelAndView.addObject("allNotes", allNotes);
			return modelAndView;
		}
	}
	
	
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public ModelAndView update(@RequestBody Note note) {

		ModelAndView modelAndView = new ModelAndView();
		
		
		int noteid = note.getNoteId();

		Note noteById = noteService.getNoteById(noteid);

		Date createDate = noteById.getCreatedDate();
		note.setCreatedDate(createDate);

		User user = noteById.getUser();
		note.setUser(user);

		Date modifiedDate = new Date();
		note.setModifiedDate(modifiedDate);
		
		boolean isUpdated = noteService.updateNote(note);

		if (isUpdated != true) {

			return modelAndView;
			
		} else {

			
			return modelAndView;
		}
	}
	
/**	
	
	@RequestMapping(value="/edit/{id}",method = RequestMethod.GET)
	public ModelAndView editNote(@PathVariable int id,HttpSession session) {
		User noteUser=(User) session.getAttribute("user");
		Note currentNote = noteService.getNoteById(id);
		session.setAttribute("createTime", currentNote.getCreatedDate());
		ModelAndView modelAndView=new ModelAndView();
		modelAndView.setViewName("noteEdit");
		modelAndView.addObject("note",currentNote);
		return modelAndView;
	}
	
	@RequestMapping(value="/update",method = RequestMethod.POST)
	public ModelAndView update(Note note,HttpSession session)
	{
		User user=(User) session.getAttribute("user");
		note.setUser(user);
		Date date=new Date();
		note.setModifiedDate(date);
		Note currentNote=noteService.getNoteById(note.getNoteId());
		ModelAndView modelAndView=new ModelAndView();
		if(currentNote!=null) {
			Date date1=(Date) session.getAttribute("createTime");
			note.setCreatedDate(date1);
			noteService.updateNote(note);
		}
		
		modelAndView.addObject("user1",user);
		List<Note> notes=noteService.getAllNotes(user);
		modelAndView.addObject("notes",notes);
		modelAndView.addObject("note",note);
		System.out.println(modelAndView.getViewName());
		System.out.println("DDD ->"+modelAndView.wasCleared());
		modelAndView.setViewName("home");
		System.out.println(modelAndView.getViewName());
		
		return modelAndView;
		
	}
	
**/
	
	
}
