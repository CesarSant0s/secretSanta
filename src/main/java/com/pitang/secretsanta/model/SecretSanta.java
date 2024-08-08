package com.pitang.secretsanta.model;

import jakarta.persistence.CascadeType;
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
@Entity(name = "secret_santa")
public class SecretSanta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "giver_id", referencedColumnName = "id") 
    private User giver;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "reciver_id", referencedColumnName = "id") 
    private User reciver;

    public SecretSanta(User giver, User reciver) {
        this.setGiver(giver);
        this.setReciver(reciver);
    }
    
}
