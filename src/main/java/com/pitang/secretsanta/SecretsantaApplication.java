package com.pitang.secretsanta;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import com.pitang.secretsanta.dto.user.UserToSaveDTO;
import com.pitang.secretsanta.service.UserService;

@SpringBootApplication
public class SecretsantaApplication {

	public static void main(String[] args) {
		SpringApplication.run(SecretsantaApplication.class, args);
	}

	@Autowired
	UserService userService;

	@EventListener(ApplicationReadyEvent.class)
	public void doSomethingAfterStartup() {
		userService.create(new UserToSaveDTO(
			"Cesar Santos", 
			"cesar.santos2042@gmail.com", 
			"12345678"));
	}
}
