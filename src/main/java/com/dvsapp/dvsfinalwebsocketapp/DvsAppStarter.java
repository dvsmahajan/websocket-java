package com.dvsapp.dvsfinalwebsocketapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;

@SpringBootApplication
@CrossOrigin
public class DvsAppStarter {
	public static void main(String[] args) {
		SpringApplication.run(DvsAppStarter.class, args);
	}
}
