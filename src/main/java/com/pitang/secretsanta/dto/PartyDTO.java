package com.pitang.secretsanta.dto;

import java.time.LocalDate;
import java.util.List;

import com.pitang.secretsanta.dto.user.UserDTO;
import com.pitang.secretsanta.model.Party;

public record PartyDTO(String name, 
    LocalDate partyDate, 
    Double maxPriceGift,
    List<GiftDTO> gifts,
    List<UserDTO> users,
    List<SecretSantaDTO> secretSantas) {

    public PartyDTO(Party party) {
        this(party.getName(), 
        party.getPartyDate(), 
        party.getMaxPriceGift(),
        party.getGifts().stream().map(GiftDTO::new).toList(),
        party.getUsers().stream().map(UserDTO::new).toList(),
        party.getSecretSantas().stream().map(SecretSantaDTO::new).toList());
    }

}
