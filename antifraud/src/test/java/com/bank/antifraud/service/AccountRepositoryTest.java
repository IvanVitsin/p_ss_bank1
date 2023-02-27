package com.bank.antifraud.service;
import com.bank.antifraud.entity.AccountTransferEntity;
import com.bank.antifraud.repository.AccountTransferRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.util.List;

@DataJpaTest
public class AccountRepositoryTest {

    @Autowired
    private AccountTransferRepository accountTransferRepository;


    //Junit test for   saveAccount
    @Test
    public void saveAccountTest() {
        AccountTransferEntity entity = getAccounts().get(0);
        accountTransferRepository.save(entity);
       // Assertions.assertThat(entity.getId()).isEqualTo(1);
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
