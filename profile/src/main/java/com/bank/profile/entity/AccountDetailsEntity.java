package com.bank.profile.entity;

import com.bank.profile.service.AuditableMy;
import com.bank.profile.service.AuditingAccountDetails;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

/**
 * Это класс  представляющий AccountDetails сущность, В аннотации @Table
 * указывается имя таблицы, как account_details_idв profile схеме базы данных.
 * <p>
 * Поля:
 * id: поле с примечаниями  представляющее уникальный идентификатор сведений об учетной записи.
 * accountId: поле, представляющее уникальный идентификатор учетной записи.
 * profileEntity: ProfileEntity поле с аннотацией представляющее связанный объект профиля.
 * Это отношение «многие к одному», отображаемое profile_id внешним ключом.
 * В @EntityListenersаннотации указан настраиваемый класс прослушивателя сущности AuditingAccountDetails для этой сущности.
 * В аннотации @Table указывается имя таблицы, как account_details_idв profile схеме базы данных.
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
@EntityListeners(AuditingAccountDetails.class)
@Table(name = "account_details_id", schema = "profile")
public class AccountDetailsEntity extends AuditableMy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long accountId;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "profile_id", referencedColumnName = "id")
    private ProfileEntity profileEntity;
}
