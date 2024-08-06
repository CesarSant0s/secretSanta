package com.pitang.secretsanta.model;

import java.util.Date;
import java.util.List;
import java.util.Random;

import org.hibernate.mapping.Collection;

import java.util.ArrayList;
import com.pitang.secretsanta.dto.GiftDTO;
import com.pitang.secretsanta.dto.PartyDTO;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "party")
public class Party {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Date partyDate;
    private Double maxPriceGift;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Gift> gifts;

    @OneToMany
    private List<User> users;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SecretSanta> secretSantas;

    public void updateParty(Party party) {
        this.setName(party.getName());
        this.setPartyDate(party.getPartyDate());
        this.setMaxPriceGift(party.getMaxPriceGift());
    }

    public void subscribe(Long userId) {
        User user = new User();
        user.setId(userId);
        this.getUsers().add(user);
    }

    public Party(PartyDTO party) {
        this.setName(party.name());
        this.setPartyDate(party.partyDate());
        this.setMaxPriceGift(party.maxPriceGift());
    }

    public void addUser(User user) {
        this.getUsers().add(user);
    }

    public void addUserGift(User user, GiftDTO giftDTO) {

        Gift gift = new Gift(user, giftDTO.name(), giftDTO.price());

        this.getGifts().add(gift);
    }

    private Random random = new Random();

    @Transient
    public void generateSecretSantas() {

        List<User> givers = new ArrayList<>(this.getUsers());
        
        List<User> recivers = new ArrayList<>(givers);

        givers.forEach(giver -> {
            recivers.remove(giver);
            
            User reciver = recivers.get(random.nextInt(recivers.size()));
            
            addSecretSanta(giver, reciver);
    
            recivers.remove(reciver);
            recivers.add(giver);
        });

    }

    private void addSecretSanta(User santaUser, User secretUser) {
        SecretSanta secretSanta = new SecretSanta(santaUser, secretUser);
        this.getSecretSantas().add(secretSanta);
    }


}
