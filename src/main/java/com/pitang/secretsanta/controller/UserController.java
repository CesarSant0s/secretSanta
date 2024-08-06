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

import com.pitang.secretsanta.dto.user.NewUserDTO;
import com.pitang.secretsanta.dto.user.UserDTO;
import com.pitang.secretsanta.dto.user.UserToSaveDTO;
import com.pitang.secretsanta.service.UserService;

@RestController
@RequestMapping("/user")
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


    /**
     * List all users
     * @return
     */
    @GetMapping
    public ResponseEntity<List<UserDTO>> list() {
        var users = service.list();
        return ResponseEntity.ok(users);
    }

    /**
     * Get a user by id
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> get(@PathVariable Long id) {
        var user = service.getUserDTOById(id);
        return ResponseEntity.ok(user);
    }

    /**
     * Update a user
     * @param updatedUser
     * @return
     */
    @PutMapping
    public ResponseEntity<UserDTO> update(@RequestBody UserDTO updatedUser) {
        var user = service.update(updatedUser);
        return ResponseEntity.ok(user);
    }

    /**
     * Delete a user
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}
