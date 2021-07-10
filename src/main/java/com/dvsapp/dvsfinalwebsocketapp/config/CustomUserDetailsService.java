package com.dvsapp.dvsfinalwebsocketapp.config;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.dvsapp.dvsfinalwebsocketapp.entity.UserEntity;
import com.dvsapp.dvsfinalwebsocketapp.repository.IUserRepository;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@NoArgsConstructor
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private IUserRepository iUserRepository;

	@Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    	Optional<UserEntity> userEntity = this.iUserRepository.findByUsername(username);	
    	log.info("userEntity is {}",userEntity.isPresent());
    	if (!userEntity.isPresent()) {
            throw new UsernameNotFoundException("Username: " + username + " not found");
        }
            return new CustomUserDetails(userEntity.get());    		
    	

    	

    }
}
