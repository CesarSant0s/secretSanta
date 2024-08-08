package com.pitang.secretsanta.model;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.pitang.secretsanta.dto.user.UserDTO;
import com.pitang.secretsanta.dto.user.UserToSaveDTO;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String password;

    public User(UserToSaveDTO user) {
        this.setName(user.name());
        this.setEmail(user.email());
        this.setPassword(user.password());
    }

    
    public User(UserDTO updatedUser) {
        this.setEmail(updatedUser.email());
        this.setName(updatedUser.name());
    }
    
    public void updateUser(User user) {
        this.setEmail(user.getEmail());
        this.setName(user.getName());
    }
    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public String getPassword() {
        return this.password;
    }


    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void updateData(UserDTO updatedDataUser) {

        this.setEmail(updatedDataUser.email());
        this.setName(updatedDataUser.name());
    }

}
