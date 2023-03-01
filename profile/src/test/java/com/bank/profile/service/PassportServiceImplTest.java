package com.bank.profile.service;

import com.bank.profile.dto.PassportDTO;
import com.bank.profile.entity.PassportEntity;
import com.bank.profile.entity.RegistrationEntity;
import com.bank.profile.mapper.MultiEntityMapper;
import com.bank.profile.repository.PassportRepository;
import com.bank.profile.repository.RegistrationRepository;
import com.bank.profile.util.exeptions.NotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.hamcrest.MatcherAssert.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PassportServiceImplTest {
    @Mock
    private RegistrationRepository registrationRepository;
    @Mock
    private PassportRepository passportRepository;
    @InjectMocks
    private PassportServiceImpl passportService;

    @Test
    void testSaveNewPassport_whenRepositoryPassportReturnSavedObject_if_RepositoryRegistrationReturnObjectById() {
        //Arrange
        RegistrationEntity registrationEntity = new RegistrationEntity();
        registrationEntity.setId(1L);
        when(registrationRepository.findById(registrationEntity.getId())).thenReturn(Optional.of(registrationEntity));
        PassportDTO passportDTO = new PassportDTO(123456, 654321L, "Doe",
                "V", "M", "M", LocalDate.of(1990, 1, 1),
                "N", "D", LocalDate.of(2020, 1, 1),
                123456, LocalDate.of(2030, 1, 1));

        PassportEntity passportEntity = MultiEntityMapper.MAPPER.toPassport(passportDTO);
        when(registrationRepository.getReferenceById(1L)).thenReturn(registrationEntity);
        passportEntity.setRegistrationEntity(registrationEntity);
        when(passportRepository.saveAndFlush(any(PassportEntity.class))).thenReturn(passportEntity);
        //Act
        PassportDTO savedPassport = passportService.savePassport(passportDTO, 1L);
        //Assert
        verify(passportRepository, times(1)).saveAndFlush(any(PassportEntity.class));
        assertNull(passportEntity.getId());
        assertEquals(registrationEntity, passportEntity.getRegistrationEntity());
        assertEquals(passportDTO.getGender(), passportEntity.getGender());
        assertEquals(passportEntity.getNumber(), savedPassport.getNumber());
        assertEquals(savedPassport.getNumber(), passportDTO.getNumber());
        assertEquals(savedPassport.getGender(), passportDTO.getGender());
        assertEquals(savedPassport.getSeries(), passportDTO.getSeries());
        assertEquals(savedPassport.getExpirationDate(), passportDTO.getExpirationDate());
        verify(registrationRepository, times(1)).findById(registrationEntity.getId());
        verify(registrationRepository, times(1)).getReferenceById(1L);
    }

    @Test
    void testSaveNewPassport_neverRepositoryPassportReturnSavedObject_if_RepositoryRegistrationReturnEmptyValue() {
        PassportDTO passportDTO = new PassportDTO();
        Long idNot = 555L;
        when(registrationRepository.findById(idNot)).thenReturn(Optional.empty());
        //Act&Assert
        Exception exception = assertThrows(NotFoundException.class, () -> passportService.savePassport(passportDTO, idNot));
        assertEquals("No passport found with the given id", exception.getMessage());
        verify(registrationRepository, times(1)).findById(idNot);
        verify(registrationRepository, never()).getReferenceById(idNot);
        verify(passportRepository, never()).saveAndFlush(any());
    }


    @Test
    void testUpdatePassportWithId_whenRepositoryReturnObjectById() {
        RegistrationEntity registrationEntity = new RegistrationEntity();
        Long id = 1L;
        PassportEntity passportEntity = new PassportEntity(1L, 12456, 6L, "De",
                "Ce", "Me", "Me", LocalDate.of(1990, 12, 12),
                "Ne", "De", LocalDate.of(2020, 12, 12),
                13, LocalDate.of(2030, 12, 12), registrationEntity);

        PassportDTO passportDTO = new PassportDTO(123456, 654321L, "S",
                "V", "M", "M", LocalDate.of(1990, 1, 1),
                "N", "D", LocalDate.of(2020, 1, 1),
                123456, LocalDate.of(2030, 1, 1));
        when(passportRepository.findById(id)).thenReturn(Optional.of(passportEntity));
        when(passportRepository.saveAndFlush(any(PassportEntity.class))).thenReturn(passportEntity);

        PassportDTO result = passportService.update(passportDTO, id);

        assertNotNull(result);
        assertEquals(passportEntity.getRegistrationEntity(), registrationEntity);
        assertThat(passportEntity.getFirstName(), is(equalTo("V")));
        assertEquals(passportEntity.getRegistrationEntity(), registrationEntity);
        assertEquals(passportEntity.getNumber(), result.getNumber());
        assertEquals(passportEntity.getSeries(), result.getSeries());
        assertEquals(passportEntity.getLastName(), passportDTO.getLastName());
        verify(passportRepository, times(1)).findById(id);
        verify(passportRepository, times(1)).saveAndFlush(any(PassportEntity.class));
    }

    @Test
    void testUpdatePassportWithId_whenRepositoryReturnEmptyValue() {
        PassportDTO passportDTO = new PassportDTO();
        Long idNot = 555L;
        when(passportRepository.findById(idNot)).thenReturn(Optional.empty());
        //Act&Assert
        Exception exception = assertThrows(NotFoundException.class, () -> passportService.update(passportDTO, idNot));
        assertEquals("No passport found with the given id", exception.getMessage());
        verify(passportRepository, times(1)).findById(idNot);
        verify(passportRepository, never()).saveAndFlush(any());
    }

    @Test
    void testDeletePassportWithId_whenRepositoryReturnObjectById_and_whenRepositoryDeleteById() {
        //Arrange
        RegistrationEntity registrationEntity = new RegistrationEntity();
        Long id = 1L;
        PassportEntity passportEntity = new PassportEntity(1L, 12456, 6L, "De",
                "Ce", "Me", "Me", LocalDate.of(1990, 12, 12),
                "Ne", "De", LocalDate.of(2020, 12, 12),
                13, LocalDate.of(2030, 12, 12), registrationEntity);
        when(passportRepository.findById(id)).thenReturn(Optional.of(passportEntity));
        doNothing().when(passportRepository).deleteById(id);
        //Act
        passportService.delete(id);
        //Assert
        assertNotEquals(0, id);
        assertEquals(id, passportEntity.getId());
        verify(passportRepository, times(1)).findById(id);
        verify(passportRepository, times(1)).deleteById(id);
    }

    @Test
    void testDeletePassportWithId_whenRepositoryReturnEmptyValue() {
        //Arrange
        Long idNot = 555L;
        when(passportRepository.findById(idNot)).thenReturn(Optional.empty());
        //Act&Assert
        Exception exception = assertThrows(NotFoundException.class, () -> passportService.delete(idNot));
        assertEquals("No passport found with the given id", exception.getMessage());
        verify(passportRepository, times(1)).findById(idNot);
        verify(passportRepository, never()).deleteById(any());
    }

    @Test
    void testGetAllPassport_whenRepositoryIsNull() {
        //Arrange
        PassportServiceImpl passportService = new PassportServiceImpl(null, null);
        //Act&Assert
        Exception exception = assertThrows(NotFoundException.class, passportService::getALLPassport);
        assertEquals("passportRepository is null", exception.getMessage());
    }

    @Test
    void testGetAllPassport_whenRepositoryReturnsEmptyList() {
        //Arrange
        List<PassportEntity> testData = new ArrayList<>();
        when(passportRepository.findAll()).thenReturn(testData);
        //Act&Assert
        Exception exception = assertThrows(NotFoundException.class, () -> passportService.getALLPassport());
        assertEquals("No passports found", exception.getMessage());
        verify(passportRepository).findAll();
    }

    @Test
    void testGetAllPassport_whenRepositoryReturnsNotEmptyList() {
        //Arrange
        RegistrationEntity registrationEntity1 = new RegistrationEntity();

        PassportEntity passportEntity1 = new PassportEntity(1L, 123456, 63L, "De",
                "Cre", "Mre", "Mre", LocalDate.of(1990, 1, 12),
                "Nre", "Dre", LocalDate.of(2020, 1, 1),
                133, LocalDate.of(2030, 1, 1), registrationEntity1);
        RegistrationEntity registrationEntity2 = new RegistrationEntity();

        PassportEntity passportEntity2 = new PassportEntity(2L, 12456, 6L, "De",
                "Ce", "Me", "Me", LocalDate.of(1990, 12, 12),
                "Ne", "De", LocalDate.of(2020, 12, 12),
                13, LocalDate.of(2030, 12, 12), registrationEntity2);
        List<PassportEntity> testData = new ArrayList<>();
        testData.add(passportEntity1);
        testData.add(passportEntity2);
        when(passportRepository.findAll()).thenReturn(testData);
        //Act
        List<PassportDTO> registrationDTOs = testData
                .stream()
                .map(MultiEntityMapper.MAPPER::toPassportDTO)
                .collect(Collectors.toList());
        //Assert
        assertEquals(2, registrationDTOs.size());
        assertNotNull(registrationDTOs);
        assertEquals(registrationDTOs, passportService.getALLPassport());
        verify(passportRepository, times(1)).findAll();
    }
}