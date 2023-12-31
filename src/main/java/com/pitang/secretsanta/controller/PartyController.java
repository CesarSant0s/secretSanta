package com.pitang.secretsanta.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.pitang.secretsanta.dto.PartyDTO;
import com.pitang.secretsanta.model.Party;
import com.pitang.secretsanta.service.PartyService;
import org.springframework.http.ResponseEntity;

@Controller
@RequestMapping("/parties")
public class PartyController {

    @Autowired
    private PartyService partyService;

    @PostMapping
    @ResponseBody
    public ResponseEntity<Party> createParty(@RequestBody PartyDTO party) {
        Party createdParty = partyService.createParty(new Party(party));
        return ResponseEntity.ok(createdParty);
    }

    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<Party> getParty(@PathVariable Long id) {
        Party party = partyService.getParty(id);
        return ResponseEntity.ok(party);
    }

    @PutMapping("/{id}")
    @ResponseBody
    public ResponseEntity<Party> updateParty(@PathVariable Long id, @RequestBody Party party) {
        Party updatedParty = partyService.updateParty(id, party);
        return ResponseEntity.ok(updatedParty);
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public ResponseEntity<Void> deleteParty(@PathVariable Long id) {
        partyService.deleteParty(id);
        return ResponseEntity.noContent().build();
    }
}
