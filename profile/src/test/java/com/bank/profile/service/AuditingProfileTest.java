package com.bank.profile.service;

import static org.mockito.Mockito.*;

import javax.persistence.EntityManager;

import com.bank.profile.entity.AuditEntity;
import com.bank.profile.entity.ProfileEntity;
import com.bank.profile.util.BeanUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationContext;

@ExtendWith(MockitoExtension.class)
class AuditingProfileTest {
    @Mock
    ApplicationContext context;
    @Mock
    private EntityManager entityManager;
    @InjectMocks
    AuditingProfile auditingProfile;

    @Test
    void postUpdate_shouldUpdateAuditEntity() {
        // Arrange
        AuditableMy auditable = new ProfileEntity();
        BeanUtil.context = context;
        when(context.getBean(EntityManager.class)).thenReturn(entityManager);
        doNothing().when(entityManager).persist(any(AuditEntity.class));
        // Act
        auditingProfile.postUpdate(auditable);
        // Assert
        verify(context).getBean(EntityManager.class);
        verify(entityManager).persist(any(AuditEntity.class));
    }

    @Test
    void postPersist_shouldPersistAuditEntity() {
        // Arrange
        AuditableMy auditable = new ProfileEntity();
        BeanUtil.context = context;
        when(context.getBean(EntityManager.class)).thenReturn(entityManager);
        doNothing().when(entityManager).persist(any(AuditEntity.class));
        // Act
        auditingProfile.postPersist(auditable);
        // Assert
        verify(context).getBean(EntityManager.class);
        verify(entityManager).persist(any(AuditEntity.class));
    }
    @Test
    void prePersist_shouldPrePersistAuditEntity() {
        // Arrange
        AuditableMy auditable = new ProfileEntity();
        // Act
        auditingProfile.prePersist(auditable);
        // Assert
        verify(context, never()).getBean(EntityManager.class);
        verify(entityManager, never()).persist(any(AuditEntity.class));
    }
}