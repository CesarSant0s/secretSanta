package com.pitang.secretsanta.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
    private LocalDate partyDate;
    private Double maxPriceGift;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Gift> gifts;

    @OneToMany
    private List<User> users;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SecretSanta> secretSantas;

    public void updateParty(Party party) {
        if (party.getName() != null) {
            this.setName(party.getName());
        }
        if (party.getPartyDate() != null) {
            this.setPartyDate(party.getPartyDate());
        }
        if (party.getMaxPriceGift() != null) {
            this.setMaxPriceGift(party.getMaxPriceGift());
        }

        validateAll();
    }

    public Party(PartyDTO party) {
        this.setName(party.name());
        this.setPartyDate(party.partyDate());
        this.setMaxPriceGift(party.maxPriceGift());
        
        validateAll();
    }

    public void addUser(User user) {

        this.users.forEach(u -> {
            if (u.getId().equals(user.getId())) {
                throw new IllegalArgumentException("User already exists");
            }
        });

        this.getUsers().add(user);
    }

    public void addGift(Gift gift) throws Exception {
        if (gift.getPrice() > this.getMaxPriceGift()) {
            throw new IllegalArgumentException("The gift value is higher than the maximum price");
        }
        this.getGifts().add(gift);
    }

    @Transient
    public void generateSecretSantas() {

        if (this.getUsers().size() < 3) {
            throw new IllegalArgumentException("It is necessary to have at least three participants");
        }

        List<User> participantList = new ArrayList<>(this.getUsers());

        List<SecretSanta> generatedSecretSantas = new ArrayList<>();

        Collections.shuffle(participantList);

        for (int i = 0; i < participantList.size(); i++) {
            if (i == participantList.size() - 1) {
                generatedSecretSantas.add(new SecretSanta(participantList.get(i), participantList.get(0)));
                break;
            }
            generatedSecretSantas.add(new SecretSanta(participantList.get(i), participantList.get(i + 1)));
        }

        this.setSecretSantas(generatedSecretSantas);

    }

    
    private void validatePartyDate() {
        if (partyDate.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("The party date cannot be before the current date");
        }
    }

    private void validateMaxPriceGift() {
        if (maxPriceGift < 0) {
            throw new IllegalArgumentException("The maximum price of the gift cannot be negative");
        }
    }

    private void validateName() {
        if (name.isEmpty()) {
            throw new IllegalArgumentException("The party name cannot be empty");
        }
    }

    private void validateAll() {
        validateName();
        validatePartyDate();
        validateMaxPriceGift();
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
