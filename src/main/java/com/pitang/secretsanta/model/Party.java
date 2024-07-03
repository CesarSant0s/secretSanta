package com.pitang.secretsanta.model;

import java.util.Date;
import java.util.List;

import com.pitang.secretsanta.dto.PartyDTO;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
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

    @ManyToMany(mappedBy = "parties")
    private List<User> users;

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
}
