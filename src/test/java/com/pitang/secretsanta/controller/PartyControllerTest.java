package com.pitang.secretsanta.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.pitang.secretsanta.dto.GiftDTO;
import com.pitang.secretsanta.dto.PartyDTO;
import com.pitang.secretsanta.service.PartyService;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class PartyControllerTest {

    @MockBean
    private PartyService partyService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JacksonTester<PartyDTO> jsonPartyDto;

    @Autowired
    private JacksonTester<GiftDTO> jsonGiftDto;

    @Test
    @WithMockUser(username = "user1", password = "pwd", roles = "USER")
    void testCreateParty() throws Exception {

        var partyDto = new PartyDTO("Festa de ano novo", LocalDate.now().plusDays(1), 100.0);

        MockHttpServletResponse response = mockMvc.perform(
            post("/party")
            .content(jsonPartyDto.write(partyDto).getJson())
            .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
    }


    @Test
    @WithMockUser(username = "user1", password = "pwd", roles = "USER")
    void testDeleteParty() throws Exception {

        MockHttpServletResponse response = mockMvc.perform(
            delete("/party/{id}",1)
        ).andReturn().getResponse();

        assertEquals(HttpStatus.NO_CONTENT.value(), response.getStatus());
    }

    @Test
    @WithMockUser(username = "user1", password = "pwd", roles = "USER")
    void testGetParty() throws Exception {

        MockHttpServletResponse response = mockMvc.perform(
            get("/party/{id}",1)
        ).andReturn().getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());

    }

    @Test
    @WithMockUser(username = "user1", password = "pwd", roles = "USER")
    void testInsertUser() throws Exception {

        MockHttpServletResponse response = mockMvc.perform(
            post("/party/{id}/user/{userId}",1, 1)
        ).andReturn().getResponse();

        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
    }

    @Test
    @WithMockUser(username = "user1", password = "pwd", roles = "USER")
    void testInsertUserGift() throws Exception {
        var giftDTO = new GiftDTO("Festa de ano novo", 100.0,null, 1L);

        MockHttpServletResponse response = mockMvc.perform(
            post("/party/{id}/gift",1)
            .content(jsonGiftDto.write(giftDTO).getJson())
            .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
    }

    @Test
    @WithMockUser(username = "user1", password = "pwd", roles = "USER")
    void testGenerateSecretSantas() throws Exception {
        MockHttpServletResponse response = mockMvc.perform(
            post("/party/{id}/generate-scret-santa",1)
        ).andReturn().getResponse();

        assertEquals(HttpStatus.NO_CONTENT.value(), response.getStatus());


    }

    @Test
    @WithMockUser(username = "user1", password = "pwd", roles = "USER")
    void testUpdateParty() throws Exception {
        var partyDto = new PartyDTO("Festa de ano novo", LocalDate.now().plusDays(1), 100.0);

        MockHttpServletResponse response = mockMvc.perform(
            put("/party/{id}",1)
            .content(jsonPartyDto.write(partyDto).getJson())
            .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertEquals(HttpStatus.NO_CONTENT.value(), response.getStatus());
    }

}
