package com.pitang.secretsanta.model;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


class PartyTest {

    @Test
    @DisplayName("Verificar se o update da festa está correto")
    void testUpdateParty() {
        // arrange
        Party party = new Party();
        party.setName("Festa de Natal");
        party.setPartyDate(LocalDate.now().plusDays(1));
        party.setMaxPriceGift(100.0);
        Party partyUpdate = new Party();
        partyUpdate.setName("Festa de Ano Novo");
        partyUpdate.setPartyDate(LocalDate.now().plusDays(2));
        partyUpdate.setMaxPriceGift(200.0);

        // act
        party.updateParty(partyUpdate);

        // assert
        assertEquals("Festa de Ano Novo", party.getName());
        assertEquals(LocalDate.now().plusDays(2), party.getPartyDate());
        assertEquals(200.0, party.getMaxPriceGift());
    }

    @Test
    @DisplayName("Retorna exceção se o nome da festa é vazio")
    void testUpdateParty1(){
        // arrange
        Party party = new Party();
        party.setName("Festa de Natal");
        Party partyUpdate = new Party();
        partyUpdate.setName("");

        // act + assert
        assertThrows(IllegalArgumentException.class, () -> party.updateParty(partyUpdate));
    }

    @Test
    @DisplayName("Retorna exceção se a data da festa é posterior a data atual")
    void testUpdateParty2(){
        // arrange
        Party party = new Party();
        party.setPartyDate(LocalDate.now().plusDays(1));
        Party partyUpdate = new Party();
        partyUpdate.setPartyDate(LocalDate.now().minusDays(2));

        // act + assert
        assertThrows(IllegalArgumentException.class, () -> party.updateParty(partyUpdate));
    }

    @Test
    @DisplayName("Verificar se o valor máximo do presente é maior que zero")
    void testUpdateParty3(){
        // arrange
        Party party = new Party();
        party.setMaxPriceGift(100.0);
        Party partyUpdate = new Party();
        partyUpdate.setMaxPriceGift(-100.0);

        // act + assert
        assertThrows(IllegalArgumentException.class, () -> party.updateParty(partyUpdate));
    }

    @Test
    @DisplayName("Não deve mudar  infomrações nulas")
    void testUpdateParty4(){
        // arrange
        Party party = new Party();
        party.setName("Festa de Natal");
        party.setPartyDate(LocalDate.now().plusDays(1));
        party.setMaxPriceGift(100.0);
        Party partyUpdate = new Party();

        // act
        party.updateParty(partyUpdate);

        // assert
        assertEquals("Festa de Natal", party.getName());
        assertEquals(LocalDate.now().plusDays(1), party.getPartyDate());
        assertEquals(100.0, party.getMaxPriceGift());
    }

    @Test
    @DisplayName("Valor do presente deverá ser menor ou igual ao valor máximo")
    void testAddGift1() {
        // arrange
        Party party = new Party();
        party.setMaxPriceGift(100.0);
        party.setGifts(new ArrayList<>());
        Gift gift = new Gift();
        gift.setPrice(50.0);

        // act + assert 
        assertDoesNotThrow(() -> party.addGift(gift));
    }

    @Test
    @DisplayName("Valor do presente maior que o valor máximo deve lançar exceção")
    void testAddGiftException() {
         // arrange
         Party party = new Party();
         party.setMaxPriceGift(100.0);
         party.setGifts(new ArrayList<>());
         Gift gift = new Gift();
         gift.setPrice(150.0);
        // act + assert
        assertThrows(IllegalArgumentException.class, () -> party.addGift(gift));
    }


    @Test
    @DisplayName("Valor do presente igual ao valor máximo deve ser aceito")
    void testAddGift2() {
        // arrange
        Party party = new Party();
        party.setMaxPriceGift(100.0);
        party.setGifts(new ArrayList<>());
        Gift gift = new Gift();
        gift.setPrice(100.0);

        // act + assert 
        assertDoesNotThrow(() -> party.addGift(gift));
    }


