package com.springthymeleaf.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.springthymeleaf.model.User;
import com.springthymeleaf.service.UserService;

@Controller
public class UserController {

	@Autowired
	UserService userService;
	
	@RequestMapping(value = "/{name}", method = RequestMethod.GET)
	public String getMovie(@PathVariable String name, ModelMap model) {
		model.addAttribute("name", name);
		return "hello";
	}

	@RequestMapping(value = "/registration", method = RequestMethod.GET)
	public ModelAndView registration() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("user", new User());
		modelAndView.setViewName("registration");
		return modelAndView;
	}
	

	@RequestMapping(value = "/registerUser", method = RequestMethod.POST)
	public ModelAndView save(User user) {
		ModelAndView modelAndView = new ModelAndView();
		userService.saveUser(user);
		modelAndView.addObject("successMessage", "User has been registered successfully");
		modelAndView.setViewName("hello");
		return modelAndView;
	}
	

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView login() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("loginuser", new User());
		modelAndView.setViewName("login");
		return modelAndView;
	}
	
	
	@RequestMapping(value = "/loginUser", method = RequestMethod.POST)
	public ModelAndView loginUser(User user) {
		ModelAndView modelAndView = new ModelAndView();
		userService.loginUser(user);
		modelAndView.addObject("successMessage", "Login user successfully");
		modelAndView.setViewName("hello");
		return modelAndView;
	}

	@RequestMapping(value = "/homeUser", method = RequestMethod.POST)
	public ModelAndView homeUser(User user) {
		ModelAndView modelAndView = new ModelAndView();
		userService.emailValidate(user.getEmail());
		modelAndView.addObject("successMessage", "Login user successfully");
		modelAndView.setViewName("home");
		return modelAndView;
	}
	
}
