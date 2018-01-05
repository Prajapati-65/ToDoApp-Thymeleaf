package com.springthymeleaf.controller;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.springthymeleaf.model.User;
import com.springthymeleaf.service.NoteService;
import com.springthymeleaf.service.UserService;
import com.springthymeleaf.utility.token.GenerateJWT;

@Controller
public class UserController {

	@Autowired
	UserService userService;

	@Autowired 
	NoteService noteService;
	
	@RequestMapping("/")
	public String login(ModelMap modelMap) {
		modelMap.put("user", new User());
		return "login";
	}

	@RequestMapping("/registration")
	public String registerForm(ModelMap modelMap) {
		modelMap.put("user", new User());
		return "registration";
	}

	@RequestMapping(value = "/registerUser", method = RequestMethod.POST)
	public String saveRegistration(User user) {
		ModelMap modelMap = new ModelMap();
		if (user == null) {
			modelMap.put("user", user);
			return "registration";
		}
		userService.saveUser(user);
		modelMap.put("user", user);
		return "login";
	}

	@RequestMapping(value = "/loginUser", method = RequestMethod.POST)
	public ModelAndView loginUser(User user, HttpSession session ,HttpServletRequest request) {
		int userId = userService.loginUser(user);
		if (userId != 0) {
			String jwt = GenerateJWT.generate(userId);
			session.setAttribute("user", jwt);
			request.setAttribute("token", jwt);
			return new ModelAndView("redirect:/user/home");
		}
		else {
			return new ModelAndView("redirect:/loginUser");
		}
	}
	

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public ModelAndView logout(HttpSession session) {
		ModelAndView modelAndView = new ModelAndView();
		if (session != null) {
			session.removeAttribute("user");
			session.invalidate();
			modelAndView.addObject("user", new User());
			modelAndView.setViewName("login");
			return modelAndView;
		} else {
			modelAndView.setViewName("home");
			return modelAndView;
		}
	}

}
