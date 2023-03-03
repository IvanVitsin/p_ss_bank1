package com.bank.antifraud.service;
import com.bank.antifraud.entity.PhoneTransferEntity;
import com.bank.antifraud.repository.PhoneTransferRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class PhoneTransferServiceTest {


    @InjectMocks
    private PhoneTransferService phoneTransferService;

    @Mock
    private PhoneTransferRepository phoneTransferRepository;

    @Test
    public void returnAllAccountsIfFound() {
        List<PhoneTransferEntity> phone = getPhones();
        Mockito.when(phoneTransferRepository.findAll()).thenReturn(phone);
        phoneTransferService.getAllAccounts();
    }


    @Test
    public void whenGivenId_shouldDeleteTransaction_ifFound(){
        PhoneTransferEntity phone = getPhones().get(0);
        Mockito.when(phoneTransferRepository.findById(phone.getId())).thenReturn(Optional.of(phone));
        phoneTransferService.deleteAccountTransfer(phone.getId());
        verify(phoneTransferRepository).deleteById(phone.getId());
    }

    @Test
    public void getAccountTransferByIdTest() {
        PhoneTransferEntity phone = getPhones().get(0);
        Mockito.when(phoneTransferRepository.findById(phone.getId())).thenReturn(Optional.of(phone));
        phoneTransferService.findAccountTransferById(phone.getId());
        verify(phoneTransferRepository).findById(phone.getId());

    }



    private List<PhoneTransferEntity> getPhones() {
        PhoneTransferEntity accountDTO = new PhoneTransferEntity();

        accountDTO.setPhoneTransferId(1L);
        accountDTO.setId(1L);
        accountDTO.setBlocked(false);
        accountDTO.setSuspicious(true);
        accountDTO.setBlockedReason("it's ok");
        accountDTO.setSuspiciousReason("dyrachok");
        return List.of(accountDTO);
    }


}
