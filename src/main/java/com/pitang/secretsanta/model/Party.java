package com.pitang.secretsanta.model;

import java.time.LocalDate;
import java.util.List;

import com.pitang.secretsanta.dto.PartyDTO;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
    private LocalDate partyDate;
    private Double maxPriceGift;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Gift> gifts;

    @OneToMany
    private List<User> users;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SecretSanta> secretSantas;

    public Party(String name, LocalDate partyDate, Double maxPriceGift) {
        this.setName(name);
        this.setPartyDate(partyDate);
        this.setMaxPriceGift(maxPriceGift);
    }

    public Party(PartyDTO party) {
        this.setName(party.name());
        this.setPartyDate(party.partyDate());
        this.setMaxPriceGift(party.maxPriceGift());
    }

    public void addUser(User user) {
        this.getUsers().add(user);
    }

    public void addGift(Gift gift) {
        this.getGifts().add(gift);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Party other = (Party) obj;
        if (id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!id.equals(other.id)) {
            return false;
        } else if (!name.equals(other.name)) {
            return false;
        } else if (!partyDate.equals(other.partyDate)) {
            return false;
        } else if (!maxPriceGift.equals(other.maxPriceGift)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = id != null ? id.hashCode() : 0;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((partyDate == null) ? 0 : partyDate.hashCode());
        result = prime * result + ((maxPriceGift == null) ? 0 : maxPriceGift.hashCode());
        return result;
    }
}
