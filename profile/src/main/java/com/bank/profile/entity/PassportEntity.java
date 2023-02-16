package com.bank.profile.entity;

import com.bank.profile.service.AuditableMy;
import com.bank.profile.service.AuditingPassport;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * Это с именем PassportEntity. Аннотация @Entity указывает, что этот класс является сущностью.
 * В @EntityListeners аннотации указан класс прослушивателя  AuditingPassport
 * В @Table аннотации указывается имя таблицы базы данных, связанной с этим объектом, а также схема базы.
 * Класс PassportEntity имеет несколько  полей,  id, series, number, lastName,
 * firstName, middleName, gender, birthDate, birthPlace, issuedBy, dateOfIssue, divisionCode,
 * expirationDate, и registrationEntity.
 * Все поля их название и значения описаны в классе PassportDTO
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
@EntityListeners(AuditingPassport.class)
@Table(name = "passport", schema = "profile")
public class PassportEntity extends AuditableMy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer series;
    private Long number;
    private String lastName;
    private String firstName;
    @Column(nullable = false)
    private String middleName;
    private String gender;
    private LocalDate birthDate;
    private String birthPlace;
    private String issuedBy;
    private LocalDate dateOfIssue;
    private Integer divisionCode;
    @Column(nullable = false)
    private LocalDate expirationDate;
    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "registration_id", referencedColumnName = "id")
    private RegistrationEntity registrationEntity;
}
