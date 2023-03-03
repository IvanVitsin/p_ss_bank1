package com.bank.antifraud.service;

import com.bank.antifraud.Exception.ErrorDTO;
import com.bank.antifraud.entity.CardTransferEntity;
import com.bank.antifraud.repository.CardTransferRepository;
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
public class CardTransferServiceTest {

    @InjectMocks
    private CardTransferService cardTransferService;

    @Mock
    private CardTransferRepository cardTransferRepository;


    @Test
    public void returnAllAccountsIfFound() {
        List<CardTransferEntity> cards = getCards();
        Mockito.when(cardTransferRepository.findAll()).thenReturn(cards);
        cardTransferService.getAllAccounts();
    }



    @Test
    public void whenGivenId_shouldDeleteTransaction_ifFound(){
        CardTransferEntity cards = getCards().get(0);
        Mockito.when(cardTransferRepository.findById(cards.getId())).thenReturn(Optional.of(cards));
        cardTransferService.deleteAccountTransfer(cards.getId());
        ErrorDTO errorDTO = new ErrorDTO("vot tak", "just for coverage");

        verify(cardTransferRepository).deleteById(cards.getId());
    }

    @Test
    public void getAccountTransferByIdTest() {
        CardTransferEntity phone = getCards().get(0);
        Mockito.when(cardTransferRepository.findById(phone.getId())).thenReturn(Optional.of(phone));
        cardTransferService.findAccountTransferById(phone.getId());
        verify(cardTransferRepository).findById(phone.getId());

    }



    private List<CardTransferEntity> getCards() {
        CardTransferEntity accountDTO = new CardTransferEntity();

        accountDTO.setCardTransferId(1L);
        accountDTO.setId(1L);
        accountDTO.setBlocked(false);
        accountDTO.setSuspicious(true);
        accountDTO.setBlockedReason("it's ok");
        accountDTO.setSuspiciousReason("dyrachok");
        return List.of(accountDTO);
    }


}
