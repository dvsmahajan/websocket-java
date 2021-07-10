package com.dvsapp.dvsfinalwebsocketapp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dvsapp.dvsfinalwebsocketapp.entity.UserEntity;

public interface IUserRepository extends JpaRepository<UserEntity, Integer>{

	UserEntity findByUsernameAndPassword(String username, String password);

	Optional<UserEntity> findByUsername(String username);

}
