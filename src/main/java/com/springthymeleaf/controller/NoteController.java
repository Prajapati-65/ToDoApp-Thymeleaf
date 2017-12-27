package com.springthymeleaf.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
@RequestMapping(value = "/user")
public class NoteController {

	@Autowired
	NoteService noteService;

	@Autowired
	UserService userService;

	
	@RequestMapping(value="/addNote",method = RequestMethod.POST)
	public ModelAndView addNote(Note note,HttpServletRequest request) {
		int userId = (int) request.getAttribute("userId");
		User user = userService.getUserById(userId);
		Date date = new Date();
		note.setCreatedDate(date);
		note.setModifiedDate(date);
		note.setUser(user);
		noteService.createNote(note);
		ModelAndView modelAndView=new ModelAndView();
		modelAndView.setViewName("home");
		modelAndView.addObject("user",user);
		modelAndView.addObject("note",note);
		List<Note> allNotes =noteService.getAllNotes(user);
		modelAndView.addObject("allNotes", allNotes);
		return modelAndView;
	}
	
	

	
}
