package com.bank.profile.service;

import com.bank.profile.dto.RegistrationDTO;
import com.bank.profile.entity.RegistrationEntity;
import com.bank.profile.mapper.MultiEntityMapper;
import com.bank.profile.repository.RegistrationRepository;
import com.bank.profile.util.exeptions.NotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.hamcrest.MatcherAssert.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RegistrationServiceImplTest {
    @Mock
    private RegistrationRepository registrationRepository;
    @InjectMocks
    private RegistrationServiceImpl registrationService;

    @Test
    void testSaveNewRegistration_whenRepositoryReturnSavedObject() {
        //Arrange
        RegistrationDTO registrationDTO = new RegistrationDTO();
        registrationDTO.setCountry("Country2");
        registrationDTO.setRegion("Region2");
        registrationDTO.setCity("City2");
        registrationDTO.setDistrict("District2");
        registrationDTO.setLocality("Locality2");
        registrationDTO.setStreet("Street2");
        registrationDTO.setHouseNumber("456");
        registrationDTO.setHouseBlock("B");
        registrationDTO.setFlatNumber("3");
        registrationDTO.setIndex(654321L);
        RegistrationEntity registrationEntity = MultiEntityMapper.MAPPER.toRegistration(registrationDTO);
        when(registrationRepository.saveAndFlush(any(RegistrationEntity.class))).thenReturn(registrationEntity);
        //Act
        RegistrationDTO savedRegistration = registrationService.saveRegistration(registrationDTO);
        //Assert
        assertNotNull(savedRegistration);
        assertEquals(registrationEntity.getDistrict(), savedRegistration.getDistrict());
        assertEquals(registrationDTO.getCountry(), savedRegistration.getCountry());
        assertEquals(registrationDTO.getRegion(), savedRegistration.getRegion());
        assertEquals(registrationDTO.getCity(), savedRegistration.getCity());
        assertEquals(registrationDTO.getDistrict(), savedRegistration.getDistrict());
        assertEquals(registrationDTO.getLocality(), savedRegistration.getLocality());
        assertEquals(registrationDTO.getStreet(), savedRegistration.getStreet());
        assertEquals(registrationDTO.getHouseNumber(), savedRegistration.getHouseNumber());
        assertEquals(registrationDTO.getHouseBlock(), savedRegistration.getHouseBlock());
        assertEquals(registrationDTO.getFlatNumber(), savedRegistration.getFlatNumber());
        assertEquals(registrationDTO.getIndex(), savedRegistration.getIndex());
        assertThat(savedRegistration.getCountry(), is(equalTo("Country2")));
        verify(registrationRepository, times(1)).saveAndFlush(any(RegistrationEntity.class));
    }

    @Test
    void testUpdateRegistrationWithId_whenRepositoryReturnObjectById() {
        //Arrange
        Long id = 1L;
        RegistrationEntity existingEntity = new RegistrationEntity();
        existingEntity.setId(id);
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
        RegistrationDTO registrationDTO = new RegistrationDTO();
        registrationDTO.setCountry("Country2");
        registrationDTO.setRegion("Region2");
        registrationDTO.setCity("City2");
        registrationDTO.setDistrict("District2");
        registrationDTO.setLocality("Locality2");
        registrationDTO.setStreet("Street2");
        registrationDTO.setHouseNumber("456");
        registrationDTO.setHouseBlock("B");
        registrationDTO.setFlatNumber("3");
        registrationDTO.setIndex(696L);
        ArgumentCaptor<RegistrationEntity> argumentCaptor = ArgumentCaptor.forClass(RegistrationEntity.class);
        when(registrationRepository.findById(id)).thenReturn(Optional.of(existingEntity));
        when(registrationRepository.saveAndFlush(any(RegistrationEntity.class))).thenReturn(existingEntity);
        //Act
        RegistrationDTO result = registrationService.update(registrationDTO, id);
        //Assert
        assertNotNull(result);
        verify(registrationRepository).saveAndFlush(argumentCaptor.capture());
        RegistrationEntity capturedEntity = argumentCaptor.getValue();
        assertThat(capturedEntity, is(equalTo(existingEntity)));
        assertThat(capturedEntity.getCity(), is(equalTo(registrationDTO.getCity())));
        assertThat(capturedEntity.getCountry(), is(equalTo(registrationDTO.getCountry())));
        assertThat(capturedEntity.getRegion(), is(equalTo(registrationDTO.getRegion())));
        verify(registrationRepository, times(1)).findById(id);
        verify(registrationRepository, times(1)).saveAndFlush(any(RegistrationEntity.class));
    }

    @Test
    void testUpdateRegistrationWithId_whenRepositoryReturnEmptyValue() {
        //Arrange
        Long notId = 123L;
        RegistrationDTO registrationDTO = new RegistrationDTO();
        registrationDTO.setCountry("Country2");
        registrationDTO.setRegion("Region2");
        registrationDTO.setCity("City2");
        registrationDTO.setDistrict("District2");
        registrationDTO.setLocality("Locality2");
        registrationDTO.setStreet("Street2");
        registrationDTO.setHouseNumber("456");
        registrationDTO.setHouseBlock("B");
        registrationDTO.setFlatNumber("3");
        registrationDTO.setIndex(696L);
        when(registrationRepository.findById(notId)).thenReturn(Optional.empty());
        //Act&Assert
        assertThrows(NotFoundException.class, () -> registrationService.update(registrationDTO, notId));
        verify(registrationRepository, times(1)).findById(notId);
        verify(registrationRepository, never()).saveAndFlush(any());
    }

    @Test
    void testDeleteRegistrationWithId_whenRepositoryReturnObjectById_and_whenRepositoryDeleteById() {
        //Arrange
        Long id = 1L;
        RegistrationEntity existingEntity = new RegistrationEntity();
        existingEntity.setId(id);
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
        when(registrationRepository.findById(id)).thenReturn(Optional.of(existingEntity));
        doNothing().when(registrationRepository).deleteById(existingEntity.getId());
        //Act
        registrationService.delete(id);
        //Assert
        assertNotEquals(0, id);
        assertEquals(id, existingEntity.getId());
        verify(registrationRepository, times(1)).findById(id);
        verify(registrationRepository, times(1)).deleteById(id);
    }

    @Test
    void testDeleteRegistrationWithId_whenRepositoryReturnEmptyValue() {
        //Arrange
        Long idNot = 555L;
        when(registrationRepository.findById(idNot)).thenReturn(Optional.empty());
        //Act&Assert
        Exception exception = assertThrows(NotFoundException.class, () -> registrationService.delete(idNot));
        assertEquals("No registration found with the given id", exception.getMessage());
        verify(registrationRepository, times(1)).findById(idNot);
        verify(registrationRepository, never()).deleteById(any());
    }

    @Test
    void testGetAllRegistration_whenRepositoryIsNull() {
        //Arrange
        RegistrationServiceImpl registrationService = new RegistrationServiceImpl(null);
        //Act&Assert
        assertThrows(NotFoundException.class, registrationService::getALLRegistration);
    }

    @Test
    void testGetAllRegistration_whenRepositoryReturnsEmptyList() {
        //Arrange
        List<RegistrationEntity> testData = new ArrayList<>();
        when(registrationRepository.findAll()).thenReturn(testData);
        //Act&Assert
        assertThrows(NotFoundException.class, () -> registrationService.getALLRegistration());
        verify(registrationRepository).findAll();
    }

    @Test
    void testGetAllRegistration_whenRepositoryReturnsNotEmptyList() {
        //Arrange
        RegistrationEntity existingEntity1 = new RegistrationEntity();
        existingEntity1.setId(1L);
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
        RegistrationEntity existingEntity2 = new RegistrationEntity();
        existingEntity2.setId(2L);
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
        List<RegistrationEntity> testData = new ArrayList<>();
        testData.add(existingEntity1);
        testData.add(existingEntity2);
        when(registrationRepository.findAll()).thenReturn(testData);
        //Act
        List<RegistrationDTO> registrationDTOs = testData
                .stream()
                .map(MultiEntityMapper.MAPPER::toRegistrationDTO)
                .collect(Collectors.toList());
        //Assert
        assertEquals(2, registrationDTOs.size());
        assertNotNull(registrationDTOs);
        assertEquals(registrationDTOs, registrationService.getALLRegistration());
        verify(registrationRepository, times(1)).findAll();
    }
}