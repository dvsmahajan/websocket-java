package com.dvsapp.dvsfinalwebsocketapp.config.jwt;


import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {

//		response.sendError(HttpServletResponse.SC_UNAUTHORIZED	, "Jwt authentication failed");
		response.setContentType("application/json");
		final String expired = (String) request.getAttribute("expired");
		log.info("qwertyuiop :::>>> {}",expired);
		if (expired != null) {
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, expired);
		} else {
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid Login details");
		}
	}

}
