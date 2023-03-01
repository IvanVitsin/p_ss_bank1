package com.bank.profile.service;

import com.bank.profile.dto.AccountDetailsDTO;
import com.bank.profile.entity.AccountDetailsEntity;
import com.bank.profile.entity.ProfileEntity;
import com.bank.profile.mapper.MultiEntityMapper;
import com.bank.profile.repository.AccountDetailsRepository;
import com.bank.profile.repository.ProfileRepository;
import com.bank.profile.util.exeptions.NotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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
public class AccountDetailsServiceImplTest {
    @Mock
    private AccountDetailsRepository detailsRepository;
    @Mock
    private ProfileRepository profileRepository;
    @InjectMocks
    private AccountDetailsServiceImpl detailsService;

    @Test
    void testSaveNewDetails_whenRepositoryDetailsReturnSavedObject_if_RepositoryProfileReturnObjectById() {
        //Arrange
        Long profileId = 1L;
        ProfileEntity profileEntity = new ProfileEntity();
        profileEntity.setId(profileId);
        AccountDetailsDTO accountDetailsDTO = new AccountDetailsDTO(9L);
        AccountDetailsEntity accountDetails = MultiEntityMapper.MAPPER.toAccountDetails(accountDetailsDTO);
        when(profileRepository.getReferenceById(1L)).thenReturn(profileEntity);
        accountDetails.setProfileEntity(profileEntity);
        when(detailsRepository.save(any(AccountDetailsEntity.class))).thenReturn(accountDetails);
        //Act
        AccountDetailsDTO detailsDTO = detailsService.saveAccountDetails(accountDetailsDTO, 1L);
        //Assert
        verify(detailsRepository, times(1)).save(any(AccountDetailsEntity.class));
        assertNotNull(detailsDTO);
        assertEquals(accountDetails.getProfileEntity(), profileEntity);
        assertEquals(detailsDTO.getAccountId(), accountDetailsDTO.getAccountId());
        verify(profileRepository, times(1)).getReferenceById(1L);
    }

    @Test
    void testUpdateDetailsWithId_whenRepositoryReturnObjectById() {
        //Arrange
        ProfileEntity profileEntity = new ProfileEntity();
        profileEntity.setId(5L);
        Long id = 1L;
        AccountDetailsDTO accountDetailsDTO = new AccountDetailsDTO(12L);
        AccountDetailsEntity accountDetailsEntity = new AccountDetailsEntity(1L, 9L, profileEntity);
        when(detailsRepository.findById(id)).thenReturn(Optional.of(accountDetailsEntity));
        //Act
        AccountDetailsDTO result = detailsService.updateAccountDetails(accountDetailsDTO, id);
        //Assert
        assertNotNull(result);
        assertThat(5L, is(equalTo(accountDetailsEntity.getProfileEntity().getId())));
        assertEquals(result.getAccountId(), accountDetailsDTO.getAccountId());
        verify(detailsRepository, times(1)).findById(id);
    }

    @Test
    void testUpdateDetailsWithId_whenRepositoryReturnEmptyValue() {
        //Arrange
        AccountDetailsDTO accountDetailsDTO = new AccountDetailsDTO();
        Long idNot = 555L;
        when(detailsRepository.findById(idNot)).thenReturn(Optional.empty());
        //Act&Assert
        Exception exception = assertThrows(NotFoundException.class, () -> detailsService.updateAccountDetails(accountDetailsDTO, idNot));
        assertEquals("No account Details found with the given id", exception.getMessage());
        verify(detailsRepository, times(1)).findById(idNot);

    }

    @Test
    void testDeleteDetailsWithId_whenRepositoryReturnObjectById_and_whenRepositoryDeleteById() {
        //Arrange
        ProfileEntity profileEntity = new ProfileEntity();
        profileEntity.setId(5L);
        Long id = 1L;
        AccountDetailsEntity accountDetailsEntity = new AccountDetailsEntity(1L, 9L, profileEntity);

        when(detailsRepository.findById(id)).thenReturn(Optional.of(accountDetailsEntity));
        doNothing().when(detailsRepository).deleteById(id);
        //Act
        detailsService.deleteAccountDetails(id);
        //Assert
        assertNotEquals(null, id);
        assertEquals(id, accountDetailsEntity.getId());
        verify(detailsRepository, times(1)).findById(id);
        verify(detailsRepository, times(1)).deleteById(id);
    }

    @Test
    void testDeletePassportWithId_whenRepositoryReturnEmptyValue() {
        //Arrange
        Long idNot = 555L;
        when(detailsRepository.findById(idNot)).thenReturn(Optional.empty());
        //Act&Assert
        Exception exception = assertThrows(NotFoundException.class, () -> detailsService.deleteAccountDetails(idNot));
        assertEquals("No account Details found with the given id", exception.getMessage());
        verify(detailsRepository, times(1)).findById(idNot);
        verify(detailsRepository, never()).deleteById(any());
    }

    @Test
    void testGetAllDetails_whenRepositoryIsNull() {
        //Arrange
        AccountDetailsServiceImpl detailsService1 = new AccountDetailsServiceImpl(null, null);
        //Act&Assert
        Exception exception = assertThrows(NotFoundException.class, detailsService1::getALLAccountDetails);
        assertEquals("accountDetailsRepository is null", exception.getMessage());
    }

    @Test
    void testGetAllDetails_whenRepositoryReturnsEmptyList() {
        //Arrange
        List<AccountDetailsEntity> testData = new ArrayList<>();
        when(detailsRepository.findAll()).thenReturn(testData);
        //Act&Assert
        Exception exception = assertThrows(NotFoundException.class, () -> detailsService.getALLAccountDetails());
        assertEquals("No account details found", exception.getMessage());
        verify(detailsRepository).findAll();
    }

    @Test
    void testGetAllDetails_whenRepositoryReturnsNotEmptyList() {
        //Arrange
        ProfileEntity profileEntity1 = new ProfileEntity();
        ProfileEntity profileEntity2 = new ProfileEntity();
        AccountDetailsEntity accountDetailsEntity1 = new AccountDetailsEntity(1L, 9L, profileEntity1);
        AccountDetailsEntity accountDetailsEntity2 = new AccountDetailsEntity(2L, 98L, profileEntity2);
        List<AccountDetailsEntity> testData = new ArrayList<>();
        testData.add(accountDetailsEntity1);
        testData.add(accountDetailsEntity2);
        when(detailsRepository.findAll()).thenReturn(testData);
        //Act
        List<AccountDetailsDTO> detailsDTOS = testData
                .stream()
                .map(MultiEntityMapper.MAPPER::toAccountDetailsDTO)
                .collect(Collectors.toList());
        //Assert
        assertEquals(2, detailsDTOS.size());
        assertNotNull(detailsDTOS);
        assertEquals(detailsDTOS, detailsService.getALLAccountDetails());
        verify(detailsRepository, times(1)).findAll();
    }
}