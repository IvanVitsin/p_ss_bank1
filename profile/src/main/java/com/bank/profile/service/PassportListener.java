/*
package com.bank.profile.service;

import com.bank.profile.entity.AuditEntity;
import com.bank.profile.util.BeanUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import javax.persistence.*;
import java.time.LocalDateTime;

public class PassportListener {
    @PrePersist
    public void prePersist(Object o) {

    }

    @PreUpdate
    public void preUpdate(Object o) {

    }

    @PreRemove
    public void preRemove(Object o) {

    }

    @PostLoad
    public void postLoad(Object o) {

    }

    @PostRemove
    public void postRemove(Object o) {

    }

    @PostUpdate
    public void postUpdate(Object o) {

    }

    @PostPersist
    public void postPersist(Object o) {
        EntityManager entityManager = BeanUtil.getBean(EntityManager.class);
        AuditEntity auditEntity = new AuditEntity();
        auditEntity.setEntityType(o.getClass().getSimpleName());
        auditEntity.setOperationType("Persist");
        auditEntity.setCreatedBy("User");
        auditEntity.setCreatedAt(LocalDateTime.now());
        auditEntity.setModifiedBy("User");
        auditEntity.setModifiedAt(LocalDateTime.now());
        auditEntity.setEntityJson(toJson(o));
        auditEntity.setNewEntityJson(toJson(o));
        entityManager.persist(auditEntity);
       // log.info("Данные о entity добавлены в таблицу аудит");
    }
    private String toJson(Object object) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        try {
           // log.info("Данные о entity преобразованы в json");
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
          //  log.error("ERROR: entity не преобразован в json");
            throw new RuntimeException(e);
        }
    }
}
*/
