package com.bits.pilani.api_gateway.main;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.HttpMethod;

@Component
@Order(1)
public class JwtAuthenticationFilter extends OncePerRequestFilter {
	
	@Autowired
	RestTemplate restTemplate;
	
	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
		var isAuthTokenUrl = request.getRequestURI().contains("/auth/token");
		var isAuthorizeUrl = request.getRequestURI().contains("/auth/authorize");
		var isUserCreationUrl = request.getRequestURI().contains("/user") && request.getMethod().equals(HttpMethod.POST);
		return isAuthTokenUrl || isAuthorizeUrl || isUserCreationUrl;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {					

		HttpHeaders headers = new HttpHeaders();
		
		if(request.getHeader(HttpHeaders.AUTHORIZATION) != null) {
			headers.add(HttpHeaders.AUTHORIZATION, request.getHeader(HttpHeaders.AUTHORIZATION));			
		}
		
		var httpEntity = new HttpEntity<>(headers);

		try {
			restTemplate.exchange("http://user-service/auth/authorize", org.springframework.http.HttpMethod.GET, httpEntity, Map.class);
			filterChain.doFilter(request, response);			
		} catch(HttpClientErrorException e) {
			response.setStatus(e.getStatusCode().value());
			response.setContentType(MediaType.APPLICATION_JSON_VALUE);
			response.getWriter().print(e.getResponseBodyAsString());
		}
	}
}
