package com.feng.spring.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;

public class CustomLogoutHandler implements LogoutHandler {

	@Override
	public void logout(HttpServletRequest arg0, HttpServletResponse arg1,
			Authentication arg2) {
		System.out.println("Hello Wolrd");
	}

}