    @Test
    @DisplayName("Cadastrar um participante que não está na lista de participantes")
    void testAddUser1() {
        // arrange
        Party party = new Party();
        party.setUsers(new ArrayList<>());
        User user = new User();
        user.setId(1L);

        // act + assert 
        assertDoesNotThrow(() -> party.addUser(user));
    }

    @Test
    @DisplayName("Cadastrar um participante que já está na lista de participantes")
    void testAddUser2() {
        // arrange
        Party party = new Party();
        party.setUsers(new ArrayList<>());
        User user = new User();
        user.setId(1L);
        party.addUser(user);

        // act + assert 
        assertThrows(IllegalArgumentException.class, () -> party.addUser(user));
    }

    @Test
    @DisplayName("Todos devem ter um amigo secreto")
    void testGenerateSecretSantas1() {
        // arrange
        Party party = new Party();
        party.setUsers(new ArrayList<>());
        party.setSecretSantas(new ArrayList<>());
        User user;
        for (int i = 0; i < 5; i++) {
            user = new User();
            user.setId((long) i);
            party.addUser(user);
        }

        // act
        party.generateSecretSantas();

        // assert 
        var recivers = party.getSecretSantas().stream().map(secretSanta -> secretSanta.getReciver()).toList();

        assertEquals(5, recivers.size());

    }

    @Test
    @DisplayName("Verifica se existem amigos scretos duplicados")
    void testGenerateSecretSantas2() {
        // arrange
        Party party = new Party();
        party.setUsers(new ArrayList<>());
        party.setSecretSantas(new ArrayList<>());
        User user;
        for (int i = 0; i < 5; i++) {
            user = new User();
            user.setId((long) i);
            party.addUser(user);
        }

        // act
        party.generateSecretSantas();

        // assert 
        var recivers = party.getSecretSantas().stream().map(secretSanta -> secretSanta.getReciver().getId()).toList();
        var givers = party.getSecretSantas().stream().map(secretSanta -> secretSanta.getGiver().getId()).toList();

        var duplicateRecivers = listDuplicate(recivers);
        var duplicateGivers = listDuplicate(givers);

        for (int i = 0; i < 5; i++) {
            assertEquals(false, duplicateRecivers.contains((long) i));
            assertEquals( false, duplicateGivers.contains((long) i));
        }

    }

    @Test
    @DisplayName("A festa tem que ter ao menos tres participantes")
    void testGenerateSecretSantas3() {
        // arrange
        Party party = new Party();
        party.setUsers(new ArrayList<>());
        party.setSecretSantas(new ArrayList<>());
        User user;
        for (int i = 0; i < 3; i++) {
            user = new User();
            user.setId((long) i);
            party.addUser(user);
        }

        // act + assert 
        assertDoesNotThrow(() -> party.generateSecretSantas());
    }

    @Test
    @DisplayName("A festa tem que ter ao menos tres participantes")
    void testGenerateSecretSantas4() {
        // arrange
        Party party = new Party();
        party.setUsers(new ArrayList<>());
        party.setSecretSantas(new ArrayList<>());
        User user;
        for (int i = 0; i < 2; i++) {
            user = new User();
            user.setId((long) i);
            party.addUser(user);
        }

        // act + assert 
        assertThrows(IllegalArgumentException.class,() -> party.generateSecretSantas());
    }

    @Test
    @DisplayName("Após rodar várias vezes o tamanho do array de secret santas deve ser igual ao de usuários")
    void testGenerateSecretSantas5() {
        // arrange
        Party party = new Party();
        party.setUsers(new ArrayList<>());
        party.setSecretSantas(new ArrayList<>());
        User user;
        for (int i = 0; i < 3; i++) {
            user = new User();
            user.setId((long) i);
            party.addUser(user);
        }

        // act
        for (int i = 0; i < 10; i++) {
            party.generateSecretSantas();
        }
        
        // assert 
        assertEquals(3, party.getSecretSantas().size());
    }

    List<Long> listDuplicate(List<Long> list) {
        Set<Long> elements = new HashSet<Long>();
        return list.stream()
          .filter(n -> !elements.add(n))
          .collect(Collectors.toList());
    }

}
