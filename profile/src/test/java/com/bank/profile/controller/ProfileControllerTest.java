package com.bank.profile.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import com.bank.profile.dto.PassportDTO;
import com.bank.profile.dto.ProfileDTO;
import com.bank.profile.dto.RegistrationDTO;
import com.bank.profile.service.ProfileService;
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

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class ProfileControllerTest {
    private MockMvc mockMvc;
    @Mock
    private ProfileService profileService;
    @InjectMocks
    private ProfileController profileController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(profileController)
                .setControllerAdvice(new CustomExceptionHandler())
                .build();
    }

    @Test
    void testAddProfileWithValidInputWithActualRegistration() throws Exception {
        ProfileDTO profileDTO = new ProfileDTO(123L, "email",
                "Vr", 121L, 1580L);
        Long idPassport = 5L;
        Long idRegis = 6L;
        when(profileService.saveProfile(any(), anyLong(), anyLong())).thenReturn(profileDTO);
        mockMvc.perform(post("/profile/{id1}/{id2}", idPassport, idRegis)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(profileDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").doesNotExist())
                .andExpect(jsonPath("$.email").value("email"))
                .andExpect(jsonPath("$.inn").value(profileDTO.getInn()))
                .andExpect(jsonPath("$.snils").value(profileDTO.getSnils()))
                .andExpect(content().json(toJson(profileDTO)));
        verify(profileService).saveProfile(any(), anyLong(), anyLong());
    }

    @Test
    void testAddProfileWithNotValidInputWithActualRegistration() throws Exception {
        ProfileDTO profileDTO = new ProfileDTO(123L, null,
                "", null, 1580L);
        Long idPassport = 5L;
        Long idRegis = 6L;
        mockMvc.perform(post("/profile/{id1}/{id2}", idPassport, idRegis)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(profileDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error")
                        .value("Передоваемый аргумент в методе не валидный"))
                .andExpect(jsonPath("$.error_description").exists());
        verifyNoInteractions(profileService);
    }

    @Test
    void testAddProfileWithNotFormatJSONWithActualRegistration() throws Exception {
        mockMvc.perform(post("/profile/5/4")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(""))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Неправильный формат  ожидаемого JSON формата"))
                .andExpect(jsonPath("$.error_description").exists());
        verifyNoInteractions(profileService);
    }

    @Test
    void testAddProfileWithValidInputNotActualRegistration() throws Exception {
        ProfileDTO profileDTO = new ProfileDTO(123L, "email",
                "Vr", 121L, 1580L);
        Long idPassport = 5L;
        when(profileService.saveProfileNoActualRegistration(any(), anyLong())).thenReturn(profileDTO);
        mockMvc.perform(post("/profile/{id1}", idPassport)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(profileDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").doesNotExist())
                .andExpect(jsonPath("$.email").value("email"))
                .andExpect(jsonPath("$.inn").value(profileDTO.getInn()))
                .andExpect(jsonPath("$.snils").value(profileDTO.getSnils()))
                .andExpect(content().json(toJson(profileDTO)));
        verify(profileService).saveProfileNoActualRegistration(any(), anyLong());
    }

    @Test
    void testAddProfileWithNotValidInputNotActualRegistration() throws Exception {
        ProfileDTO profileDTO = new ProfileDTO(123L, null,
                "", null, 1580L);
        Long idPassport = 5L;
        mockMvc.perform(post("/profile/{id1}", idPassport)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(profileDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error")
                        .value("Передоваемый аргумент в методе не валидный"))
                .andExpect(jsonPath("$.error_description").exists());
        verifyNoInteractions(profileService);
    }

    @Test
    void testAddProfileWithNotFormatJSONNotActualRegistration() throws Exception {
        mockMvc.perform(post("/profile/5")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(""))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Неправильный формат  ожидаемого JSON формата"))
                .andExpect(jsonPath("$.error_description").exists());
        verifyNoInteractions(profileService);
    }

    @Test
    void testUpdateProfileWithValidInput() throws Exception {
        ProfileDTO profileDTO = new ProfileDTO(123L, "email",
                "Vr", 121L, 1580L);
        Long id = 5L;
        when(profileService.update(any(), anyLong())).thenReturn(profileDTO);
        mockMvc.perform(put("/profile/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(profileDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").doesNotExist());
        verify(profileService).update(any(), anyLong());
    }

    @Test
    void testUpdateProfileWithNotValidInput() throws Exception {
        ProfileDTO profileDTO = new ProfileDTO(123L, null,
                "", null, 1580L);
        Long id = 5L;
        mockMvc.perform(put("/profile/{id1}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(profileDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error")
                        .value("Передоваемый аргумент в методе не валидный"))
                .andExpect(jsonPath("$.error_description").exists());
        verifyNoInteractions(profileService);
    }

    @Test
    void testUpdateProfileWithNotFormatJSON() throws Exception {
        mockMvc.perform(put("/profile/5")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(""))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Неправильный формат  ожидаемого JSON формата"))
                .andExpect(jsonPath("$.error_description").exists());
        verifyNoInteractions(profileService);
    }

    @Test
    void deleteProfile_returnsMessage() throws Exception {
        Long id = 1L;
        String response = "Данные успешно удалены";
        doNothing().when(profileService).delete(id);
        mockMvc.perform(delete("/profile/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(response)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(response));
        verify(profileService, times(1)).delete(id);
    }

    @Test
    void testGetRegistrationInProfile_returnRegistration() throws Exception {
        Long id = 5L;
        RegistrationDTO registrationDTO = new RegistrationDTO("f", "r",
                "y", "q", "ff", "l", "q", "ff", "l",
                55L);
        when(profileService.getPassportRegistration(anyLong())).thenReturn(registrationDTO);
        mockMvc.perform(get("/profile/registration/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").doesNotExist())
                .andExpect(jsonPath("$.street").value("l"))
                .andExpect(jsonPath("$.index").value(registrationDTO.getIndex()))
                .andExpect(jsonPath("$.region").value(registrationDTO.getRegion()))
                .andExpect(jsonPath("$.city").value(registrationDTO.getCity()))
                .andExpect(content().json(toJson(registrationDTO)));
        verify(profileService).getPassportRegistration(anyLong());
    }

    @Test
    void testGetPassportInProfile_returnPassport() throws Exception {
        Long id = 5L;
        PassportDTO passportDTO = new PassportDTO(123456, 63L, "De",
                "Cre", "Mre", "Mre", LocalDate.of(1990, 1, 12),
                "Nre", "Dre", LocalDate.of(2020, 1, 1),
                133, LocalDate.of(2030, 1, 1));
        when(profileService.getProfilePassport(anyLong())).thenReturn(passportDTO);
        mockMvc.perform(get("/profile/passport/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").doesNotExist())
                .andExpect(jsonPath("$.gender").value("Mre"))
                .andExpect(jsonPath("$.lastName").value(passportDTO.getLastName()))
                .andExpect(jsonPath("$.firstName").value(passportDTO.getFirstName()))
                .andExpect(jsonPath("$.divisionCode").value(passportDTO.getDivisionCode()));
        verify(profileService).getProfilePassport(anyLong());
    }

    @Test
    void handleMethodArgumentTypeMismatch_shouldReturnBadRequest() throws Exception {
        mockMvc.perform(delete("/profile/sss"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("The parameter  of value  could not be converted to type"))
                .andExpect(jsonPath("$.error_description").exists());
        verifyNoInteractions(profileService);
    }

    @Test
    void handleMethodArgumentAllException_shouldReturnStatusException() throws Exception {
        Long id = 1L;
        doThrow(NotFoundException.class).when(profileService).delete(id);
        mockMvc.perform(delete("/profile/{id}", id))
                .andExpect(status().isNotFound());
        verify(profileService, times(1)).delete(id);
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

