package com.springthymeleaf.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.springthymeleaf.model.Collaborater;
import com.springthymeleaf.model.DocDetails;
import com.springthymeleaf.model.Note;
import com.springthymeleaf.model.User;
import com.springthymeleaf.service.NoteService;
import com.springthymeleaf.service.UserService;
import com.springthymeleaf.utility.token.VerifiedJWT;

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
	
	
	
								
	
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	public ModelAndView deleteNote(@PathVariable("id") int noteId ,HttpServletRequest request) {
		
		int id = (int) request.getAttribute("userId");
		User user = userService.getUserById(id);
		
		Note note = new Note();
		note.setNoteId(noteId);
		note.setUser(user);
		
		boolean delete = noteService.deleteNote(note);
		
		if (delete == true) {
			
			return new ModelAndView("redirect:/user/trash");
			
		} else {
		
			return new ModelAndView("redirect:/user/home");
		}
	}
	
	
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public ModelAndView update(Note note , HttpServletRequest request) {
		int userId = (int) request.getAttribute("userId");
		User user = userService.getUserById(userId);
		
		ModelAndView modelAndView = new ModelAndView("redirect:/user/home");
		/*	
		Note noteById = noteService.getNoteById(note.getNoteId());
		Date createDate = noteById.getCreatedDate();
		note.setCreatedDate(createDate);
		Date modifiedDate = new Date();
		note.setModifiedDate(modifiedDate);
		*/
		note.setUser(user);
		
		boolean isUpdated = noteService.updateNote(note);

		if (isUpdated != true) {
			modelAndView.addObject("note", new Note());
			return modelAndView;
			
		} else {
			
			return modelAndView;
		}
	}
	
	@RequestMapping(value="/otherNote/{option}/{id}",method = RequestMethod.GET)
	public ModelAndView otherFunction(@PathVariable("option") int option, @PathVariable("id") int id , HttpServletRequest request) {
		int usetId = (int) request.getAttribute("userId");
		User user = userService.getUserById(usetId);
		
		Note note=noteService.getNoteById(id);
		note.setUser(user);
		
		Date date=new Date();
		note.setModifiedDate(date);
		
		if(option==1) {
			note.setArchiveStatus("true");
		
		}else if(option==2) {
			note.setArchiveStatus("false");
			noteService.updateNote(note);
			return new ModelAndView("redirect:/user/archive");
		
		}else if(option==3){
			note.setTrashStatus("true");
	
		}else if(option==4) {
			note.setTrashStatus("false");
			noteService.updateNote(note);
			return new ModelAndView("redirect:/user/trash");
		}
		noteService.updateNote(note);
		return new ModelAndView("redirect:/user/home");
	}
	
	@RequestMapping(value="/copy/{id}",method = RequestMethod.GET)
	public ModelAndView makeCopy(@PathVariable int id,HttpServletRequest request) {
		Note copyNote = noteService.getNoteById(id);
		
		int userId = (int) request.getAttribute("userId");
		User noteUser=userService.getUserById(userId);
		
		Date date = new Date();
		copyNote.setCreatedDate(date);
		copyNote.setModifiedDate(date);
		copyNote.setUser(noteUser);
		
		noteService.createNote(copyNote);
		return new ModelAndView("redirect:/user/home");
	}
	
	@RequestMapping("/archive")
	public ModelAndView archivePage(HttpServletRequest request) {
		int userId = (int) request.getAttribute("userId");
		User noteUser=userService.getUserById(userId);
		
		ModelAndView modelAndView = new ModelAndView();
		
		modelAndView.setViewName("archive");
		modelAndView.addObject("user", noteUser);
		
		Note note = new Note();
		modelAndView.addObject("note", note);
		
		List<Note> allNotes = noteService.getAllNotes(noteUser);
		modelAndView.addObject("allNotes", allNotes);
		return modelAndView;
		
	}
	
	@RequestMapping("/trash")
	public ModelAndView trashPage(HttpServletRequest request) {
		int userId = (int) request.getAttribute("userId");
		User noteUser=userService.getUserById(userId);
		
		ModelAndView modelAndView = new ModelAndView();
		
		modelAndView.setViewName("trash");
		modelAndView.addObject("user", noteUser);
		
		Note note = new Note();
		modelAndView.addObject("note", note);
		
		List<Note> allNotes = noteService.getAllNotes(noteUser);
		modelAndView.addObject("allNotes", allNotes);
		return modelAndView;
	
	}
	
	@RequestMapping(value = "/collaborate", method = RequestMethod.POST)
	public ResponseEntity<List<User>> getNotes(@RequestBody Collaborater collborator, HttpServletRequest request) {
		List<User> userList = new ArrayList<User>();

		Collaborater collaborate = new Collaborater();

		Note note = (Note) collborator.getNoteId();
		User shareUser = (User) collborator.getShareId();
		User owner = (User) collborator.getOwnerId();

		shareUser = userService.emailValidate(shareUser.getEmail());

		String accessToken = request.getHeader("token");

		User user = userService.getUserById(VerifiedJWT.verify(accessToken));

		userList = noteService.getListOfUser(note.getNoteId());

		if (user != null) {
			if (shareUser != null && shareUser.getId() != owner.getId()) {
				int i = 0;
				int variable = 0;
				while (userList.size() > i) {
					if (userList.get(i).getId() == shareUser.getId()) {
						variable = 1;
					}
					i++;
				}
				if (variable == 0) {
					collaborate.setNoteId(note);
					collaborate.setOwnerId(owner);
					collaborate.setShareId(shareUser);
					if (noteService.saveCollborator(collaborate) > 0) {
						userList.add(shareUser);
					} else {
						ResponseEntity.ok(userList);
					}
				}
			}
		}
		return ResponseEntity.ok(userList);
	}

	
	@RequestMapping(value = "/getOwner", method = RequestMethod.POST)
	public ResponseEntity<User> getOwner(@RequestBody Note note, HttpServletRequest request) {

		String accessToken = request.getHeader("token");

		User user = userService.getUserById(VerifiedJWT.verify(accessToken));

		if (user != null) {
			Note noteComplete = noteService.getNoteById(note.getNoteId());
			User owner = noteComplete.getUser();
			return ResponseEntity.ok(owner);
		} else {
			return ResponseEntity.ok(null);
		}
	}

	
	@RequestMapping(value = "/removeCollborator", method = RequestMethod.POST)
	public ModelAndView removeCollborator(@RequestBody Collaborater collborator, HttpServletRequest request) {

		ModelAndView modelAndView = new ModelAndView();
		int shareWith = collborator.getShareId().getId();
		int noteId = collborator.getNoteId().getNoteId();
		Note note = noteService.getNoteById(noteId);

		User owner = note.getUser();
		String token = request.getHeader("token");

		User user = userService.getUserById(VerifiedJWT.verify(token));
		if (user != null) {
			if (owner.getId() != shareWith) {
				if (noteService.removeCollborator(shareWith, noteId) > 0) {
					
					return modelAndView;

				} else {
					
					return modelAndView;
				}
			} else {
				
				return modelAndView;
			}
		}

		else {
			return modelAndView;
		}
	}
	
	
	
}
