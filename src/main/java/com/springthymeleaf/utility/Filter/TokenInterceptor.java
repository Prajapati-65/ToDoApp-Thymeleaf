package com.springthymeleaf.utility.Filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.springthymeleaf.utility.token.VerifiedJWT;


/**
 * @author Om Prajapati
 * 
 * this class is use to check and varify the JWT token  
 * and set the userId to the request  using setAttribute
 *
 */
public class TokenInterceptor implements HandlerInterceptor {


	
	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {

	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {

	}

	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object arg2) throws Exception {
	
		HttpSession session = request.getSession(false);
		session.getAttribute("user");
		int userId = VerifiedJWT.verify(session.getAttribute("user")+"");
		
		//int userId = VerifiedJWT.verify(request.getHeader("token"));
		if (userId == 0) {
			response.setStatus(511);
			return false;
		}
		request.setAttribute("userId", userId);
		return true;
	}

}
