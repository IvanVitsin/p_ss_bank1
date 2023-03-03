package com.bank.antifraud.controllers;

import com.bank.antifraud.dto.CardDTO;
import com.bank.antifraud.dto.PhoneDTO;
import com.bank.antifraud.entity.CardTransferEntity;
import com.bank.antifraud.entity.PhoneTransferEntity;
import com.bank.antifraud.mapper.AllEntityMapper;
import com.bank.antifraud.service.CardTransferService;
import com.bank.antifraud.service.PhoneTransferService;
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
public class PhoneTransferControllerTest {



    private MockMvc mockMvc;
    private PhoneTransferEntity phoneTransferEntity;
    private PhoneDTO phoneDTO;

    @Mock
    private PhoneTransferService phoneTransferService;

    @InjectMocks
    private PhoneTransferController phoneTransferController;


    @BeforeEach
    public void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(phoneTransferController)
                .build();
        phoneTransferEntity = PhoneTransferEntity.builder()
                .id(1L)
                .phoneTransferId(1L)
                .isBlocked(false)
                .isSuspicious(true)
                .blockedReason("...")
                .suspiciousReason("doesn't support Z")
                .build();
        phoneDTO = AllEntityMapper.MAPPER.toDTO(phoneTransferEntity);
    }

    @Test
    void addAccountTransfer() throws Exception {
        when(phoneTransferService.saveAccountTransfer(any())).thenReturn(phoneDTO);
        mockMvc.perform(post("/api/phone-transfer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(phoneDTO)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(phoneDTO.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.phoneTransferId").value(phoneDTO.getPhoneTransferId()));
        verify(phoneTransferService).saveAccountTransfer(any());

    }

    @Test
    void getAccountTransferById() throws Exception {
        Long id = 1L;
        when(phoneTransferService.findAccountTransferById(id)).thenReturn(phoneDTO);
        mockMvc.perform(get("/api/phone-transfer/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.phoneTransferId").value(phoneDTO.getPhoneTransferId()));
        verify(phoneTransferService).findAccountTransferById(id);

    }

    @Test
    void getAccountDetailsAll() throws Exception {
        when(phoneTransferService.getAllAccounts()).thenReturn(List.of(phoneDTO));
        mockMvc.perform(get("/api/phone-transfer")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].suspiciousReason").value(phoneDTO.getSuspiciousReason()));
        verify(phoneTransferService).getAllAccounts();
    }

    @Test
    void deleteAccountTransfer() throws Exception {
        Long id = 1L;
        phoneTransferService.saveAccountTransfer(phoneDTO);
        doNothing().when(phoneTransferService).deleteAccountTransfer(id);
        mockMvc.perform(delete("/api/phone-transfer/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson("Данные успешно удалены")))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").value("Данные успешно удалены"));
        verify(phoneTransferService).deleteAccountTransfer(id);
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
