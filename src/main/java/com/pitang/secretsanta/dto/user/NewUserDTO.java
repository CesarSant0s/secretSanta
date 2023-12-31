package com.pitang.secretsanta.dto.user;

import com.pitang.secretsanta.model.User;

public record NewUserDTO(String name, String email) {

    public NewUserDTO(User user) {
        this(user.getName(), user.getEmail());
    }
}
