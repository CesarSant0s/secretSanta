package com.pitang.secretsanta.dto.user;

import java.util.List;

import com.pitang.secretsanta.dto.GiftDTO;
import com.pitang.secretsanta.dto.PartyDTO;
import com.pitang.secretsanta.model.User;

public record UserDTO(String name, String email, List<PartyDTO> parties, List<GiftDTO> gifts) {

    public UserDTO(User user) {
        this(user.getName(),
                user.getEmail(),
                user.getParties().stream().map(PartyDTO::new).toList(),
                user.getGifts().stream().map(GiftDTO::new).toList());
    }

}
