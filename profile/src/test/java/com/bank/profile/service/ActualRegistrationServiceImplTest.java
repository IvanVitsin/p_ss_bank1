package com.bank.profile.service;

import com.bank.profile.dto.ActualRegistrationDTO;
import com.bank.profile.entity.ActualRegistrationEntity;
import com.bank.profile.mapper.MultiEntityMapper;
import com.bank.profile.repository.ActualRegisRepository;
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
public class ActualRegistrationServiceImplTest {
    @Mock
    private ActualRegisRepository registrationRepository;
    @InjectMocks
    private ActualRegistrationServiceImpl registrationService;

    @Test
    void testSaveNewActualRegistration_whenRepositoryReturnSavedObject() {
        //Arrange
        ActualRegistrationDTO registrationDTO = new ActualRegistrationDTO();
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
        ActualRegistrationEntity registrationEntity = MultiEntityMapper.MAPPER.toActualRegistration(registrationDTO);
        when(registrationRepository.saveAndFlush(any(ActualRegistrationEntity.class))).thenReturn(registrationEntity);
        //Act
        ActualRegistrationDTO savedRegistration = registrationService.saveActualRegistration(registrationDTO);
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
        verify(registrationRepository, times(1)).saveAndFlush(any(ActualRegistrationEntity.class));
    }

    @Test
    void testUpdateActualRegistrationWithId_whenRepositoryReturnObjectById() {
        //Arrange
        Long id = 1L;
        ActualRegistrationEntity existingEntity = new ActualRegistrationEntity();
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
        ActualRegistrationDTO registrationDTO = new ActualRegistrationDTO();
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
        ArgumentCaptor<ActualRegistrationEntity> argumentCaptor = ArgumentCaptor.forClass(ActualRegistrationEntity.class);
        when(registrationRepository.findById(id)).thenReturn(Optional.of(existingEntity));
        when(registrationRepository.saveAndFlush(any(ActualRegistrationEntity.class))).thenReturn(existingEntity);
        //Act
        ActualRegistrationDTO result = registrationService.updateActualRegistration(registrationDTO, id);
        //Assert
        assertNotNull(result);
        verify(registrationRepository).saveAndFlush(argumentCaptor.capture());
        ActualRegistrationEntity capturedEntity = argumentCaptor.getValue();
        assertThat(capturedEntity, is(equalTo(existingEntity)));
        assertThat(capturedEntity.getCity(), is(equalTo(registrationDTO.getCity())));
        assertThat(capturedEntity.getCountry(), is(equalTo(registrationDTO.getCountry())));
        assertThat(capturedEntity.getRegion(), is(equalTo(registrationDTO.getRegion())));
        verify(registrationRepository, times(1)).findById(id);
        verify(registrationRepository, times(1)).saveAndFlush(any(ActualRegistrationEntity.class));
    }

    @Test
    void testUpdateActualRegistrationWithId_whenRepositoryReturnEmptyValue() {
        //Arrange
        Long notId = 123L;
        ActualRegistrationDTO registrationDTO = new ActualRegistrationDTO();
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
        Exception exception = assertThrows(NotFoundException.class, () -> registrationService.updateActualRegistration(registrationDTO, notId));
        assertEquals("No actual registration found with the given id", exception.getMessage());
        verify(registrationRepository, times(1)).findById(notId);
        verify(registrationRepository, never()).saveAndFlush(any());
    }

    @Test
    void testDeleteActualRegistrationWithId_whenRepositoryReturnObjectById_and_whenRepositoryDeleteById() {
        //Arrange
        Long id = 1L;
        ActualRegistrationEntity existingEntity = new ActualRegistrationEntity();
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
        registrationService.deleteActualRegistration(id);
        //Assert
        assertNotEquals(0, id);
        assertEquals(id, existingEntity.getId());
        verify(registrationRepository, times(1)).findById(id);
        verify(registrationRepository, times(1)).deleteById(id);
    }

    @Test
    void testDeleteActualRegistrationWithId_whenRepositoryReturnEmptyValue() {
        //Arrange
        Long idNot = 555L;
        when(registrationRepository.findById(idNot)).thenReturn(Optional.empty());
        //Act&Assert
        Exception exception = assertThrows(NotFoundException.class, () -> registrationService.deleteActualRegistration(idNot));
        assertEquals("No actual registration found with the given id", exception.getMessage());
        verify(registrationRepository, times(1)).findById(idNot);
        verify(registrationRepository, never()).deleteById(any());
    }

    @Test
    void testGetAllActualRegistration_whenRepositoryIsNull() {
        //Arrange
        ActualRegistrationServiceImpl registrationService = new ActualRegistrationServiceImpl(null);
        //Act&Assert
        Exception exception = assertThrows(NotFoundException.class, registrationService::getALLActualRegistration);
        assertEquals("ActualRegistrationRepository is null", exception.getMessage());
    }

    @Test
    void testGetAllRegistration_whenRepositoryReturnsEmptyList() {
        //Arrange
        List<ActualRegistrationEntity> testData = new ArrayList<>();
        when(registrationRepository.findAll()).thenReturn(testData);
        //Act&Assert
        Exception exception = assertThrows(NotFoundException.class, () -> registrationService.getALLActualRegistration());
        assertEquals("No  Actual Registrations found", exception.getMessage());
        verify(registrationRepository).findAll();
    }

    @Test
    void testGetAllRegistration_whenRepositoryReturnsNotEmptyList() {
        //Arrange
        ActualRegistrationEntity existingEntity1 = new ActualRegistrationEntity();
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
        ActualRegistrationEntity existingEntity2 = new ActualRegistrationEntity();
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
        List<ActualRegistrationEntity> testData = new ArrayList<>();
        testData.add(existingEntity1);
        testData.add(existingEntity2);
        when(registrationRepository.findAll()).thenReturn(testData);
        //Act
        List<ActualRegistrationDTO> registrationDTOs = testData
                .stream()
                .map(MultiEntityMapper.MAPPER::toActualRegistrationDTO)
                .collect(Collectors.toList());
        //Assert
        assertEquals(2, registrationDTOs.size());
        assertNotNull(registrationDTOs);
        assertEquals(registrationDTOs, registrationService.getALLActualRegistration());
        verify(registrationRepository, times(1)).findAll();
    }
}