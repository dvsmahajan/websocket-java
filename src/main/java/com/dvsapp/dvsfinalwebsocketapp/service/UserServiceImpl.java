package com.dvsapp.dvsfinalwebsocketapp.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dvsapp.dvsfinalwebsocketapp.dto.LoginUserDTO;
import com.dvsapp.dvsfinalwebsocketapp.entity.UserEntity;
import com.dvsapp.dvsfinalwebsocketapp.repository.IUserRepository;

@Service
public class UserServiceImpl implements IUserService {

	@Autowired
	IUserRepository iUserRepository;
	
	@Override
	public UserEntity saveUser(UserEntity user) {
		return iUserRepository.save(user);
	}

	@Override
	public UserEntity findUserByUsernameAndPassword(LoginUserDTO user) {
		
		return iUserRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword());
	}

	@Override
	public UserEntity loadUserByUsername(String username) {
		Optional<UserEntity> optUser = iUserRepository.findByUsername(username);
		if(optUser.isPresent()) {
			return optUser.get();
		}
		return null;
	}

	@Override
	public Optional<UserEntity> getByUsername(String username) {
		return iUserRepository.findByUsername(username);
	}

	@Override
	public UserEntity getUserById(Integer id) {
		
		return iUserRepository.getOne(id);
	}

}
