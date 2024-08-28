package com.pitang.secretsanta.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.pitang.secretsanta.dto.GiftDTO;
import com.pitang.secretsanta.dto.PartyDTO;
import com.pitang.secretsanta.model.Party;
import com.pitang.secretsanta.model.User;
import com.pitang.secretsanta.repository.PartyRepository;

@ExtendWith(MockitoExtension.class)
class PartyServiceTest {

    @InjectMocks
    private PartyService partyService;

    @Mock
    private PartyRepository partyRepository;

    @Mock
    private UserService userService;

    @Mock
    private User user1;

    @Mock
    private User user2;

    @Mock
    private PartyDTO partyDTO;

    @Mock
    private Party party;

    private GiftDTO giftDTO;

    @Test
    void shouldThrowAnExceptionWhenTheMaxPriceIsLowerThanZero() {
        // arrange
        this.party = new Party();
        this.party.setMaxPriceGift(0.0);
        this.party.setPartyDate(LocalDate.now().plusDays(1));
        this.party.setName("Festa de ano novo");

        // act + assert
        assertThrows(IllegalArgumentException.class, () -> partyService.validateParty(this.party));
    }

    @Test
    void shouldThrowAnExceptionWhenTheDateIsBeforeToday() {
        // arrange
        this.party = new Party();
        this.party.setMaxPriceGift(100.0);
        this.party.setPartyDate(LocalDate.now().minusDays(1));
        this.party.setName("Festa de ano novo");

        // act + assert
        assertThrows(IllegalArgumentException.class, () -> partyService.validateParty(this.party));
    }

    @Test
    void shouldThrowAnExceptionWhenThePartyNameIsEmpty() {
        // arrange
        this.party = new Party();
        this.party.setMaxPriceGift(100.0);
        this.party.setPartyDate(LocalDate.now().plusDays(1));
        this.party.setName("");

        // act + assert
        assertThrows(IllegalArgumentException.class, () -> partyService.validateParty(this.party));
    }

    @Test
    void shouldCallSaveParty() {
        // arrange
        this.partyDTO = new PartyDTO("Festa de ano novo", LocalDate.now().plusDays(1), 100.0);

        // act
        partyService.createParty(partyDTO);

        // assert
        then(partyRepository).should().save(new Party(partyDTO));
    }

    @Test
    void shouldCallfindByIdParty() {
        // arrange
        given(partyRepository.findById(1L)).willReturn(Optional.of(party));

        // act
        partyService.getParty(1L);

        // assert
        then(partyRepository).should().findById(1L);
    }

    @Test
    void shouldCallDeleteById() {
        // act 
        partyService.deleteParty(1L);

        // assert
        then(partyRepository).should().deleteById(1L);
    }

    @Test
    void shouldCallSavePartyAfterUpdate() {
        // arrange
        var oldParty = spy(new Party("Festa de natal", LocalDate.now().plusDays(5), 50.0));
        this.partyDTO = new PartyDTO("Festa de ano novo", LocalDate.now().plusDays(1), 100.0);
        given(partyRepository.findById(1L)).willReturn(Optional.of(oldParty));

        // act
        partyService.updateParty(1L,partyDTO);

        // assert
        then(partyRepository).should().save(new Party(partyDTO));
    }

    @Test
    void shouldThrowAnErrorWhenThePriceIsHigherThanPartyPrice() {
        // arrange
        this.giftDTO = new GiftDTO("name", 100.0, null, 1L);
        given(partyRepository.findById(giftDTO.userId())).willReturn(Optional.of(party));
        given(userService.getUserById(giftDTO.userId())).willReturn(user1);
        given(party.getMaxPriceGift()).willReturn(50.0);

        assertThrows(GiftPriceExeception.class, ()-> partyService.insertUserGift(1L, giftDTO));
    }

    @Test
    void shouldNotThrowAnErrorWhenThePriceIsLowerThanPartyPrice() {
        // arrange
        this.giftDTO = new GiftDTO("name", 100.0, null, 1L);
        given(partyRepository.findById(giftDTO.userId())).willReturn(Optional.of(party));
        given(userService.getUserById(giftDTO.userId())).willReturn(user1);
        given(party.getMaxPriceGift()).willReturn(100.0);

        // act
        assertDoesNotThrow(()-> partyService.insertUserGift(1L, giftDTO));
    }

