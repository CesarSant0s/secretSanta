package com.pitang.secretsanta.dto;

import com.pitang.secretsanta.dto.user.UserDTO;
import com.pitang.secretsanta.model.SecretSanta;

public record SecretSantaDTO(UserDTO giver, UserDTO reciver) {
        
        public SecretSantaDTO(SecretSanta secretSanta) {
            this(
                new UserDTO(secretSanta.getGiver()),
                new UserDTO(secretSanta.getReciver())
            );
        }
}
