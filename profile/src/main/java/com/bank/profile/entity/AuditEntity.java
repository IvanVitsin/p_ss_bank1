package com.bank.profile.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Это класс  именем AuditEntity, представляющим сущность
 * в таблице базы данных auditс именем схемы profile.
 * Класс снабжен аннотацией, @Entityчтобы пометить его как объект JPA и @Table указать имя и
 * схему таблицы базы данных.
 * <p>
 * Класс имеет следующие поля:
 * <p>
 * id: значение, которое является первичным ключом для объекта. Он снабжен аннотацией, @Idчтобы пометить
 * его как первичный ключ и @GeneratedValueуказать, что он генерируется базой данных с использованием IDENTITYстратегии.
 * <p>
 * entityType: строковое значение, представляющее тип проверенного объекта.
 * <p>
 * operationType: строковое значение, представляющее тип операции, выполненной над объектом,
 * <p>
 * createdBy: строковое значение, представляющее имя пользователя, создавшего объект.
 * <p>
 * modifiedBy: строковое значение, представляющее имя пользователя, который  изменил объект.
 * <p>
 * createdAt: значение LocalDateTime, представляющее дату и время создания объекта.
 * <p>
 * modifiedAt: значение LocalDateTime, представляющее дату и время последнего изменения объекта.
 * <p>
 * newEntityJson: строковое значение, представляющее данные JSON при создании сущности.
 * <p>
 * entityJson: строковое значение, представляющее данные JSON после изменения объекта.
 *
 * @author Sazonov Vladimir
 * @version 1.0
 * @since 12.02.2023
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "audit", schema = "profile")
public class AuditEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String entityType;
    private String operationType;
    private String createdBy;
    @Column(nullable = false)
    private String modifiedBy;
    private LocalDateTime createdAt;
    @Column(nullable = false)
    private LocalDateTime modifiedAt;
    @Column(nullable = false, columnDefinition = "TEXT")
    private String newEntityJson;
    @Column(columnDefinition = "TEXT")
    private String entityJson;
}
