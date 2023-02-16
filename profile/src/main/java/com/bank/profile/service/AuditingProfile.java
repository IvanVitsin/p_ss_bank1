package com.bank.profile.service;

import com.bank.profile.entity.AuditEntity;
import com.bank.profile.util.BeanUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;
import javax.persistence.PrePersist;
import java.time.LocalDateTime;


/**
 * Этот класс представляет AuditingProfile  прослушивает класс ProfileEntity.
 * <p>
 * Цель этого класса — обрабатывать события сохраняемости
 * (PrePersist, PostPersist, PostUpdate) для  сохранения данных в таблицу аудита.
 * Логирование происходит с помощью ламбока аннотацией @Slf4j с выводом в консоль.
 * Поля:
 * originalState — это строковая переменная, используемая
 * для хранения исходного состояния проверяемого объекта в виде Json формата.
 * now - это переменная времени сохраняет значение перед сохранением
 *
 * @author Sazonov Vladimir
 * @version 1.0
 * @since 12.02.2023
 */
@Slf4j
@Component
@Transactional
public class AuditingProfile {
    String originalState;
    LocalDateTime now;

    /**
     * Этот метод выполняется перед событием сохранения.
     * Он устанавливает исходное состояние проверяемого объекта в строку JSON.
     *
     * @param auditable — сохраняемый проверяемый объект.
     */
    @PrePersist
    public void prePersist(AuditableMy auditable) {
        this.originalState = toJson(auditable);
        this.now = LocalDateTime.now();
        log.info("Данные о entity перед сохранением добавлены в поле в виде json");
    }

    /**
     * Этот метод выполняется после события prePersist.
     * Он создает объект аудита и устанавливает его атрибуты на основе сохраняемого объекта аудита.
     * Созданный объект аудита затем сохраняется в базе данных.
     *
     * @param auditable — сохраненный проверяемый объект.
     */
    @PostPersist
    public void postPersist(AuditableMy auditable) {
        EntityManager entityManager = BeanUtil.getBean(EntityManager.class);
        AuditEntity auditEntity = new AuditEntity();
        auditEntity.setEntityType(auditable.getClass().getSimpleName());
        auditEntity.setOperationType("Persist");
        auditEntity.setCreatedBy("User");
        auditEntity.setCreatedAt(LocalDateTime.now());
        auditEntity.setModifiedBy("User");
        auditEntity.setModifiedAt(LocalDateTime.now());
        auditEntity.setEntityJson(toJson(auditable));
        auditEntity.setNewEntityJson(toJson(auditable));
        entityManager.persist(auditEntity);
        log.info("Данные о entity добавлены в таблицу аудит");
    }

    /**
     * Этот метод выполняется после события обновления.
     * Он создает объект аудита и устанавливает его атрибуты на основе объекта аудита, который был обновлен.
     * Созданный объект аудита затем сохраняется в базе данных.
     *
     * @param auditable — объект аудита, который был обновлен.
     */
    @PostUpdate
    public void postUpdate(AuditableMy auditable) {
        EntityManager entityManager = BeanUtil.getBean(EntityManager.class);
        AuditEntity auditEntity = new AuditEntity();
        auditEntity.setEntityType(auditable.getClass().getSimpleName());
        auditEntity.setOperationType("Update");
        auditEntity.setCreatedBy("User");
        auditEntity.setModifiedBy("User");
        auditEntity.setCreatedAt(now);
        auditEntity.setModifiedAt(LocalDateTime.now());
        auditEntity.setEntityJson(originalState);
        auditEntity.setNewEntityJson(toJson(auditable));
        entityManager.persist(auditEntity);
        log.info("Данные о entity обновлены в таблице аудит");
    }

    /**
     * Этот метод преобразует объект в формат JSON.
     *
     * @param object — объект, который нужно преобразовать в формат JSON.
     */
    private String toJson(Object object) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        try {
            log.info("Данные о entity преобразованы в json");
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            log.error("ERROR: entity не преобразован в json");
            throw new RuntimeException(e);
        }
    }
}