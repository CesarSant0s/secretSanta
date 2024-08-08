package com.pitang.secretsanta.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.pitang.secretsanta.dto.GiftDTO;
import com.pitang.secretsanta.dto.PartyDTO;
import com.pitang.secretsanta.model.Party;
import com.pitang.secretsanta.service.PartyService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/party")
public class PartyController {

    @Autowired
    private PartyService partyService;

    @PostMapping
    public ResponseEntity<Party> createParty(@RequestBody PartyDTO partyDTO) {
        Party createdParty = partyService.createParty(partyDTO);
        return ResponseEntity.ok(createdParty);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PartyDTO> getParty(@PathVariable Long id) {
        PartyDTO party = partyService.getParty(id);
        return ResponseEntity.ok(party);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Party> updateParty(@PathVariable Long id, @RequestBody Party party) {
        Party updatedParty = partyService.updateParty(id, party);
        return ResponseEntity.ok(updatedParty);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteParty(@PathVariable Long id) {
        partyService.deleteParty(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/user/{userId}")
    public ResponseEntity<Void> insertUser(@PathVariable Long id,
        @PathVariable Long userId) {

        partyService.insertUser(id, userId);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/{id}/gift")
    public ResponseEntity<Party> insertUserGift(
        @PathVariable Long id,
        @RequestBody GiftDTO giftDTO) throws Exception {

        partyService.insertUserGift(id, giftDTO);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/{id}/generate-scret-santa")
    public ResponseEntity<Void> postMethodName(@PathVariable Long id) {
        
        partyService.generateSecretSantas(id);
        
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
