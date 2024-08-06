package com.pitang.secretsanta.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.pitang.secretsanta.dto.user.NewUserDTO;
import com.pitang.secretsanta.dto.user.UserDTO;
import com.pitang.secretsanta.dto.user.UserToSaveDTO;
import com.pitang.secretsanta.model.User;
import com.pitang.secretsanta.repository.PartyRepository;
import com.pitang.secretsanta.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PartyRepository partyRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Transactional
    public NewUserDTO create(UserToSaveDTO user) {
        User newUser = new User();
        newUser.setEmail(user.email());
        newUser.setPassword(passwordEncoder.encode(user.password()));
        newUser.setName(user.name());
        
        var createdUser = userRepository.save(newUser);

        return new NewUserDTO(createdUser);
    }

    public List<UserDTO> list() {
        return userRepository.findAll().stream()
                .map(UserDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public UserDTO update(UserDTO updatedDataUser) {
        var user = userRepository.getReferenceById(updatedDataUser.id());

        user.updateData(updatedDataUser);

        userRepository.save(user);

        return new UserDTO(user);
    }

    public User delete(long id) {
        var user = userRepository.findById(id).get();
        userRepository.delete(user);
        return null;
    }


    public UserDTO getUserDTOById(Long id) {
        return new UserDTO(userRepository.findById(id).orElseThrow(NullPointerException::new));
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(NullPointerException::new);
    }

}
