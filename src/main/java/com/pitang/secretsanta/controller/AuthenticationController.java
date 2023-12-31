package com.pitang.secretsanta.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.pitang.secretsanta.dto.DataTokenJWTDTO;
import com.pitang.secretsanta.dto.LoginDTO;
import com.pitang.secretsanta.model.User;
import com.pitang.secretsanta.security.TokenService;

@Controller
public class AuthenticationController {

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<DataTokenJWTDTO> login(@RequestBody LoginDTO login) {
        
        var authenticationToken = new UsernamePasswordAuthenticationToken(login.email(), login.password());
        var authentication = manager.authenticate(authenticationToken);
        var tokenJWT = tokenService.generateToken((User) authentication.getPrincipal());

        return ResponseEntity.ok(new DataTokenJWTDTO(tokenJWT));
    }

}
