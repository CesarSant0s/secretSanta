package com.pitang.secretsanta.dto.user;

import com.pitang.secretsanta.model.User;

public record UserDTO(Long id,String name, String email) {

    public UserDTO(User user) {
        this(user.getId(),
            user.getName(),
            user.getEmail());
    }

}
