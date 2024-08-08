package com.pitang.secretsanta.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pitang.secretsanta.dto.GiftDTO;
import com.pitang.secretsanta.dto.PartyDTO;
import com.pitang.secretsanta.model.Gift;
import com.pitang.secretsanta.model.Party;
import com.pitang.secretsanta.model.User;
import com.pitang.secretsanta.repository.PartyRepository;

import jakarta.transaction.Transactional;

@Service
public class PartyService {
    
    @Autowired
    private PartyRepository partyRepository;

    @Autowired UserService userService;

    public Party createParty(PartyDTO partyDTO) {
        return partyRepository.save(new Party(partyDTO));
    }

    public PartyDTO getParty(Long id) {
        return new PartyDTO(partyRepository.findById(id).orElseThrow(NullPointerException::new));
    }

    @Transactional
    public Party updateParty(Long id, Party party) {
        Party partyToUpdate = partyRepository.findById(id).get();
        partyToUpdate.updateParty(party);
        return partyRepository.save(partyToUpdate);
    }

    @Transactional
    public void deleteParty(Long id) {
        partyRepository.deleteById(id);
    }

    public void insertUser(Long id, Long userId) {
        
        Party party = partyRepository.findById(id).orElseThrow(NullPointerException::new);

        User user = userService.getUserById(userId);

        party.addUser(user);

        partyRepository.save(party);

    }

    public void insertUserGift(Long id, GiftDTO giftDTO) throws Exception {
        
        Party party = partyRepository.findById(id).orElseThrow(NullPointerException::new);
        User user = userService.getUserById(giftDTO.userId());
        party.addGift(new Gift(giftDTO, user));

        partyRepository.save(party);

    }

    public void generateSecretSantas(Long id) {

        Party party = partyRepository.findById(id).orElseThrow(NullPointerException::new);

        party.generateSecretSantas();

        partyRepository.save(party);

    }

}
