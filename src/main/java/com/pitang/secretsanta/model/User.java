package com.pitang.secretsanta.model;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.pitang.secretsanta.dto.user.UserDTO;
import com.pitang.secretsanta.dto.user.UserToSaveDTO;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
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

    @OneToMany(
        mappedBy = "user", 
        cascade = CascadeType.ALL)
    private List<Gift> gifts;

    @ManyToMany
    @JoinTable(name = "party_user", 
        joinColumns = @jakarta.persistence.JoinColumn(name = "user_id"), 
        inverseJoinColumns = @jakarta.persistence.JoinColumn(name = "party_id"))
    private List<Party> parties;

    public User(UserToSaveDTO user) {
        this.setName(user.name());
        this.setEmail(user.email());
        this.setPassword(user.password());
    }

    public void updateUser(User user) {
        this.setEmail(user.getEmail());
        this.setName(user.getName());
    }

    public User(UserDTO updatedUser) {
        this.setEmail(updatedUser.email());
        this.setName(updatedUser.name());
    }

    public void subscribeParty(Long partyId) {
        Party party = new Party();
        party.setId(partyId);
        this.getParties().add(party);
    }

    public void registerGifts(Party party, List<Gift> giftsList) {
        giftsList.stream().forEach(g -> {
            g.setParty(party);
            g.setUser(this);
        });
        gifts.addAll(giftsList);
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

}
