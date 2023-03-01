package com.bank.profile.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import com.bank.profile.dto.PassportDTO;
import com.bank.profile.service.PassportService;
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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class PassportControllerTest {
    private MockMvc mockMvc;
    @Mock
    private PassportService passportService;
    @InjectMocks
    private PassportController passportController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(passportController)
                .setControllerAdvice(new CustomExceptionHandler())
                .build();
    }

    @Test
    void testAddPassportWithValidInput() throws Exception {
        Long id = 5L;
        PassportDTO passportDTO = new PassportDTO(123456, 63L, "De",
                "Cre", "Mre", "Mre", LocalDate.of(1990, 1, 12),
                "Nre", "Dre", LocalDate.of(2020, 1, 1),
                133, LocalDate.of(2030, 1, 1));
        when(passportService.savePassport(any(), anyLong())).thenReturn(passportDTO);
        mockMvc.perform(post("/passport/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(passportDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").doesNotExist())
                .andExpect(jsonPath("$.gender").value("Mre"))
                .andExpect(jsonPath("$.lastName").value(passportDTO.getLastName()))
                .andExpect(jsonPath("$.firstName").value(passportDTO.getFirstName()))
                .andExpect(jsonPath("$.divisionCode").value(passportDTO.getDivisionCode()))
                .andExpect(content().json(toJson(passportDTO)));
        verify(passportService).savePassport(any(), anyLong());
    }

    @Test
    void testAddPassportWithNotValidInput() throws Exception {
        Long id = 5L;
        PassportDTO passportDTO = new PassportDTO(123456, null, "De",
                null, "", "", LocalDate.of(1990, 1, 12),
                "Nre", "Dre", LocalDate.of(2020, 1, 1),
                133, LocalDate.of(2030, 1, 1));
        mockMvc.perform(post("/passport/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(passportDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error")
                        .value("Передоваемый аргумент в методе не валидный"))
                .andExpect(jsonPath("$.error_description").exists());
        verifyNoInteractions(passportService);
    }

    @Test
    void testAddPassportWithNotFormatJSON() throws Exception {
        mockMvc.perform(post("/passport/5")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(""))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Неправильный формат  ожидаемого JSON формата"))
                .andExpect(jsonPath("$.error_description").exists());
        verifyNoInteractions(passportService);
    }

    @Test
    void testUpdatePassportWithValidInput() throws Exception {
        Long id = 5L;
        PassportDTO passportDTO = new PassportDTO(123456, 63L, "De",
                "Cre", "Mre", "Mre", LocalDate.of(1990, 1, 12),
                "Nre", "Dre", LocalDate.of(2020, 1, 1),
                133, LocalDate.of(2030, 1, 1));
        when(passportService.update(passportDTO, id)).thenReturn(passportDTO);
        mockMvc.perform(put("/passport/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(passportDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").doesNotExist())
                .andExpect(jsonPath("$.gender").value("Mre"))
                .andExpect(jsonPath("$.lastName").value(passportDTO.getLastName()))
                .andExpect(jsonPath("$.firstName").value(passportDTO.getFirstName()))
                .andExpect(jsonPath("$.divisionCode").value(passportDTO.getDivisionCode()));
        verify(passportService).update(passportDTO, id);
    }

    @Test
    void testUpdatePassportWithNotValidInput() throws Exception {
        Long id = 5L;
        PassportDTO passportDTO = new PassportDTO(123456, null, "De",
                null, "", "", LocalDate.of(1990, 1, 12),
                "Nre", "Dre", LocalDate.of(2020, 1, 1),
                133, LocalDate.of(2030, 1, 1));
        mockMvc.perform(put("/passport/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(passportDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error")
                        .value("Передоваемый аргумент в методе не валидный"))
                .andExpect(jsonPath("$.error_description").exists());
        verifyNoInteractions(passportService);
    }

    @Test
    void testUpdatePassportWithNotFormatJSON() throws Exception {
        mockMvc.perform(put("/passport/5")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(""))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Неправильный формат  ожидаемого JSON формата"))
                .andExpect(jsonPath("$.error_description").exists());
        verifyNoInteractions(passportService);
    }

    @Test
    void getPassportAll_returnsPassportList() throws Exception {
        PassportDTO passportDTO1 = new PassportDTO(123456, 63L, "De",
                "Cre", "Mre", "Mre", LocalDate.of(1990, 1, 12),
                "Nre", "Dre", LocalDate.of(2020, 1, 1),
                133, LocalDate.of(2030, 1, 1));


        PassportDTO passportDTO2 = new PassportDTO(12456, 6L, "De",
                "Ce", "Me", "Me", LocalDate.of(1990, 12, 12),
                "Ne", "De", LocalDate.of(2020, 12, 12),
                13, LocalDate.of(2030, 12, 12));
        List<PassportDTO> testData = new ArrayList<>();
        testData.add(passportDTO1);
        testData.add(passportDTO2);
        when(passportService.getALLPassport()).thenReturn(testData);
        mockMvc.perform(get("/passport"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").doesNotExist())
                .andExpect(jsonPath("$[0].gender").value("Mre"))
                .andExpect(jsonPath("$[0].lastName").value(passportDTO1.getLastName()))
                .andExpect(jsonPath("$[0].firstName").value(passportDTO1.getFirstName()))
                .andExpect(jsonPath("$[0].divisionCode").value(passportDTO1.getDivisionCode()))
                .andExpect(jsonPath("$[1].id").doesNotExist())
                .andExpect(jsonPath("$[1].gender").value("Me"))
                .andExpect(jsonPath("$[1].lastName").value(passportDTO2.getLastName()))
                .andExpect(jsonPath("$[1].firstName").value(passportDTO2.getFirstName()))
                .andExpect(jsonPath("$[1].divisionCode").value(passportDTO2.getDivisionCode()));
        verify(passportService).getALLPassport();
    }

    @Test
    void deletePassport_returnsMessage() throws Exception {
        Long id = 1L;
        String response = "Данные успешно удалены";
        doNothing().when(passportService).delete(id);
        mockMvc.perform(delete("/passport/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(response)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(response));
        verify(passportService, times(1)).delete(id);
    }

    @Test
    void handleMethodArgumentTypeMismatch_shouldReturnBadRequest() throws Exception {
        mockMvc.perform(delete("/passport/sss"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("The parameter  of value  could not be converted to type"))
                .andExpect(jsonPath("$.error_description").exists());
        verifyNoInteractions(passportService);
    }

    @Test
    void handleMethodArgumentAllException_shouldReturnStatusException() throws Exception {
        Long id = 1L;
        doThrow(NotFoundException.class).when(passportService).delete(id);
        mockMvc.perform(delete("/passport/{id}", id))
                .andExpect(status().isNotFound());
        verify(passportService, times(1)).delete(id);
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