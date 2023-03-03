package com.bank.antifraud.service.audit;

import com.bank.antifraud.entity.AccountTransferEntity;
import com.bank.antifraud.entity.AuditEntity;
import com.bank.antifraud.util.BeanInit;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationContext;

import javax.persistence.EntityManager;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class AuditingPhoneTest {

    @Mock
    private ApplicationContext context;

    @Mock
    private EntityManager entityManager;

    @InjectMocks
    AuditingPhone auditingPhone;


    @Test
    void prePersist() {

        // Arrange
        AuditAbstract auditable = new AccountTransferEntity();
        // Act
        auditingPhone.prePersist(auditable);
        // Assert
        verify(context, never()).getBean(EntityManager.class);
        verify(entityManager, never()).persist(any(AuditEntity.class));

    }

    @Test
    void postPersist() {
        // Arrange
        AuditAbstract auditable = new AccountTransferEntity();
        BeanInit.applicationContext = context;
        when(context.getBean(EntityManager.class)).thenReturn(entityManager);
        doNothing().when(entityManager).persist(any(AuditEntity.class));
        // Act
        auditingPhone.postPersist(auditable);
        // Assert
        verify(context).getBean(EntityManager.class);
        verify(entityManager).persist(any(AuditEntity.class));
    }

    @Test
    void postUpdate() {

        // Arrange
        AuditAbstract auditable = new AccountTransferEntity();
        BeanInit.applicationContext = context;
        when(context.getBean(EntityManager.class)).thenReturn(entityManager);
        doNothing().when(entityManager).persist(any(AuditEntity.class));
        // Act
        auditingPhone.postUpdate(auditable);
        // Assert
        verify(context).getBean(EntityManager.class);
        verify(entityManager).persist(any(AuditEntity.class));
    }
}