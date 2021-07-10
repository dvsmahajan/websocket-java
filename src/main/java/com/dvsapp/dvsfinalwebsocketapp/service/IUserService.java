package com.dvsapp.dvsfinalwebsocketapp.service;

import java.util.Optional;

import com.dvsapp.dvsfinalwebsocketapp.dto.LoginUserDTO;
import com.dvsapp.dvsfinalwebsocketapp.entity.UserEntity;

public interface IUserService {

	UserEntity saveUser(UserEntity user);

	UserEntity findUserByUsernameAndPassword(LoginUserDTO user);

	UserEntity loadUserByUsername(String username);

	Optional<UserEntity> getByUsername(String username);

	UserEntity getUserById(Integer id);
 
}
