package com.bank.antifraud.controllers;

import com.bank.antifraud.dto.CardDTO;
import com.bank.antifraud.entity.AccountTransferEntity;
import com.bank.antifraud.entity.CardTransferEntity;
import com.bank.antifraud.mapper.AllEntityMapper;
import com.bank.antifraud.service.CardTransferService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class CardTransferControllerTest {


    private MockMvc mockMvc;
    private CardTransferEntity cardTransferEntity;
    private CardDTO cardDTO;

    @Mock
    private CardTransferService cardTransferService;

    @InjectMocks
    private CardTransferController cardTransferController;


    @BeforeEach
    public void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(cardTransferController)
                .build();
        cardTransferEntity = CardTransferEntity.builder()
                .id(1L)
                .cardTransferId(1L)
                .isBlocked(false)
                .isSuspicious(true)
                .blockedReason("...")
                .suspiciousReason("doesn't support Z")
                .build();
        cardDTO = AllEntityMapper.MAPPER.toDTO(cardTransferEntity);
    }

    @Test
    void addAccountTransfer() throws Exception {
        when(cardTransferService.saveAccountTransfer(any())).thenReturn(cardDTO);
        mockMvc.perform(post("/api/card-transfer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(cardDTO)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(cardDTO.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.cardTransferId").value(cardDTO.getCardTransferId()));
        verify(cardTransferService).saveAccountTransfer(any());

    }

    @Test
    void getAccountTransferById() throws Exception {
        Long id = 1L;
        when(cardTransferService.findAccountTransferById(id)).thenReturn(cardDTO);
        mockMvc.perform(get("/api/card-transfer/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.cardTransferId").value(cardDTO.getCardTransferId()));
        verify(cardTransferService).findAccountTransferById(id);

    }

    @Test
    void getAccountDetailsAll() throws Exception {
        when(cardTransferService.getAllAccounts()).thenReturn(List.of(cardDTO));
        mockMvc.perform(get("/api/card-transfer")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].suspiciousReason").value(cardDTO.getSuspiciousReason()));
        verify(cardTransferService).getAllAccounts();
    }

    @Test
    void deleteAccountTransfer() throws Exception {
        Long id = 1L;
        cardTransferService.saveAccountTransfer(cardDTO);
        doNothing().when(cardTransferService).deleteAccountTransfer(id);
        mockMvc.perform(delete("/api/card-transfer/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson("Данные успешно удалены")))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").value("Данные успешно удалены"));
        verify(cardTransferService).deleteAccountTransfer(id);
    }



    private String toJson(Object object) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
