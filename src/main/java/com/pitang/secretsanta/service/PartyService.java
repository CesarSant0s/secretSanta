package com.pitang.secretsanta.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pitang.secretsanta.model.Party;
import com.pitang.secretsanta.repository.PartyRepository;

import jakarta.transaction.Transactional;

@Service
public class PartyService {
    
    @Autowired
    private PartyRepository partyRepository;

    public Party createParty(Party party) {
        return partyRepository.save(party);
    }

    public Party getParty(Long id) {
        return partyRepository.findById(id).get();
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

}
