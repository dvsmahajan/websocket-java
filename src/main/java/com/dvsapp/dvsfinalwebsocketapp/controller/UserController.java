package com.dvsapp.dvsfinalwebsocketapp.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dvsapp.dvsfinalwebsocketapp.config.jwt.JwtTokenProvider;
import com.dvsapp.dvsfinalwebsocketapp.dto.LoginUserDTO;
import com.dvsapp.dvsfinalwebsocketapp.entity.UserEntity;
import com.dvsapp.dvsfinalwebsocketapp.service.IUserService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

	@Autowired
	private IUserService iUserService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtTokenProvider jwtTokenProvider;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@PostMapping("/save")
	public ResponseEntity<UserEntity> userRegistration(@RequestBody UserEntity user){
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		return ResponseEntity.status(HttpStatus.CREATED).body(iUserService.saveUser(user));
	}
	

	
	@PostMapping("/signin")
	public ResponseEntity<Object> signIn(@RequestBody LoginUserDTO userDTO){
		System.out.println(userDTO);
		
		if (!userDTO.getUsername().isEmpty() || !userDTO.getPassword().isEmpty()) {
			Optional<UserEntity> entity = iUserService.getByUsername(userDTO.getUsername());
			List<String> list = new ArrayList<>();
			log.info("qwertyuiop {}",entity);
			if (entity.isPresent()) {
				try {
					
					authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userDTO.getUsername(), userDTO.getPassword()));
					log.info("AUTHENTICATION SUCCeSSFULL");
				} catch (AuthenticationException e) {
					log.error("SOMEthiNG ::::>>>> {}",e.getMessage());
					throw new BadCredentialsException("Invalid username/password supplied");
				}
				String token = jwtTokenProvider.createToken(userDTO.getUsername(), list);

				Map<Object, Object> model = new HashMap<>();

				
				model.put("username", entity.get().getUsername());
				model.put("token", token);
				return ResponseEntity.ok(model);
			}


		}
		return null;
	}
	
	
	
	
	@PostMapping("/get/by/id")
	public ResponseEntity<UserEntity> getUserById(@RequestBody UserEntity userDTO){
		System.out.println(userDTO);
		UserEntity entity = iUserService.getUserById(userDTO.getId());
		
		return new ResponseEntity<>(entity,HttpStatus.OK);
	}
	
}
