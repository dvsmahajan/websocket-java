package com.dvsapp.dvsfinalwebsocketapp.config.jwt;


import java.io.IOException;

import javax.security.sasl.AuthenticationException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JwtTokenAuthenticationFilter extends GenericFilterBean {

    private JwtTokenProvider jwtTokenProvider;

    public JwtTokenAuthenticationFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain filterChain)
        throws IOException, ServletException {
    	try {
    		log.info("came in do filter.....");
        String token = jwtTokenProvider.resolveToken((HttpServletRequest) req);
        log.info("token is {}",token);
        if(token!=null)
        {
        	log.info("got the token {}",token);
    		boolean isValidToken= jwtTokenProvider.validateToken(token);
    		log.info("is Valid Token {}",isValidToken);
            if (token != null && isValidToken) {
                Authentication auth = jwtTokenProvider.getAuthentication(token);
                log.info("Checking Authentication..........{}",auth.getPrincipal());
                if (auth != null) {
                    SecurityContextHolder.getContext().setAuthentication(auth);
                }
            }
        }
        log.info("calling do another Filter");
        filterChain.doFilter(req, res);
    	}catch (ExpiredJwtException ex) {
        	log.error("message is {} \n and exception is {}", ex.getMessage(),ex);
    		((HttpServletResponse) res).sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authentication failed");
//    		throw new AuthenticationException();
        }catch (Exception e) {
        	log.error("message is {} \n and exception is {}", e.getMessage(),e);
        	filterChain.doFilter(req, res);
        }
    }

}
