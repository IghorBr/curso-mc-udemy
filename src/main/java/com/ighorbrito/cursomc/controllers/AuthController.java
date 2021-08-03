package com.ighorbrito.cursomc.controllers;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ighorbrito.cursomc.dto.EmailDTO;
import com.ighorbrito.cursomc.security.JWTUtil;
import com.ighorbrito.cursomc.security.UserSS;
import com.ighorbrito.cursomc.services.AuthService;
import com.ighorbrito.cursomc.services.UserService;

@RestController
@RequestMapping(value = "/auth")
public class AuthController {
	
	@Autowired
	private JWTUtil jwtUtil;
	
	@Autowired
	private AuthService authService;

	@RequestMapping(value = "/refresh_token", method = RequestMethod.POST)
	public ResponseEntity<Void> refreshToken(HttpServletResponse response) {
		UserSS user = UserService.authenticated();
		String token = jwtUtil.generateToken(user.getUsername());
		response.addHeader("Authorization", "Bearer " + token);
		response.addHeader("access-control-expose-headers", "Authorization");
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value = "/forgot", method = RequestMethod.POST)
	public ResponseEntity<Void> forgotPassword(@Valid @RequestBody EmailDTO emailDto) {
		authService.sendNewPassword(emailDto.getEmail());
		
		return ResponseEntity.noContent().build();
	}
}
