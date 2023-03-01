package com.bank.profile.service;

import com.bank.profile.dto.PassportDTO;
import com.bank.profile.dto.ProfileDTO;
import com.bank.profile.dto.RegistrationDTO;
import com.bank.profile.entity.ActualRegistrationEntity;
import com.bank.profile.entity.PassportEntity;
import com.bank.profile.entity.ProfileEntity;
import com.bank.profile.entity.RegistrationEntity;
import com.bank.profile.mapper.MultiEntityMapper;
import com.bank.profile.repository.ActualRegisRepository;
import com.bank.profile.repository.PassportRepository;
import com.bank.profile.repository.ProfileRepository;
import com.bank.profile.util.exeptions.NotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.*;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.hamcrest.MatcherAssert.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProfileServiceImplTest {
    @Mock
    private ProfileRepository profileRepository;
    @Mock
    private PassportRepository passportRepository;
    @Mock
    private ActualRegisRepository actualRegisRepository;
    @InjectMocks
    private ProfileServiceImpl profileService;

    @Test
    void testSaveNewProfile_whenRepositoryProfileReturnSavedObject_if_RepositoryRegistrationANDPassportReturnObjectById() {
        //Arrange
        ProfileDTO profileDTO = new ProfileDTO(123L, "email", "Vr", 121L, 1580L);
        Long idPassport = 5L;
        Long idRegis = 6L;
        PassportEntity passportEntity = new PassportEntity();
        passportEntity.setId(5L);
        when(passportRepository.findById(idPassport)).thenReturn(Optional.of(passportEntity));
        ActualRegistrationEntity actualRegistrationEntity = new ActualRegistrationEntity();
        actualRegistrationEntity.setId(6L);
        when(actualRegisRepository.findById(idRegis)).thenReturn(Optional.of(actualRegistrationEntity));
        ProfileEntity profileEntity = MultiEntityMapper.MAPPER.toProfile(profileDTO);
        when(passportRepository.getReferenceById(idPassport)).thenReturn(passportEntity);
        profileEntity.setPassportEntity(passportEntity);
        when(actualRegisRepository.getReferenceById(idRegis)).thenReturn(actualRegistrationEntity);
        profileEntity.setActualRegistration(actualRegistrationEntity);
        when(profileRepository.save(any(ProfileEntity.class))).thenReturn(profileEntity);
        //Act
        ProfileDTO savedProfile = profileService.saveProfile(profileDTO, idPassport, idRegis);
        //Assert
        verify(profileRepository, times(1)).save(any(ProfileEntity.class));
        assertNotNull(passportEntity.getId());
        assertEquals(passportEntity, profileEntity.getPassportEntity());
        assertEquals(actualRegistrationEntity, profileEntity.getActualRegistration());
        assertEquals(profileDTO.getEmail(), profileEntity.getEmail());
        assertEquals(profileEntity.getSnils(), savedProfile.getSnils());
        assertEquals(savedProfile.getSnils(), profileDTO.getSnils());
        assertEquals(savedProfile.getEmail(), profileDTO.getEmail());
        assertEquals(savedProfile.getInn(), profileDTO.getInn());
        assertEquals(savedProfile.getPhoneNumber(), profileDTO.getPhoneNumber());
        verify(passportRepository, times(1)).findById(passportEntity.getId());
        verify(passportRepository, times(1)).getReferenceById(5L);
        verify(actualRegisRepository, times(1)).findById(actualRegistrationEntity.getId());
        verify(actualRegisRepository, times(1)).getReferenceById(6L);
    }

    @Test
    void testSaveNewProfile_neverRepositoryProfileReturnSavedObject_if_RepositoryActualRegisReturnEmptyValue() {
        //Arrange
        ProfileDTO profileDTO = new ProfileDTO();
        Long idPassport = 5L;
        PassportEntity passportEntity = new PassportEntity();
        passportEntity.setId(5L);
        when(passportRepository.findById(idPassport)).thenReturn(Optional.of(passportEntity));
        Long idNot = 555L;
        when(actualRegisRepository.findById(idNot)).thenReturn(Optional.empty());
        //Act&Assert
        Exception exception = assertThrows(NotFoundException.class, () -> profileService.saveProfile(profileDTO, idPassport, idNot));
        assertEquals("No  actual registration found with the given id", exception.getMessage());
        verify(actualRegisRepository, times(1)).findById(idNot);
        verify(actualRegisRepository, never()).getReferenceById(idNot);
        verify(passportRepository, times(1)).findById(idPassport);
        verify(profileRepository, never()).save(any());
    }

    @Test
    void testSaveNewProfile_neverRepositoryProfileNotReturnSavedObject_when_RepositoryPassportReturnEmptyValue() {
        //Arrange
        ProfileDTO profileDTO = new ProfileDTO();
        Long idNot = 555L;
        when(passportRepository.findById(idNot)).thenReturn(Optional.empty());
        //Act&Assert
        Exception exception = assertThrows(NotFoundException.class, () -> profileService.saveProfile(profileDTO, idNot, 5L));
        assertEquals("No passport found with the given id", exception.getMessage());
        verify(passportRepository, times(1)).findById(idNot);
        verify(passportRepository, never()).getReferenceById(idNot);
        verify(profileRepository, never()).save(any());
    }

    @Test
    void testSaveProfileNoActualRegistration_whenRepositoryProfileReturnSavedObject_if_RepositoryPassportReturnObjectById() {
        //Arrange
        ProfileDTO profileDTO = new ProfileDTO(123L, "email", "Vr", 121L, 1580L);
        Long idPassport = 5L;
        PassportEntity passportEntity = new PassportEntity();
        passportEntity.setId(5L);
        when(passportRepository.findById(idPassport)).thenReturn(Optional.of(passportEntity));
        ProfileEntity profileEntity = MultiEntityMapper.MAPPER.toProfile(profileDTO);
        when(passportRepository.getReferenceById(idPassport)).thenReturn(passportEntity);
        profileEntity.setPassportEntity(passportEntity);
        when(profileRepository.saveAndFlush(any(ProfileEntity.class))).thenReturn(profileEntity);
        //Act
        ProfileDTO savedProfile = profileService.saveProfileNoActualRegistration(profileDTO, idPassport);
        //Assert
        verify(profileRepository, times(1)).saveAndFlush(any(ProfileEntity.class));
        assertNotNull(passportEntity.getId());
        assertEquals(passportEntity, profileEntity.getPassportEntity());
        assertEquals(profileDTO.getEmail(), profileEntity.getEmail());
        assertEquals(profileEntity.getSnils(), savedProfile.getSnils());
        assertEquals(savedProfile.getSnils(), profileDTO.getSnils());
        assertEquals(savedProfile.getEmail(), profileDTO.getEmail());
        assertEquals(savedProfile.getInn(), profileDTO.getInn());
        assertEquals(savedProfile.getPhoneNumber(), profileDTO.getPhoneNumber());
        verify(passportRepository, times(1)).findById(passportEntity.getId());
        verify(passportRepository, times(1)).getReferenceById(5L);
    }

    @Test
    void testSaveProfileNoActualRegistration_neverRepositoryProfileNotReturnSavedObject_When_RepositoryPassportReturnEmptyValue() {
        //Arrange
        ProfileDTO profileDTO = new ProfileDTO();
        Long idNot = 555L;
        when(passportRepository.findById(idNot)).thenReturn(Optional.empty());
        //Act&Assert
        Exception exception = assertThrows(NotFoundException.class, () -> profileService.saveProfileNoActualRegistration(profileDTO, idNot));
        assertEquals("No passport found with the given id", exception.getMessage());
        verify(passportRepository, times(1)).findById(idNot);
        verify(passportRepository, never()).getReferenceById(idNot);
        verify(profileRepository, never()).save(any());
    }

    @Test
    void testUpdateProfileWithId_whenRepositoryReturnObjectById() {
        //Arrange
        ProfileDTO profileDTO = new ProfileDTO(1123L, "email1", "Vr1", 1211L, 11580L);
        Long id = 5L;
        PassportEntity passportEntity = new PassportEntity();
        passportEntity.setId(5L);
        ActualRegistrationEntity actualRegistrationEntity = new ActualRegistrationEntity();
        actualRegistrationEntity.setId(6L);
        ProfileEntity profileEntity = new ProfileEntity(5L, 123L, "email",
                "Vr", 121L, 1580L, passportEntity, actualRegistrationEntity);
        when(profileRepository.findById(id)).thenReturn(Optional.of(profileEntity));
        when(profileRepository.saveAndFlush(any(ProfileEntity.class))).thenReturn(profileEntity);
        //Act
        ProfileDTO result = profileService.update(profileDTO, id);
        //Assert
        assertNotNull(result);
        assertEquals(profileEntity.getPassportEntity(), passportEntity);
        assertThat(profileEntity.getEmail(), is(equalTo("email1")));
        assertEquals(profileEntity.getActualRegistration(), actualRegistrationEntity);
        assertEquals(profileEntity.getInn(), result.getInn());
        assertEquals(profileEntity.getPhoneNumber(), result.getPhoneNumber());
        assertEquals(profileEntity.getNameOnCard(), profileDTO.getNameOnCard());
        verify(profileRepository, times(1)).findById(id);
        verify(profileRepository, times(1)).saveAndFlush(any(ProfileEntity.class));
    }

    @Test
    void testUpdateProfileWithId_whenRepositoryReturnEmptyValue() {
        //Arrange
        ProfileDTO profileDTO = new ProfileDTO();
        Long idNot = 555L;
        when(profileRepository.findById(idNot)).thenReturn(Optional.empty());
        //Act&Assert
        Exception exception = assertThrows(NotFoundException.class, () -> profileService.update(profileDTO, idNot));
        assertEquals("No profile found with the given id", exception.getMessage());
        verify(profileRepository, times(1)).findById(idNot);
        verify(profileRepository, never()).saveAndFlush(any());
    }

    @Test
    void testDeleteProfileWithId_whenRepositoryReturnObjectById_and_whenRepositoryDeleteById() {
        //Arrange
        Long id = 5L;
        PassportEntity passportEntity = new PassportEntity();
        passportEntity.setId(5L);
        ActualRegistrationEntity actualRegistrationEntity = new ActualRegistrationEntity();
        actualRegistrationEntity.setId(6L);
        ProfileEntity profileEntity = new ProfileEntity(5L, 123L, "email",
                "Vr", 121L, 1580L, passportEntity, actualRegistrationEntity);
        when(profileRepository.findById(id)).thenReturn(Optional.of(profileEntity));
        doNothing().when(profileRepository).deleteById(id);
        //Act
        profileService.delete(id);
        //Assert
        assertNotEquals(0, id);
        assertEquals(id, profileEntity.getId());
        verify(profileRepository, times(1)).findById(id);
        verify(profileRepository, times(1)).deleteById(id);
    }

    @Test
    void testDeleteProfileWithId_whenRepositoryReturnEmptyValue() {
        //Arrange
        Long idNot = 555L;
        when(profileRepository.findById(idNot)).thenReturn(Optional.empty());
        //Act&Assert
        Exception exception = assertThrows(NotFoundException.class, () -> profileService.delete(idNot));
        assertEquals("No profile found with the given id", exception.getMessage());
        verify(profileRepository, times(1)).findById(idNot);
        verify(profileRepository, never()).deleteById(any());
    }

    @Test
    void testGetProfilePassport_whenRepositoryReturnObjectById() {
        //Arrange
        Long idProfile = 5L;
        RegistrationEntity registrationEntity1 = new RegistrationEntity();
        PassportEntity passportEntity1 = new PassportEntity(1L, 123456, 63L, "De",
                "Cre", "Mre", "Mre", LocalDate.of(1990, 1, 12),
                "Nre", "Dre", LocalDate.of(2020, 1, 1),
                133, LocalDate.of(2030, 1, 1), registrationEntity1);
        ProfileEntity profileEntity = new ProfileEntity(5L, 123L, "email",
                "Vr", 121L, 1580L, passportEntity1, null);
        when(profileRepository.findById(idProfile)).thenReturn(Optional.of(profileEntity));
        //Act
        PassportDTO result = profileService.getProfilePassport(idProfile);
        //Assert
        verify(profileRepository, times(1)).findById(idProfile);
        assertNotNull(result);
        assertEquals(result.getSeries(), passportEntity1.getSeries());
        assertEquals(result.getDateOfIssue(), passportEntity1.getDateOfIssue());
        assertEquals(result.getNumber(), passportEntity1.getNumber());
    }

    @Test
    void testGetProfilePassport_whenRepositoryReturnEmptyValue() {
        //Arrange
        Long idNot = 555L;
        when(profileRepository.findById(idNot)).thenReturn(Optional.empty());
        //Act&Assert
        NotFoundException exception = assertThrows(NotFoundException.class, () -> profileService.getProfilePassport(idNot));
        assertEquals("No profile found with the given id", exception.getMessage());
        verify(profileRepository, times(1)).findById(idNot);
    }

    @Test
    void testGetPassportRegistration__whenRepositoryReturnObjectById() {
        //Arrange
        Long idProfile = 5L;
        RegistrationEntity registrationEntity1 = new RegistrationEntity(1L, "f", "r",
                "y", "q", "ff", "l", "q", "ff", "l",
                55L);
        PassportEntity passportEntity1 = new PassportEntity(1L, 123456, 63L, "De",
                "Cre", "Mre", "Mre", LocalDate.of(1990, 1, 12),
                "Nre", "Dre", LocalDate.of(2020, 1, 1),
                133, LocalDate.of(2030, 1, 1), registrationEntity1);
        ProfileEntity profileEntity = new ProfileEntity(5L, 123L, "email",
                "Vr", 121L, 1580L, passportEntity1, null);
        when(profileRepository.findById(idProfile)).thenReturn(Optional.of(profileEntity));
        //Act
        RegistrationDTO result = profileService.getPassportRegistration(idProfile);
        //Assert
        verify(profileRepository, times(1)).findById(idProfile);
        assertNotNull(result);
        assertEquals(result.getIndex(), registrationEntity1.getIndex());
        assertEquals(result.getCity(), registrationEntity1.getCity());
        assertEquals(result.getLocality(), registrationEntity1.getLocality());
    }

    @Test
    void testGetPassportRegistration_whenRepositoryReturnEmptyValue() {
        //Arrange
        Long idNot = 555L;
        when(profileRepository.findById(idNot)).thenReturn(Optional.empty());
        //Act&Assert
        Exception exception = assertThrows(NotFoundException.class, () -> profileService.getPassportRegistration(idNot));
        assertEquals("No profile found with the given id", exception.getMessage());
        verify(profileRepository, times(1)).findById(idNot);
    }
}