    @Test
    void shouldThorwAnErrorWhenTheUserAlreadyIsInTheParty() {
        // arrange
        var users = new LinkedHashSet<User>();
        users.add(user1);
        given(partyRepository.findById(1L)).willReturn(Optional.of(party));
        given(userService.getUserById(1L)).willReturn(user1);
        given(party.getUsers()).willReturn(users);
        
        // act + assert 
        assertThrows(ParticipantException.class, ()-> partyService.insertUser(1L, 1L));
    }

    @Test
    void shouldNotThorwAnErrorWhenTheUserIsNotInTheParty() {
        // arrange
        given(partyRepository.findById(1L)).willReturn(Optional.of(party));
        given(userService.getUserById(1L)).willReturn(user1);
        var users = new LinkedHashSet<User>();
        users.add(user2);
        given(party.getUsers()).willReturn(users);
        
        // act + assert
        assertDoesNotThrow( ()-> partyService.insertUser(1L, 1L));
    }

    @Test
    void testGenerateSecretSantas1() {
        // arrange
        Party partySpy = spy(Party.class);
        var users = Mockito.spy(new LinkedHashSet<User>());
        for (int i = 0; i < 5; i++) {
            users.add(mock(User.class));
        }
        partySpy.setUsers(users);

        given(partyRepository.findById(1L)).willReturn(Optional.of(partySpy));

        // act
        partyService.generateSecretSantas(1L);

        // assert 
        assertEquals(5, partySpy.getSecretSantas().stream().map(s -> s.getReciver()).toList().size());

    }

    @Test
    void testGenerateSecretSantas2() {
        // arrange
        var partySpy = spy(Party.class);
        var users = spy(new LinkedHashSet<User>());
        for (int i = 0; i < 5; i++) {
            users.add(new User(2L + i, "nametest"+i, "email"+i, "test"+i));
        }

        partySpy.setUsers(users);

        given(partyRepository.findById(1L)).willReturn(Optional.of(partySpy));

        // act
        partyService.generateSecretSantas(1L);

        // assert 
        var recivers = partySpy.getSecretSantas().stream().map(secretSanta -> secretSanta.getReciver().getId()).toList();
        var givers = partySpy.getSecretSantas().stream().map(secretSanta -> secretSanta.getGiver().getId()).toList();

        assertEquals(recivers.size(),new HashSet<>(recivers).size());
        assertEquals( givers.size(), new HashSet<>(givers).size());
    }

    @Test
    void testGenerateSecretSantas3() {

        // arrange
        Party partySpy = spy(Party.class);
        var users = Mockito.spy(new LinkedHashSet<User>());
        for (int i = 0; i < 5; i++) {
            users.add(mock(User.class));
        }
        partySpy.setUsers(users);

        given(partyRepository.findById(1L)).willReturn(Optional.of(partySpy));

        // act
        partyService.generateSecretSantas(1L);

        // act + assert 
        assertDoesNotThrow(() -> partyService.generateSecretSantas(1L));
        assertEquals(5l, partySpy.getSecretSantas().size());
    }

    @Test
    void testGenerateSecretSantas4() {
         // arrange
         Party partySpy = spy(Party.class);
         var users = Mockito.spy(new LinkedHashSet<User>());
         for (int i = 0; i < 2; i++) {
             users.add(mock(User.class));
         }
         partySpy.setUsers(users);
         given(partyRepository.findById(1L)).willReturn(Optional.of(partySpy));
 
        // act + assert 
        assertThrows(IllegalArgumentException.class,() -> partyService.generateSecretSantas(1L));
    }

    @Test
    void testGenerateSecretSantas5() {
        // arrange
        Party partySpy = spy(Party.class);
        var users = Mockito.spy(new LinkedHashSet<User>());
        for (int i = 0; i < 3; i++) {
            users.add(mock(User.class));
        }
        partySpy.setUsers(users);

        given(partyRepository.findById(1L)).willReturn(Optional.of(partySpy));

        // act
        for (int i = 0; i < 10; i++) {
            partyService.generateSecretSantas(1L);
        }
        
        // assert 
        assertEquals(3, partySpy.getSecretSantas().size());
    }

}
