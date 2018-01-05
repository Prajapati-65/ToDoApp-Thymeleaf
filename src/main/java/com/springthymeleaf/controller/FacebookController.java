package com.springthymeleaf.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springthymeleaf.model.User;
import com.springthymeleaf.service.UserService;
import com.springthymeleaf.socialLogin.modal.FbLogin;
import com.springthymeleaf.utility.token.GenerateJWT;

/**
 * @author Om Prajapati
 *
 */
@Controller
public class FacebookController {

	@Autowired
	UserService userService;


	/**
	 * @param response
	 * @throws IOException
	 * 
	 * this method find the url data on the fb account
	 */
	@RequestMapping(value = "/facebookConnection", method = RequestMethod.GET)
	public void beforeFbLogin(HttpServletResponse response) throws IOException {
		String fbUrl = FbLogin.getFbLoginUrl();
		response.sendRedirect(fbUrl);
	}

	/**
	 * @param request
	 * @param response
	 * @param session
	 * @throws IOException
	 */
	@RequestMapping(value = "/facebookLogin", method = RequestMethod.GET)
	public String afterFbLogin(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws IOException {

		String code;
		code = request.getParameter("code");

		String fbAccessToken = FbLogin.getFbAccessToken(code);

		String profileData = FbLogin.getProfileData(fbAccessToken);

		ObjectMapper objectMapper = new ObjectMapper();
		String email = objectMapper.readTree(profileData).get("email").asText();

		User user = userService.emailValidate(email);

		if (user == null) {
			
			user = new User();
			String firstName = objectMapper.readTree(profileData).get("first_name").asText();
			user.setFirstName(firstName);

			String lastName = objectMapper.readTree(profileData).get("last_name").asText();
			user.setLastName(lastName);

			String profileImage = objectMapper.readTree(profileData).get("picture").asText();
			user.setProfileImage(profileImage);
			
			user.setEmail(email);
			
			userService.saveUser(user, request);

			String accessToken = GenerateJWT.generate(user.getId());
			session.setAttribute("todoAppAccessToken", accessToken);
			return "redirect:/user/home";
		} else if(user!=null && user.getPassword()==null){
			String accessToken = GenerateJWT.generate(user.getId());
			session.setAttribute("todoAppAccessToken", accessToken);
			return "redirect:/user/home";
		} else{
			return "redirect:/";
		}
	}

}