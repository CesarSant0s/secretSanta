package com.pitang.secretsanta.model;

import com.pitang.secretsanta.dto.GiftDTO;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "gift")
public class Gift {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;
    private Double price;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Gift(User user, String name, Double price) {
        this.setName(name);
        this.setPrice(price);
        this.setUser(user);
    }

    public Gift(GiftDTO giftDTO) {
        this.setName(giftDTO.name());
        this.setPrice(giftDTO.price());
    }

}
