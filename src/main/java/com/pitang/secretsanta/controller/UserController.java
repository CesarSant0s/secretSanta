package com.pitang.secretsanta.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pitang.secretsanta.dto.RegisterGiftsDTO;
import com.pitang.secretsanta.dto.user.NewUserDTO;
import com.pitang.secretsanta.dto.user.UserDTO;
import com.pitang.secretsanta.dto.user.UserToSaveDTO;
import com.pitang.secretsanta.service.UserService;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    UserService service;

    /**
     * Create a new user
     * @param newUser
     * @return
     */
    @PostMapping
    public ResponseEntity<NewUserDTO> create(@RequestBody UserToSaveDTO newUser) {
        var createdUser = service.create(newUser);
        return ResponseEntity.ok(createdUser);
    }


    @GetMapping
    public ResponseEntity<List<UserDTO>> list() {
        var users = service.list();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> get(@PathVariable Long id) {
        var user = service.getUserById(id);
        return ResponseEntity.ok(user);
    }

    @PutMapping
    public ResponseEntity<UserDTO> update(@RequestBody UserDTO updatedUser) {
        var user = service.update(updatedUser);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/subscribe/{userId}/parties/{partyId}")
    public ResponseEntity<Void> subscribeParty(@PathVariable Long partyId, @PathVariable Long userId) {
        service.subscribeParty(userId, partyId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/gifts")
    public ResponseEntity<Void> registerGifts(@RequestBody RegisterGiftsDTO gifts){

        service.registerGifts(gifts);
        return ResponseEntity.ok().build();
    }

}
