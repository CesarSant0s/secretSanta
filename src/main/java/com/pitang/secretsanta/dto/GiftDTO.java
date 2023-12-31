package com.pitang.secretsanta.dto;

import com.pitang.secretsanta.model.Gift;

public record GiftDTO(String name, Double price, String partyName) {

    public GiftDTO(Gift gift){
        this(gift.getName(), gift.getPrice(), gift.getParty().getName());
    }
}  
