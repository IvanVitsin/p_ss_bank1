package com.bank.antifraud.controllers;

import com.bank.antifraud.dto.AccountDTO;
import com.bank.antifraud.entity.AccountTransferEntity;
import com.bank.antifraud.mapper.AllEntityMapper;
import com.bank.antifraud.repository.AccountTransferRepository;
import com.bank.antifraud.service.AccountTransferService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class AccountTransferControllerTest {


    private MockMvc mockMvc;

    private AccountTransferEntity accountTransferEntity;

    private AccountDTO accountDTO;

    @InjectMocks
    private AccountTransferController accountTransferController;

    @Mock
    private AccountTransferService accountTransferService;


    @BeforeEach
    public void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(accountTransferController)
                .build();
        accountTransferEntity = AccountTransferEntity.builder()
                .id(1L)
                .accountTransferId(1L)
                .isBlocked(false)
                .isSuspicious(true)
                .blockedReason("...")
                .suspiciousReason("doesn't support Z")
                .build();
        accountDTO = AllEntityMapper.MAPPER.toDTO(accountTransferEntity);
    }


    @Test
    void addAccountTransfer() throws Exception {
        when(accountTransferService.saveAccountTransfer(any())).thenReturn(accountDTO);
        mockMvc.perform(post("/api/account-transfer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(accountDTO)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(accountDTO.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.accountTransferId").value(accountDTO.getAccountTransferId()));
        verify(accountTransferService).saveAccountTransfer(any());
    }

    @Test
    void getAccountTransferById() throws Exception {
        Long id = 1L;
        when(accountTransferService.findAccountTransferById(id)).thenReturn(accountDTO);
        mockMvc.perform(get("/api/account-transfer/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.accountTransferId").value(accountDTO.getAccountTransferId()));
        verify(accountTransferService).findAccountTransferById(id);


    }

    @Test
    void getAccountDetailsAll() throws Exception {
        when(accountTransferService.getAllAccounts()).thenReturn(List.of(accountDTO));
        mockMvc.perform(get("/api/account-transfer")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].suspiciousReason").value(accountDTO.getSuspiciousReason()));
        verify(accountTransferService).getAllAccounts();
    }

    @Test
    void deleteAccountTransfer() throws Exception {
        Long id = 1L;
        accountTransferService.saveAccountTransfer(accountDTO);
        doNothing().when(accountTransferService).deleteAccountTransfer(id);
        mockMvc.perform(delete("/api/account-transfer/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson("Данные успешно удалены")))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").value("Данные успешно удалены"));
        verify(accountTransferService).deleteAccountTransfer(id);
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
