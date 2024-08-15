package com.pitang.secretsanta.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pitang.secretsanta.dto.GiftDTO;
import com.pitang.secretsanta.dto.PartyDTO;
import com.pitang.secretsanta.model.Party;
import com.pitang.secretsanta.service.GiftPriceExeception;
import com.pitang.secretsanta.service.ParticipantException;
import com.pitang.secretsanta.service.PartyService;


@RestController
@RequestMapping("/party")
public class PartyController {

    @Autowired
    private PartyService partyService;

    @PostMapping
    public ResponseEntity<Party> createParty(@RequestBody PartyDTO partyDTO) {
        Party createdParty = partyService.createParty(partyDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdParty);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PartyDTO> getParty(@PathVariable Long id) {
        PartyDTO party = partyService.getParty(id);
        return ResponseEntity.ok(party);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PartyDTO> updateParty(@PathVariable Long id, @RequestBody PartyDTO partyDTO) {
        partyService.updateParty(id, partyDTO);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteParty(@PathVariable Long id) {
        partyService.deleteParty(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/user/{userId}")
    public ResponseEntity<Void> insertUser(@PathVariable Long id,
        @PathVariable Long userId) throws ParticipantException {

        partyService.insertUser(id, userId);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/{id}/gift")
    public ResponseEntity<Party> insertUserGift(
        @PathVariable Long id,
        @RequestBody GiftDTO giftDTO) throws GiftPriceExeception {

        partyService.insertUserGift(id, giftDTO);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/{id}/generate-scret-santa")
    public ResponseEntity<Void> generateSecretSantas(@PathVariable Long id) {
        
        partyService.generateSecretSantas(id);
        
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
