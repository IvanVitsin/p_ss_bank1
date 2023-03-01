package com.bank.profile.service;

import static org.mockito.Mockito.*;

import javax.persistence.EntityManager;

import com.bank.profile.entity.AuditEntity;
import com.bank.profile.entity.RegistrationEntity;
import com.bank.profile.util.BeanUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationContext;

@ExtendWith(MockitoExtension.class)
public class AuditingRegistrationTest {
    @Mock
    ApplicationContext context;
    @Mock
    private EntityManager entityManager;
    @InjectMocks
    AuditingRegistration auditingRegistration;
    @Test
    void postUpdate_shouldUpdateAuditEntity() {
        // Arrange
        AuditableMy auditable = new RegistrationEntity();
        BeanUtil.context = context;
        when(context.getBean(EntityManager.class)).thenReturn(entityManager);
        doNothing().when(entityManager).persist(any(AuditEntity.class));
        // Act
        auditingRegistration.postUpdate(auditable);
        // Assert
        verify(context).getBean(EntityManager.class);
        verify(entityManager).persist(any(AuditEntity.class));
    }

    @Test
    void postPersist_shouldPersistAuditEntity() {
        // Arrange
        AuditableMy auditable = new RegistrationEntity();
        BeanUtil.context = context;
        when(context.getBean(EntityManager.class)).thenReturn(entityManager);
        doNothing().when(entityManager).persist(any(AuditEntity.class));
        // Act
        auditingRegistration.postPersist(auditable);
        // Assert
        verify(context).getBean(EntityManager.class);
        verify(entityManager).persist(any(AuditEntity.class));
    }

    @Test
    void prePersist_shouldPrePersistAuditEntity() {
        // Arrange
        AuditableMy auditable = new RegistrationEntity();
        // Act
        auditingRegistration.prePersist(auditable);
        // Assert
        verify(context, never()).getBean(EntityManager.class);
        verify(entityManager, never()).persist(any(AuditEntity.class));
    }
}