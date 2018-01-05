package com.springthymeleaf.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springthymeleaf.model.User;
import com.springthymeleaf.service.UserService;
import com.springthymeleaf.socialLogin.modal.GoogleLogin;
import com.springthymeleaf.utility.Response;
import com.springthymeleaf.utility.token.GenerateJWT;

/**
 * @author Om Prjapati
 *
 */
@RestController
public class GoogleController {

	@Autowired
	UserService userService;


	/**
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/googleConnection", method = RequestMethod.GET)
	public void beforeGoogle(HttpServletResponse response) throws IOException {
		String googleLoginPageUrl = GoogleLogin.generateLoginUrl();
		response.sendRedirect(googleLoginPageUrl);
	}

	/**
	 * @param request
	 * @param response
	 * @param session
	 * @throws IOException
	 */
	@RequestMapping(value = "/googleLogin", method = RequestMethod.GET)
	public ModelAndView afterLogin(HttpServletRequest request, HttpSession session)
			throws IOException {

			Response customResponse = new Response();
		if (request.getParameter("error") != null) {
			customResponse.setMessage(request.getParameter("error"));
			return new ModelAndView("redirect:/");
		} else {

			String code;
			code = request.getParameter("code");

			String googleAccessToken = GoogleLogin.getAccessToken(code);

			String profileData = GoogleLogin.getProfileData(googleAccessToken);

			ObjectMapper objectMapper = new ObjectMapper();
			String email = objectMapper.readTree(profileData).get("email").asText();
			User user = userService.emailValidate(email);

			if (user == null) {
				user = new User();
				user.setEmail(email);
				String firstName = objectMapper.readTree(profileData).get("given_name").asText();
				user.setFirstName(firstName);
				String lastName = objectMapper.readTree(profileData).get("family_name").asText();
				user.setLastName(lastName);

				String profileImage = objectMapper.readTree(profileData).get("picture").asText();
				user.setProfileImage(profileImage);

				userService.saveUser(user);

				String accessToken = GenerateJWT.generate(user.getId());
				
				session.setAttribute("todoAppAccessToken", accessToken);
				return new ModelAndView("redirect:/user/home");
			} else if (user != null && user.getPassword() == null) {
				String accessToken = GenerateJWT.generate(user.getId());
				session.setAttribute("todoAppAccessToken", accessToken);
				return new ModelAndView("redirect:/user/home");
			} else {
				return new ModelAndView("redirect:/");
			}
		}
	}
}
