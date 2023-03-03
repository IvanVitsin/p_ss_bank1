package com.bank.antifraud.entity;

import com.bank.antifraud.service.audit.AuditAbstract;
import com.bank.antifraud.service.audit.AuditingCard;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.persistence.*;

/*Сущность связанная с таблицей в бд */


@Entity
@Table(name = "suspicious_card_transfer", schema = "anti_fraud")
@Getter
@Builder
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingCard.class)
@Schema(description = "информация о подозрительных переводах по номеру карты")
public class CardTransferEntity extends AuditAbstract {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    @Column(name ="card_transfer_id", unique = true)
    private Long cardTransferId;

    @Column(name = "is_blocked")
    private boolean isBlocked;

    @Column(name = "is_suspicious")
    private boolean isSuspicious;

    @Column(name = "blocked_reason", nullable = false)
    private String blockedReason;

    @Column(name = "suspicious_reason")
    private String suspiciousReason;

}
