package com.bank.antifraud.service;
import com.bank.antifraud.entity.AccountTransferEntity;
import com.bank.antifraud.repository.AccountTransferRepository;
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
public class AccountTransferServiceTest {

    @InjectMocks
    private AccountTransferService accountTransferService;


    @Mock
    private AccountTransferRepository accountTransferRepository;


    @Test
    public void returnAllAccountsIfFound() {
        List<AccountTransferEntity> account = getAccounts();
        Mockito.when(accountTransferRepository.findAll()).thenReturn(account);
        accountTransferService.getAllAccounts();
    }


    @Test
    public void whenGivenId_shouldDeleteTransaction_ifFound(){
        AccountTransferEntity account = getAccounts().get(0);
        Mockito.when(accountTransferRepository.findById(account.getId())).thenReturn(Optional.of(account));
        accountTransferService.deleteAccountTransfer(account.getId());
        verify(accountTransferRepository).deleteById(account.getId());
    }

    @Test
    public void getAccountTransferByIdTest() {
        AccountTransferEntity account = getAccounts().get(0);
        Mockito.when(accountTransferRepository.findById(account.getId())).thenReturn(Optional.of(account));
        accountTransferService.findAccountTransferById(account.getId());
        verify(accountTransferRepository).findById(account.getId());

    }

    @Test
    public void getAllAccounts() {
        List<AccountTransferEntity> accountTransferEntity = getAccounts();
        Mockito.when(accountTransferRepository.findAll()).thenReturn(accountTransferEntity);
        accountTransferService.getAllAccounts();
        verify(accountTransferRepository).findAll();
    }


    private List<AccountTransferEntity> getAccounts() {
        AccountTransferEntity accountDTO = new AccountTransferEntity();

        accountDTO.setAccountTransferId(1L);
        accountDTO.setId(1L);
        accountDTO.setBlocked(false);
        accountDTO.setSuspicious(true);
        accountDTO.setBlockedReason("it's ok");
        accountDTO.setSuspiciousReason("dyrachok");
        return List.of(accountDTO);
    }

}
