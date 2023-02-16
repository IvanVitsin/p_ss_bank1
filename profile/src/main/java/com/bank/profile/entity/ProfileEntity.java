package com.bank.profile.entity;

import com.bank.profile.service.AuditableMy;
import com.bank.profile.service.AuditingProfile;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

/**
 * Это класс , представляющий Profileсущность,
 * * В @EntityListenersаннотации указан настраиваемый класс прослушивателя сущности
 * AuditingProfileдля этой сущности. В аннотации @Table указывается имя таблицы, как profile схеме базы данных.
 * <p>
 * Поля:
 * id: поле с примечаниями  представляющее уникальный идентификатор профиля.
 * phoneNumber: поле, представляющее номер телефона профиля
 * email: поле представляющее адрес электронной почты.
 * nameOnCard:  представляющее имя на карточке, связанной с профилем.
 * inn: представляющее индивидуальный номер налогоплательщика, связанный с профилем.
 * snils:  представляющее страховой номер, связанный с профилем.
 * passportEntity: PassportEntity представляющее связанный объект паспорта.
 * Это отношение один к одному, отображаемое внешним ключом passport_id.
 * actualRegistration: ActualRegistrationEntityполе представляющее связанный фактический регистрационный
 * объект. Это отношение один к одному, отображаемое внешним ключом actual_registration_id
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
@EntityListeners(AuditingProfile.class)
@Table(name = "profile", schema = "profile")
public class ProfileEntity extends AuditableMy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long phoneNumber;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String nameOnCard;
    @Column(unique = true, nullable = false)
    private Long inn;
    @Column(unique = true, nullable = false)
    private Long snils;
    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "passport_id", referencedColumnName = "id")
    private PassportEntity passportEntity;
    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "actual_registration_id", referencedColumnName = "id")
    private ActualRegistrationEntity actualRegistration = null;
}
