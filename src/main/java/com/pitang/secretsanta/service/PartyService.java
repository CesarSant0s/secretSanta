package com.pitang.secretsanta.service;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pitang.secretsanta.dto.GiftDTO;
import com.pitang.secretsanta.dto.PartyDTO;
import com.pitang.secretsanta.model.Gift;
import com.pitang.secretsanta.model.Party;
import com.pitang.secretsanta.model.SecretSanta;
import com.pitang.secretsanta.model.User;
import com.pitang.secretsanta.repository.PartyRepository;

import jakarta.persistence.EntityNotFoundException;


@Service
public class PartyService {
    
    @Autowired
    private PartyRepository partyRepository;

    @Autowired UserService userService;

    public Party createParty(PartyDTO partyDTO) {

        var party = new Party(partyDTO);

        this.validateParty(party);

        return partyRepository.save(party);
    }

    public PartyDTO getParty(Long id) {
        return new PartyDTO(partyRepository.findById(id).orElseThrow(NullPointerException::new));
    }

    public void updateParty(Long id, PartyDTO partyDTO) {
        Party party = partyRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        
        party.setName(partyDTO.name());
        party.setPartyDate(partyDTO.partyDate());
        party.setMaxPriceGift(partyDTO.maxPriceGift());

        this.validateParty(party);

        partyRepository.save(party);
    }

    public void deleteParty(Long id) {
        partyRepository.deleteById(id);
    }

    public void insertUser(Long partyId, Long userId) throws ParticipantException {
        
        Party party = partyRepository.findById(partyId).orElseThrow(NullPointerException::new);

        User user = userService.getUserById(userId);

        if (party.getUsers().contains(user)) {
            throw new ParticipantException("User already in party");
        }

        party.addUser(user);

        partyRepository.save(party);

    }

    public void insertUserGift(Long id, GiftDTO giftDTO) throws GiftPriceExeception {
        
        var party = partyRepository.findById(id).orElseThrow(NullPointerException::new);
        
        var user = userService.getUserById(giftDTO.userId());

        var gift = new Gift(giftDTO, user);

        party.addGift(gift);

        if (gift.getPrice() > party.getMaxPriceGift()) {
            throw new GiftPriceExeception("Gift price higher than party max price");
        }

        partyRepository.save(party);

    }

    public void generateSecretSantas(Long id) {

        Party party = partyRepository.findById(id).orElseThrow(NullPointerException::new);

        if (party.getUsers().size() < 3) {
            throw new IllegalArgumentException("Party must have at least 3 participants");
        }

        List<User> participantList = new ArrayList<>(party.getUsers());
        
        List<SecretSanta> generatedSecretSantas = new ArrayList<>();

        Collections.shuffle(participantList);

        for (int i = 0; i < participantList.size()-1; i++) {
            generatedSecretSantas.add(new SecretSanta(participantList.get(i), participantList.get(i + 1)));
        }
        generatedSecretSantas.add(new SecretSanta(participantList.get(participantList.size() - 1), participantList.get(0)));
        
        party.setSecretSantas(generatedSecretSantas);

        partyRepository.save(party);

    }

    public void validateParty(Party party) {
        if (party.getMaxPriceGift() < 1.0) {
            throw new IllegalArgumentException("Max price gift must be greater than 0");
        }

        if (party.getName().isBlank()) {
            throw new IllegalArgumentException("Party name must not be blank");
        }

        if (party.getPartyDate().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Party date must be in the future");
        }
    }

}
