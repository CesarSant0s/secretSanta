package com.pitang.secretsanta.dto;

import java.util.Date;

import com.pitang.secretsanta.model.Party;

public record PartyDTO(String name, Date partyDate, Double maxPriceGift) {

    public PartyDTO(Party party) {
        this(party.getName(), party.getPartyDate(), party.getMaxPriceGift());
    }

}
