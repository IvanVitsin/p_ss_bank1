package com.bank.profile.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import com.bank.profile.dto.RegistrationDTO;
import com.bank.profile.service.RegistrationService;
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
class RegistrationControllerTest {
    private MockMvc mockMvc;
    @Mock
    private RegistrationService registrationService;
    @InjectMocks
    private RegistrationController registrationController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(registrationController)
                .setControllerAdvice(new CustomExceptionHandler())
                .build();
    }

    @Test
    void testAddRegistrationWithValidInput() throws Exception {
        RegistrationDTO existingEntity = new RegistrationDTO();
        existingEntity.setCountry("Country1");
        existingEntity.setRegion("Region1");
        existingEntity.setCity("City1");
        existingEntity.setDistrict("District1");
        existingEntity.setLocality("Locality1");
        existingEntity.setStreet("Street1");
        existingEntity.setHouseNumber("123");
        existingEntity.setHouseBlock("A");
        existingEntity.setFlatNumber("4");
        existingEntity.setIndex(555L);
        when(registrationService.saveRegistration(any())).thenReturn(existingEntity);
        mockMvc.perform(post("/registration")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(existingEntity)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").doesNotExist())
                .andExpect(jsonPath("$.street").value("Street1"))
                .andExpect(jsonPath("$.index").value(existingEntity.getIndex()))
                .andExpect(jsonPath("$.region").value(existingEntity.getRegion()))
                .andExpect(jsonPath("$.city").value(existingEntity.getCity()))
                .andExpect(content().json(toJson(existingEntity)));
        verify(registrationService).saveRegistration(any());
    }

    @Test
    void testAddRegistrationWithNotValidInput() throws Exception {
        RegistrationDTO existingEntity = new RegistrationDTO();
        existingEntity.setCountry("Country1");
        existingEntity.setRegion("");
        existingEntity.setCity("City1");
        existingEntity.setDistrict("");
        existingEntity.setLocality(null);
        existingEntity.setStreet("");
        existingEntity.setHouseNumber("");
        existingEntity.setHouseBlock("A");
        existingEntity.setFlatNumber("4");
        existingEntity.setIndex(555L);
        mockMvc.perform(post("/registration")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(existingEntity)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error")
                        .value("Передоваемый аргумент в методе не валидный"))
                .andExpect(jsonPath("$.error_description").exists());
        verifyNoInteractions(registrationService);
    }

    @Test
    void testAddRegistrationWithNotFormatJSON() throws Exception {
        mockMvc.perform(post("/registration")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(""))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Неправильный формат  ожидаемого JSON формата"))
                .andExpect(jsonPath("$.error_description").exists());
        verifyNoInteractions(registrationService);
    }

    @Test
    void testUpdateRegistrationWithValidInput() throws Exception {
        Long id = 5L;
        RegistrationDTO existingEntity = new RegistrationDTO();
        existingEntity.setCountry("Country1");
        existingEntity.setRegion("Region1");
        existingEntity.setCity("City1");
        existingEntity.setDistrict("District1");
        existingEntity.setLocality("Locality1");
        existingEntity.setStreet("Street1");
        existingEntity.setHouseNumber("123");
        existingEntity.setHouseBlock("A");
        existingEntity.setFlatNumber("4");
        existingEntity.setIndex(555L);
        when(registrationService.update(existingEntity, id)).thenReturn(existingEntity);
        mockMvc.perform(put("/registration/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(existingEntity)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").doesNotExist())
                .andExpect(jsonPath("$.street").value("Street1"))
                .andExpect(jsonPath("$.index").value(existingEntity.getIndex()))
                .andExpect(jsonPath("$.region").value(existingEntity.getRegion()))
                .andExpect(jsonPath("$.city").value(existingEntity.getCity()))
                .andExpect(content().json(toJson(existingEntity)));
        verify(registrationService).update(existingEntity, id);
    }

    @Test
    void testUpdateRegistrationWithNotValidInput() throws Exception {
        Long id = 5L;
        RegistrationDTO existingEntity = new RegistrationDTO();
        existingEntity.setCountry("Country1");
        existingEntity.setRegion("");
        existingEntity.setCity("City1");
        existingEntity.setDistrict("");
        existingEntity.setLocality(null);
        existingEntity.setStreet("");
        existingEntity.setHouseNumber("");
        existingEntity.setHouseBlock("A");
        existingEntity.setFlatNumber("4");
        existingEntity.setIndex(555L);
        mockMvc.perform(put("/registration/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(existingEntity)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error")
                        .value("Передоваемый аргумент в методе не валидный"))
                .andExpect(jsonPath("$.error_description").exists());
        verifyNoInteractions(registrationService);
    }

    @Test
    void testUpdateRegistrationWithNotFormatJSON() throws Exception {
        mockMvc.perform(put("/registration/5")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(""))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Неправильный формат  ожидаемого JSON формата"))
                .andExpect(jsonPath("$.error_description").exists());
        verifyNoInteractions(registrationService);
    }

    @Test
    void getRegistrationAll_returnsRegistrationList() throws Exception {
        RegistrationDTO existingEntity1 = new RegistrationDTO();
        existingEntity1.setCountry("Country1");
        existingEntity1.setRegion("Region1");
        existingEntity1.setCity("City1");
        existingEntity1.setDistrict("District1");
        existingEntity1.setLocality("Locality1");
        existingEntity1.setStreet("Street1");
        existingEntity1.setHouseNumber("123");
        existingEntity1.setHouseBlock("A");
        existingEntity1.setFlatNumber("4");
        existingEntity1.setIndex(555L);
        RegistrationDTO existingEntity2 = new RegistrationDTO();
        existingEntity2.setCountry("Country12");
        existingEntity2.setRegion("Region12");
        existingEntity2.setCity("City12");
        existingEntity2.setDistrict("District12");
        existingEntity2.setLocality("Locality12");
        existingEntity2.setStreet("Street12");
        existingEntity2.setHouseNumber("1232");
        existingEntity2.setHouseBlock("A2");
        existingEntity2.setFlatNumber("42");
        existingEntity2.setIndex(5555L);
        List<RegistrationDTO> testData = new ArrayList<>();
        testData.add(existingEntity1);
        testData.add(existingEntity2);
        when(registrationService.getALLRegistration()).thenReturn(testData);
        mockMvc.perform(get("/registration"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].street").value("Street1"))
                .andExpect(jsonPath("$[0].index").value(existingEntity1.getIndex()))
                .andExpect(jsonPath("$[0].region").value(existingEntity1.getRegion()))
                .andExpect(jsonPath("$[0].city").value(existingEntity1.getCity()))
                .andExpect(jsonPath("$[1].street").value("Street12"))
                .andExpect(jsonPath("$[1].index").value(existingEntity2.getIndex()))
                .andExpect(jsonPath("$[1].region").value(existingEntity2.getRegion()))
                .andExpect(jsonPath("$[1].city").value(existingEntity2.getCity()));
        verify(registrationService).getALLRegistration();
    }

    @Test
    void deleteRegistration_returnsMessage() throws Exception {
        Long id = 1L;
        String response = "Данные успешно удалены";
        doNothing().when(registrationService).delete(id);
        mockMvc.perform(delete("/registration/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(response)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(response));
        verify(registrationService, times(1)).delete(id);
    }

    @Test
    void handleMethodArgumentTypeMismatch_shouldReturnBadRequest() throws Exception {
        mockMvc.perform(delete("/registration/sss"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("The parameter  of value  could not be converted to type"))
                .andExpect(jsonPath("$.error_description").exists());
        verifyNoInteractions(registrationService);
    }

    @Test
    void handleMethodArgumentAllException_shouldReturnStatusException() throws Exception {
        Long id = 1L;
        doThrow(NotFoundException.class).when(registrationService).delete(id);
        mockMvc.perform(delete("/registration/{id}", id))
                .andExpect(status().isNotFound());
        verify(registrationService, times(1)).delete(id);
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