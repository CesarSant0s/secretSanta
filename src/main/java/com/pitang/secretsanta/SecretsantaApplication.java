package com.pitang.secretsanta;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import java.util.Date;

import com.pitang.secretsanta.dto.PartyDTO;
import com.pitang.secretsanta.dto.user.UserToSaveDTO;
import com.pitang.secretsanta.model.Party;
import com.pitang.secretsanta.service.PartyService;
import com.pitang.secretsanta.service.UserService;

@SpringBootApplication
public class SecretsantaApplication {

	public static void main(String[] args) {
		SpringApplication.run(SecretsantaApplication.class, args);
	}

	@Autowired
	UserService userService;

	@Autowired
	PartyService partyService;

	@EventListener(ApplicationReadyEvent.class)
	public void createUser() {
		userService.create(new UserToSaveDTO(
			"Cesar Santos", 
			"cesar.santos2042@gmail.com", 
			"12345678"));
		userService.create(new UserToSaveDTO(
			"Vilma lima", 
			"vilma.lima@gmail.com",
			"12345678"));
		userService.create(new UserToSaveDTO(
			"Millena Ferreira", 
			"millena.ferreira@gmail.com",
			"12345678"));
		userService.create(new UserToSaveDTO(
			"Carlos Eduardo", 
			"carlos.eduardo@gmail.com",
			"12345678"));
		userService.create(new UserToSaveDTO(
			"Antonio Cesar", 
			"antonio.cesar@gmail.com",
			"12345678"));
					
		partyService.createParty(new Party(new 
			PartyDTO("Confraternização de fim de ano",new Date(), 100.0,
			null, null, null)));
	
	}


}
