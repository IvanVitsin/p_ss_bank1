package com.bank.profile.entity;

import com.bank.profile.service.AuditableMy;
import com.bank.profile.service.AuditingRegistration;
import lombok.*;

import javax.persistence.*;

/**
 * Это класс c именем RegistrationEntity, представляющим сущность в таблице базы данных
 * registration  с именем схемы profile. Класс снабжен аннотацией, @Entity чтобы пометить его как объект
 * JPA, @EntityListeners указать класс слушателя AuditingRegistration и @Tableуказать имя и схему таблицы
 * базы данных.
 * <p>
 * Класс расширяет класс AuditableMy.
 * RegistrationEntity он наследует некоторые общие поля аудита от AuditableMy.
 * <p>
 * Класс имеет следующие поля:
 * <p>
 * id:  значение, которое является первичным ключом для объекта.
 * <p>
 * country: строковое поле, представляющее страну .
 * <p>
 * region: строковое поле, представляющее регион регистрации.
 * <p>
 * city: строковое поле, представляющее город регистрации.
 * <p>
 * district: строковое поле, представляющее район регистрации.
 * <p>
 * locality: строковое поле, представляющее населенный пункт регистрации.
 * <p>
 * street: строковое поле, представляющее улицу регистрации.
 * <p>
 * houseNumber: строковое поле, представляющее номер дома регистрации.
 * <p>
 * houseBlock: строковое поле, представляющее корпус регистрации
 * <p>
 * flatNumber: строковое поле, представляющее  номер квартиры регистрации.
 * <p>
 * index:  поле, представляющее индекс регистрации.
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
@EntityListeners(AuditingRegistration.class)
@Table(name = "registration", schema = "profile")
public class RegistrationEntity extends AuditableMy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String country;
    @Column(nullable = false)
    private String region;
    @Column(nullable = false)
    private String city;
    @Column(nullable = false)
    private String district;
    @Column(nullable = false)
    private String locality;
    @Column(nullable = false)
    private String street;
    @Column(nullable = false)
    private String houseNumber;
    @Column(nullable = false)
    private String houseBlock;
    @Column(nullable = false)
    private String flatNumber;
    private Long index;
}
