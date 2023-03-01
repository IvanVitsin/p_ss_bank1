package com.bank.profile.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import com.bank.profile.dto.AccountDetailsDTO;
import com.bank.profile.service.AccountDetailsService;
import com.bank.profile.util.exeptions.CustomExceptionHandler;
import com.bank.profile.util.exeptions.NotFoundException;
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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class AccountDetailsControllerTest {
    private MockMvc mockMvc;
    @Mock
    private AccountDetailsService accountDetailsService;
    @InjectMocks
    private AccountDetailsController accountDetailsController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(accountDetailsController)
                .setControllerAdvice(new CustomExceptionHandler())
                .build();
    }

    @Test
    void testAddDetailsWithValidInput() throws Exception {
        Long id = 4L;
        AccountDetailsDTO accountDetailsDto = new AccountDetailsDTO(9L);
        when(accountDetailsService.saveAccountDetails(any(), anyLong())).thenReturn(accountDetailsDto);
        mockMvc.perform(post("/details/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(accountDetailsDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").doesNotExist())
                .andExpect(jsonPath("$.accountId").value(accountDetailsDto.getAccountId()))
                .andExpect(content().json(toJson(accountDetailsDto)));
        verify(accountDetailsService).saveAccountDetails(any(), anyLong());
    }

    @Test
    void testAddDetailsWithNotValidInput() throws Exception {
        Long id = 4L;
        AccountDetailsDTO accountDetailsDto = new AccountDetailsDTO(999999999999999999L);
        mockMvc.perform(post("/details/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(accountDetailsDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error")
                        .value("Передоваемый аргумент в методе не валидный"))
                .andExpect(jsonPath("$.error_description").exists());
        verifyNoInteractions(accountDetailsService);
    }

    @Test
    void testAddDetailsWithNotFormatJSON() throws Exception {
        mockMvc.perform(post("/details/5")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(""))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Неправильный формат  ожидаемого JSON формата"))
                .andExpect(jsonPath("$.error_description").exists());
        verifyNoInteractions(accountDetailsService);
    }

    @Test
    void testUpdateDetailsWithValidInput() throws Exception {
        Long id = 4L;
        AccountDetailsDTO accountDetailsDto = new AccountDetailsDTO(99L);
        when(accountDetailsService.updateAccountDetails(accountDetailsDto, id)).thenReturn(accountDetailsDto);
        mockMvc.perform(put("/details/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(accountDetailsDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").doesNotExist())
                .andExpect(jsonPath("$.accountId").value(accountDetailsDto.getAccountId()))
                .andExpect(content().json(toJson(accountDetailsDto)));
        verify(accountDetailsService).updateAccountDetails(any(), anyLong());
    }

    @Test
    void testUpdateDetailsWithNotValidInput() throws Exception {
        Long id = 4L;
        AccountDetailsDTO accountDetailsDto = new AccountDetailsDTO(999999999999999999L);
        mockMvc.perform(put("/details/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(accountDetailsDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error")
                        .value("Передоваемый аргумент в методе не валидный"))
                .andExpect(jsonPath("$.error_description").exists());
        verifyNoInteractions(accountDetailsService);
    }

    @Test
    void testUpdateDetailsWithNotFormatJSON() throws Exception {
        mockMvc.perform(put("/details/5")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(""))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Неправильный формат  ожидаемого JSON формата"))
                .andExpect(jsonPath("$.error_description").exists());
        verifyNoInteractions(accountDetailsService);
    }

    @Test
    void getDetailsAll_returnsDetailsList() throws Exception {
        AccountDetailsDTO accountDetailsDto1 = new AccountDetailsDTO(9L);
        AccountDetailsDTO accountDetailsDto2 = new AccountDetailsDTO(8L);
        List<AccountDetailsDTO> testData = new ArrayList<>();
        testData.add(accountDetailsDto1);
        testData.add(accountDetailsDto2);
        when(accountDetailsService.getALLAccountDetails()).thenReturn(testData);
        mockMvc.perform(get("/details"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].accountId").value(accountDetailsDto1.getAccountId()))
                .andExpect(jsonPath("$[1].accountId").value(accountDetailsDto2.getAccountId()));
        verify(accountDetailsService).getALLAccountDetails();
    }

    @Test
    void deleteDetails_returnsMessage() throws Exception {
        Long id = 1L;
        doNothing().when(accountDetailsService).deleteAccountDetails(id);
        mockMvc.perform(delete("/details/{id}", id))
                .andExpect(status().isOk());
        verify(accountDetailsService, times(1)).deleteAccountDetails(id);
    }

    @Test
    void handleMethodArgumentTypeMismatch_shouldReturnBadRequest() throws Exception {
        mockMvc.perform(delete("/details/sss"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("The parameter  of value  could not be converted to type"))
                .andExpect(jsonPath("$.error_description").exists());
        verifyNoInteractions(accountDetailsService);
    }

    @Test
    void handleMethodArgumentAllException_shouldReturnStatusException() throws Exception {
        Long id = 1L;
        doThrow(NotFoundException.class).when(accountDetailsService).deleteAccountDetails(id);
        mockMvc.perform(delete("/details/{id}", id))
                .andExpect(status().isNotFound());
        verify(accountDetailsService, times(1)).deleteAccountDetails(id);
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