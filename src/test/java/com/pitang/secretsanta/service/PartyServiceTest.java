package com.pitang.secretsanta.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.pitang.secretsanta.dto.PartyDTO;
import com.pitang.secretsanta.model.Party;
import com.pitang.secretsanta.model.User;
import com.pitang.secretsanta.repository.PartyRepository;
import static org.mockito.BDDMockito.then;

import java.time.LocalDate;
import java.util.ArrayList;


@ExtendWith(MockitoExtension.class)
public class PartyServiceTest {

    @InjectMocks
    private PartyService partyService;

    @Mock
    private PartyRepository partyRepository;

    @Mock
    private UserService userService;

    @Mock
    private User user;

    @Mock
    private PartyDTO partyDTO;
    
    @Test
    @DisplayName("Test create party")
    void testCreateParty() {
        // arrange
        this.partyDTO = new PartyDTO("Festa de ano novo", LocalDate.now().plusDays(1), 100.0, null, null, null);

        // act
        partyService.createParty(partyDTO);

        // assert
        then(partyRepository).should().save(new Party(partyDTO));
    }

    @Test
    void testDeleteParty() {
        // act 
        partyService.deleteParty(1L);

        // assert
        then(partyRepository).should().deleteById(1L);
    }

    @Test
    void testGenerateSecretSantas() {
        
    }

    @Test
    void testGetParty() {

    }

    // @Test
    // void testInsertUser() {

    // }

    // @Test
    // void testInsertUserGift() {

    // }

    // @Test
    // void testUpdateParty() {

    // }
}
